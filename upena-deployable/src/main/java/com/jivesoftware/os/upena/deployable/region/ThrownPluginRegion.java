package com.jivesoftware.os.upena.deployable.region;

import com.google.common.collect.Maps;
import com.jivesoftware.os.mlogger.core.MetricLogger;
import com.jivesoftware.os.mlogger.core.MetricLoggerFactory;
import com.jivesoftware.os.routing.bird.http.client.HttpRequestHelper;
import com.jivesoftware.os.routing.bird.http.client.HttpRequestHelperUtils;
import com.jivesoftware.os.upena.deployable.UpenaHealth;
import com.jivesoftware.os.upena.deployable.soy.SoyRenderer;
import com.jivesoftware.os.upena.service.UpenaStore;
import com.jivesoftware.os.upena.shared.*;
import org.apache.shiro.SecurityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;

/**
 *
 */
// soy.page.upenaRingPluginRegion
public class ThrownPluginRegion implements PageRegion<ThrownPluginRegion.ThrownPluginRegionInput> {

    private static final MetricLogger LOG = MetricLoggerFactory.getLogger();

    private final HostKey hostKey;
    private final String template;
    private final SoyRenderer renderer;
    private final UpenaStore upenaStore;

    public ThrownPluginRegion(HostKey hostKey,
        String template,
        SoyRenderer renderer,
        UpenaStore upenaStore) {

        this.hostKey = hostKey;
        this.template = template;
        this.renderer = renderer;
        this.upenaStore = upenaStore;
    }

    @Override
    public String getRootPath() {
        return "/ui/breakpoint";
    }

    public static class ThrownPluginRegionInput implements PluginInput {

        final String instanceKey;
        final String clusterKey;
        final String cluster;
        final String hostKey;
        final String host;
        final String serviceKey;
        final String service;
        final String instanceId;
        final String releaseKey;
        final String release;
        final List<String> instanceKeys;
        final String hostName;
        final int port;

        long sessionId;
        final long connectionId;
        final String breakPointFieldName;
        final String filter;
        final String className;
        final int lineNumber;
        final int maxVersions;

        final String breakpoint;

        final String action;

        public ThrownPluginRegionInput(String instanceKey, String clusterKey, String cluster, String hostKey, String host, String serviceKey,
            String service,
            String instanceId, String releaseKey, String release, List<String> instanceKeys, String hostName,
            int port,
            long sessionId,
            long connectionId,
            String breakPointFieldName,
            String filter,
            String className,
            int lineNumber,
            int maxVersions,
            String breakpoint,
            String action) {

            this.instanceKey = instanceKey;
            this.clusterKey = clusterKey;
            this.cluster = cluster;
            this.hostKey = hostKey;
            this.host = host;
            this.serviceKey = serviceKey;
            this.service = service;
            this.instanceId = instanceId;
            this.releaseKey = releaseKey;
            this.release = release;
            this.instanceKeys = instanceKeys;
            this.hostName = hostName;
            this.port = port;
            this.sessionId = sessionId;
            this.connectionId = connectionId;
            this.breakPointFieldName = breakPointFieldName;
            this.filter = filter;
            this.className = className;
            this.lineNumber = lineNumber;
            this.maxVersions = maxVersions;
            this.breakpoint = breakpoint;
            this.action = action;
        }

        @Override
        public String name() {
            return "BreakpointDumper";
        }

    }

    @Override
    public String render(String user, ThrownPluginRegionInput input) throws Exception {
        SecurityUtils.getSubject().checkPermission("debug");

        Map<String, Object> data = Maps.newHashMap();
        data.put("hostName", input.hostName);
        data.put("port", input.port);
        data.put("className", input.className);
        data.put("lineNumber", input.lineNumber);

        Map<String, Object> filters = new HashMap<>();
        filters.put("clusterKey", input.clusterKey);
        filters.put("cluster", input.cluster);
        filters.put("hostKey", input.hostKey);
        filters.put("host", input.host);
        filters.put("serviceKey", input.serviceKey);
        filters.put("service", input.service);
        filters.put("instanceId", input.instanceId);
        filters.put("releaseKey", input.releaseKey);
        filters.put("release", input.release);
        data.put("filters", filters);


        try {
            List<Map<String, Object>> thrown = populateThrown();
            data.put("thrown", thrown);
        } catch (Exception x) {
            LOG.warn("Failed populating thrown", x);
        }

        return renderer.render(template, data);
    }

    private List<Map<String, Object>> populateThrown() throws Exception {

        ConcurrentNavigableMap<InstanceKey, TimestampedValue<Instance>> found = upenaStore.instances.find(false, new InstanceFilter(null, hostKey, null, null,
            null, 0, 100_000));

        List<Map<String, Object>> instances = new ArrayList<>();
        for (Map.Entry<InstanceKey, TimestampedValue<Instance>> entry : found.entrySet()) {
            if (entry.getValue().getTombstoned()) {
                continue;
            }
            Instance instance = entry.getValue().getValue();
            if (!instance.enabled) {
                continue;
            }
            Cluster cluster = upenaStore.clusters.get(instance.clusterKey);
            if (cluster == null) {
                continue;
            }
            Host host = upenaStore.hosts.get(instance.hostKey);
            if (host == null) {
                continue;
            }
            Service service = upenaStore.services.get(instance.serviceKey);
            if (service == null) {
                continue;
            }
            ReleaseGroup releaseGroup = upenaStore.releaseGroups.get(instance.releaseGroupKey);
            if (releaseGroup == null) {
                continue;
            }

            Map<String, Object> instanceMap = new HashMap<>();
            instanceMap.put("instanceKey", entry.getKey().getKey());
            instanceMap.put("cluster", cluster.name);
            instanceMap.put("host", host.hostName);
            instanceMap.put("service", service.name);
            instanceMap.put("release", releaseGroup.name);
            instanceMap.put("instanceId", String.valueOf(instance.instanceId));

            Instance.Port manage = instance.ports.get("manage");
            LOG.debug("fetching thrown:http://localhost:" + manage.port + "/manage/thrown");
            HttpRequestHelper requestHelper = HttpRequestHelperUtils.buildRequestHelper(manage.sslEnabled,
                true, null, "localhost", manage.port);

            Throwns throwns = requestHelper.executeGetRequest("/manage/thrown", Throwns.class, null);
            long mostRecentTimestamp = 0;
            long total = 0;
            if (throwns != null && !throwns.isEmpty()) {
                // TODO sort by recency

                for (Thrown thrown : throwns) {
                    total += Long.parseLong(thrown.thrown);
                    mostRecentTimestamp = Math.max(mostRecentTimestamp, Long.parseLong(thrown.timestamps.get(thrown.timestamps.size() - 1)));
                    decorate(thrown);
                }
                instanceMap.put("thrown", throwns);
                instances.add(instanceMap);
            }

            instanceMap.put("total", String.valueOf(total));
            instanceMap.put("recency", UpenaHealth.humanReadableUptime(System.currentTimeMillis() - mostRecentTimestamp));

        }

        return instances;

    }

    private void decorate(Thrown thrown) throws NumberFormatException {
        if (thrown.level.equals("info")) {
            thrown.level = "info";
        } else if (thrown.level.equals("debug")) {
            thrown.level = "success";
        } else if (thrown.level.equals("warn")) {
            thrown.level = "warning";
        } else if (thrown.level.equals("error")) {
            thrown.level = "danger";
        } else {
            thrown.level = "default";
        }
        thrown.recency = UpenaHealth.humanReadableUptime(System.currentTimeMillis() - Long.parseLong(thrown.timestamps.get(
            thrown.timestamps.size() - 1)));

        for (Thrown value : thrown.getCause().values()) {
            decorate(value);
        }
    }

    public static class Throwns extends ArrayList<Thrown> {

    }

    public static class Thrown {

        public String key;
        public String level;
        public String message;
        public List<ClassMethodFileLine> stackTrace = new ArrayList<>();
        public String thrown;
        public List<String> timestamps = new ArrayList<>();
        public String recency = "";
        public Map<String, Thrown> cause = new HashMap<>();

        public String getKey() {
            return key;
        }

        public String getLevel() {
            return level;
        }

        public String getMessage() {
            return message;
        }

        public List<ClassMethodFileLine> getStackTrace() {
            return stackTrace;
        }

        public String getThrown() {
            return thrown;
        }

        public List<String> getTimestamps() {
            return timestamps;
        }

        public String getRecency() {
            return recency;
        }

        public Map<String, Thrown> getCause() {
            return cause;
        }

        @Override
        public String toString() {
            return "Thrown{" + "key=" + key + ", level=" + level + ", message=" + message + ", stackTrace=" + stackTrace + ", thrown=" + thrown + ", timestamps=" + timestamps + ", cause=" + cause + '}';
        }

    }

    public static class ClassMethodFileLine {

        public String className;
        public String declaringClass;
        public String methodName;
        public String fileName;
        public int lineNumber;
        public String nativeMethod;

        public String getClassName() {
            return className;
        }

        public String getDeclaringClass() {
            return declaringClass;
        }

        public String getMethodName() {
            return methodName;
        }

        public String getFileName() {
            return fileName;
        }

        public int getLineNumber() {
            return lineNumber;
        }

        public String getNativeMethod() {
            return nativeMethod;
        }

        @Override
        public String toString() {
            return "ClassMethodFileLine{" + "className=" + className + ", declaringClass=" + declaringClass + ", methodName=" + methodName + ", fileName=" + fileName + ", lineNumber=" + lineNumber + ", nativeMethod=" + nativeMethod + '}';
        }

    }

    @Override
    public String getTitle() {
        return "Thrown";

    }

}

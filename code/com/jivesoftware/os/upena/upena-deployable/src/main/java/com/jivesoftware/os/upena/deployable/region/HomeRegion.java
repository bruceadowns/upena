package com.jivesoftware.os.upena.deployable.region;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.jivesoftware.os.jive.utils.http.client.HttpClient;
import com.jivesoftware.os.jive.utils.http.client.HttpClientConfig;
import com.jivesoftware.os.jive.utils.http.client.HttpClientConfiguration;
import com.jivesoftware.os.jive.utils.http.client.HttpClientFactory;
import com.jivesoftware.os.jive.utils.http.client.HttpClientFactoryProvider;
import com.jivesoftware.os.jive.utils.http.client.rest.RequestHelper;
import com.jivesoftware.os.mlogger.core.MetricLogger;
import com.jivesoftware.os.mlogger.core.MetricLoggerFactory;
import com.jivesoftware.os.server.http.jetty.jersey.endpoints.base.HasUI;
import com.jivesoftware.os.server.http.jetty.jersey.endpoints.base.HasUI.UI;
import com.jivesoftware.os.upena.deployable.region.HomeRegion.HomeInput;
import com.jivesoftware.os.upena.deployable.soy.SoyRenderer;
import com.jivesoftware.os.upena.service.UpenaStore;
import com.jivesoftware.os.upena.shared.Cluster;
import com.jivesoftware.os.upena.shared.Host;
import com.jivesoftware.os.upena.shared.Instance;
import com.jivesoftware.os.upena.shared.InstanceFilter;
import com.jivesoftware.os.upena.shared.InstanceKey;
import com.jivesoftware.os.upena.shared.Service;
import com.jivesoftware.os.upena.shared.TimestampedValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 *
 */
public class HomeRegion implements PageRegion<HomeInput> {

    private static final MetricLogger log = MetricLoggerFactory.getLogger();

    private final String template;
    private final SoyRenderer renderer;
    private final UpenaStore upenaStore;

    public HomeRegion(String template, SoyRenderer renderer, UpenaStore upenaStore) {
        this.template = template;
        this.renderer = renderer;
        this.upenaStore = upenaStore;
    }

    public static class HomeInput {

        final String wgetURL;
        final String upenaClusterName;

        public HomeInput(String wgetURL, String upenaClusterName) {
            this.wgetURL = wgetURL;
            this.upenaClusterName = upenaClusterName;
        }

    }

    @Override
    public String render(HomeInput input) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("wgetURL", input.wgetURL);
        data.put("upenaClusterName", input.upenaClusterName);

        InstanceFilter filter = new InstanceFilter(
            null,
            null,
            null,
            null,
            null,
            0, 10000);

        ConcurrentSkipListMap<String, List<Map<String, String>>> uis = new ConcurrentSkipListMap<>();
        try {
            Map<InstanceKey, TimestampedValue<Instance>> found = upenaStore.instances.find(filter);
            for (Map.Entry<InstanceKey, TimestampedValue<Instance>> entrySet : found.entrySet()) {
                TimestampedValue<Instance> timestampedValue = entrySet.getValue();
                Instance value = timestampedValue.getValue();
                Host host = upenaStore.hosts.get(value.hostKey);
                Cluster cluster = upenaStore.clusters.get(value.clusterKey);
                Service service = upenaStore.services.get(value.serviceKey);

                Instance.Port port = value.ports.get("manage");
                if (port != null) {
                    try {
                        RequestHelper requestHelper = buildRequestHelper(host.hostName, port.port);
                        HasUI hasUI = requestHelper.executeGetRequest("/manage/hasUI", HasUI.class, null);
                        if (hasUI != null) {
                            for (UI ui : hasUI.uis) {

                                Instance.Port uiPort = value.ports.get(ui.portName);
                                if (uiPort != null) {
                                    String uiName = cluster.name + " - " + ui.name;
                                    List<Map<String, String>> namedUIs = uis.get(uiName);
                                    if (namedUIs == null) {
                                        namedUIs = new ArrayList<>();
                                        uis.put(uiName, namedUIs);
                                    }

                                    Map<String, String> uiMap = new HashMap<>();
                                    uiMap.put("cluter", cluster.name);
                                    uiMap.put("host", host.name);
                                    uiMap.put("port", String.valueOf(uiPort.port));
                                    uiMap.put("service", service.name);
                                    uiMap.put("instance", String.valueOf(value.instanceId));
                                    uiMap.put("name", ui.name);
                                    uiMap.put("url", ui.url);
                                    namedUIs.add(uiMap);
                                }
                            }
                        }
                    } catch (Exception x) {
                        log.debug("instance doens't have a ui.", x);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Unable to retrieve data", e);
        }

        List<Map<String, Object>> listOfNamedUIs = new ArrayList<>();
        for (Entry<String, List<Map<String, String>>> e : uis.entrySet()) {
            listOfNamedUIs.add(ImmutableMap.of("name", e.getKey(), "uis", e.getValue()));
        }
        data.put("uis", listOfNamedUIs);
        return renderer.render(template, data);
    }

    @Override
    public String getTitle() {
        return "Home";
    }

    RequestHelper buildRequestHelper(String host, int port) {
        HttpClientConfig httpClientConfig = HttpClientConfig.newBuilder().setSocketTimeoutInMillis(10000).build();
        HttpClientFactory httpClientFactory = new HttpClientFactoryProvider()
            .createHttpClientFactory(Arrays.<HttpClientConfiguration>asList(httpClientConfig));
        HttpClient httpClient = httpClientFactory.createClient(host, port);
        RequestHelper requestHelper = new RequestHelper(httpClient, new ObjectMapper());
        return requestHelper;
    }

}

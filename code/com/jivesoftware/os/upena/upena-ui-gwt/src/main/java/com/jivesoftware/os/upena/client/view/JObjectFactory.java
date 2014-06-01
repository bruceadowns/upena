/*
 * Copyright 2013 Jive Software, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.jivesoftware.os.upena.client.view;

import com.jivesoftware.os.upena.client.view.upena.shared.Cluster;
import com.jivesoftware.os.upena.client.view.upena.shared.ClusterFilter;
import com.jivesoftware.os.upena.client.view.upena.shared.Host;
import com.jivesoftware.os.upena.client.view.upena.shared.HostFilter;
import com.jivesoftware.os.upena.client.view.upena.shared.Instance;
import com.jivesoftware.os.upena.client.view.upena.shared.InstanceFilter;
import com.jivesoftware.os.upena.client.view.upena.shared.ReleaseGroup;
import com.jivesoftware.os.upena.client.view.upena.shared.ReleaseGroupFilter;
import com.jivesoftware.os.upena.client.view.upena.shared.Service;
import com.jivesoftware.os.upena.client.view.upena.shared.ServiceFilter;
import com.jivesoftware.os.upena.client.view.upena.shared.Tenant;
import com.jivesoftware.os.upena.client.view.upena.shared.TenantFilter;
import java.util.HashMap;
import java.util.Map;

public class JObjectFactory {

    private final Map<String, Creator<?, ?>> factory = new HashMap<String, Creator<?, ?>>();

    public JObjectFactory(final RequestHelperProvider requestHelperProvider) {

        factory.put("Instance", new Creator<Instance, InstanceFilter>() {
            @Override
            public JObject<Instance, InstanceFilter> create(boolean hasPopup, IPicked<Instance> picked) {

                JExecutor<Instance, InstanceFilter> vExecutor = new JExecutor<Instance, InstanceFilter>(requestHelperProvider, "instance");
                JFieldsForInstance fields = new JFieldsForInstance(JObjectFactory.this, "", null);
                return new JObject<Instance, InstanceFilter>(fields, vExecutor, hasPopup, picked);
            }
        });

        factory.put("Cluster", new Creator<Cluster, ClusterFilter>() {
            @Override
            public JObject<Cluster, ClusterFilter> create(boolean hasPopup, IPicked<Cluster> picked) {

                JExecutor<Cluster, ClusterFilter> vExecutor = new JExecutor<Cluster, ClusterFilter>(requestHelperProvider, "cluster");
                JFieldsForCluster fields = new JFieldsForCluster(JObjectFactory.this, "", null);
                return new JObject<Cluster, ClusterFilter>(fields, vExecutor, hasPopup, picked);
            }
        });

        factory.put("Host", new Creator<Host, HostFilter>() {
            @Override
            public JObject<Host, HostFilter> create(boolean hasPopup, IPicked<Host> picked) {
                JExecutor<Host, HostFilter> vExecutor = new JExecutor<Host, HostFilter>(requestHelperProvider, "host");
                JFieldsForHost fields = new JFieldsForHost(JObjectFactory.this, "", null);
                return new JObject<Host, HostFilter>(fields, vExecutor, hasPopup, picked);
            }
        });

        factory.put("Service", new Creator<Service, ServiceFilter>() {
            @Override
            public JObject<Service, ServiceFilter> create(boolean hasPopup, IPicked<Service> picked) {

                JExecutor<Service, ServiceFilter> vExecutor = new JExecutor<Service, ServiceFilter>(requestHelperProvider, "service");
                JFieldsForService fields = new JFieldsForService("", null);
                return new JObject<Service, ServiceFilter>(fields, vExecutor, hasPopup, picked);
            }
        });

        factory.put("ReleaseGroup", new Creator<ReleaseGroup, ReleaseGroupFilter>() {
            @Override
            public JObject<ReleaseGroup, ReleaseGroupFilter> create(boolean hasPopup, IPicked<ReleaseGroup> picked) {

                JExecutor<ReleaseGroup, ReleaseGroupFilter> vExecutor = new JExecutor<ReleaseGroup, ReleaseGroupFilter>(requestHelperProvider, "release");
                JFieldsForReleaseGroup fields = new JFieldsForReleaseGroup("", null);
                return new JObject<ReleaseGroup, ReleaseGroupFilter>(fields, vExecutor, hasPopup, picked);
            }
        });

        factory.put("Tenant", new Creator<Tenant, TenantFilter>() {
            @Override
            public JObject<Tenant, TenantFilter> create(boolean hasPopup, IPicked<Tenant> picked) {

                JExecutor<Tenant, TenantFilter> vExecutor = new JExecutor<Tenant, TenantFilter>(requestHelperProvider, "tenant");
                JFieldsTenant fields = new JFieldsTenant(JObjectFactory.this, "", null);
                return new JObject<Tenant, TenantFilter>(fields, vExecutor, hasPopup, picked);
            }
        });

    }

    JObject<?, ?> create(String  _class, boolean hasPopup, IPicked picked) {
        Creator<?, ?> got = factory.get(_class);
        return got.create(hasPopup, picked);
    }

    static interface Creator<V, F> {

        JObject<V, F> create(boolean hasPopup, IPicked<V> picked);
    }
}

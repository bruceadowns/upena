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

import com.google.gwt.json.client.JSONObject;
import com.jivesoftware.os.upena.client.view.upena.shared.Host;
import com.jivesoftware.os.upena.client.view.upena.shared.HostFilter;
import com.jivesoftware.os.upena.client.view.upena.shared.Key;
import java.util.LinkedHashMap;
import java.util.Map;

public class JFieldsForHost implements JObjectFields<Host, HostFilter> {

    JEditKeyField hostKey;
    JEditField name;
    JEditField hostName;
    JEditField port;
    JEditField workingDir;
    JEditRef clusterKey;
    Map<String, JField> fields = new LinkedHashMap<String, JField>();
    JObjectFactory factory;
    String key;
    Host value;

    public JFieldsForHost(JObjectFactory factory, String k, Host v) {
        this.factory = factory;
        this.key = k;
        this.value = v;
        name = new JEditField("name", (v != null) ? v.getName() : "");
        fields.put("name", name);
        hostName = new JEditField("hostName", (v != null) ? v.getHostName() : "");
        fields.put("hostName", hostName);
        port = new JEditField("port", (v != null) ? Integer.toString(v.getPort()) : "");
        fields.put("port", port);
        workingDir = new JEditField("workingDirectory", (v != null) ? v.getWorkingDirectory() : "");
        fields.put("workingDirectory", workingDir);
        clusterKey = new JEditRef(factory, "clusterKey", "Cluster", (v != null) ? (v.getClusterKey() != null) ? v.getClusterKey().getKey() : "" : "");
        fields.put("clusterKey",
                clusterKey);
        hostKey = new JEditKeyField("key", (k != null) ? k : "");
        fields.put("key", hostKey);

    }

    @Override
    public JObjectFields<Host, HostFilter> copy() {
        return new JFieldsForHost(factory, key, value);
    }

    @Override
    public String shortName(Host v) {
        return v.getName();
    }

    @Override
    public Host fieldsToObject() {
        Host host = Host.create(name.getValue(),
                hostName.getValue(),
                Integer.parseInt(port.getValue()),
                workingDir.getValue(),
                Key.key(clusterKey.getValue(), "clusterKey"));
        return host;
    }

    @Override
    public Map<String, JField> objectFields() {
        return fields;
    }

    @Override
    public Key key() {
        return Key.key(hostKey.getValue(), "hostKey");
    }

    @Override
    public HostFilter fieldsToFilter() {
        HostFilter filter = HostFilter.create(name.getValue(),
                hostName.getValue(),
                ((port.getValue().length() == 0) ? null : Integer.parseInt(port.getValue())),
                workingDir.getValue(),
                FilterUtils.nullIfEmpty(Key.key(clusterKey.getValue(), "clusterKey")),
                0, 100);
        return filter;
    }

    @Override
    public void update(Key key, Host v) {
        hostKey.setValue(key.getKey());
        name.setValue(v.getName());
        hostName.setValue(v.getHostName());
        port.setValue(Integer.toString(v.getPort()));
        workingDir.setValue(v.getWorkingDirectory());
        clusterKey.setValue(v.getClusterKey() == null ? "" : v.getClusterKey().getKey());
    }

    @Override
    public void updateFilter(Key key, Host v) {
        name.setValue(v.getName());
        hostName.setValue(v.getHostName());
        port.setValue(Integer.toString(v.getPort()));
        workingDir.setValue(v.getWorkingDirectory());
        clusterKey.setValue(v.getClusterKey() == null ? "" : v.getClusterKey().getKey());
    }

    @Override
    public Key key(String key) {
        return Key.key(key, "hostKey");
    }

    @Override
    public Host valueFromJsonString(String value) {
        return Host.fromJson(value);
    }

    @Override
    public String valueAsJsonString(Host value) {
        return new JSONObject(value).toString();
    }

    @Override
    public String filterAsJsonString(HostFilter filter) {
        return new JSONObject(filter).toString();
    }
}
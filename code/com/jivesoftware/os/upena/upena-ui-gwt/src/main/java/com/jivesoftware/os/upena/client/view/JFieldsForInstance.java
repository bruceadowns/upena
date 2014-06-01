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
import com.jivesoftware.os.upena.client.view.upena.shared.Instance;
import com.jivesoftware.os.upena.client.view.upena.shared.InstanceFilter;
import com.jivesoftware.os.upena.client.view.upena.shared.Key;
import java.util.LinkedHashMap;
import java.util.Map;

public class JFieldsForInstance implements JObjectFields<Instance, InstanceFilter> {

    Map<String, JField> fields = new LinkedHashMap<String, JField>();
    JObjectFactory factory;
    String key;
    Instance value;
    JEditKeyField instanceKey;
    JEditField instanceId;
    JEditBooleanField enabled;
    JEditBooleanField locked;
    JEditRef releaseGroupKey;
    JEditRef serviceKey;
    JEditRef hostKey;
    JEditRef clusterKey;
    JEditPortsField ports;

    public JFieldsForInstance(JObjectFactory factory, String k, Instance v) {
        this.factory = factory;
        this.key = k;
        this.value = v;


        hostKey = new JEditRef(factory, "host", "Host", (v != null) ? (v.hostKey != null) ? v.hostKey.getKey() : "" : "");
        fields.put("hostKey", hostKey);

        serviceKey = new JEditRef(factory, "service", "Service", (v != null) ? (v.serviceKey != null) ? v.serviceKey.getKey() : "" : "");
        fields.put("serviceKey", serviceKey);

        instanceId = new JEditField("instanceId", (v != null) ? "" + v.instanceId : "");
        fields.put("instanceId", instanceId);

        clusterKey = new JEditRef(factory, "cluster", "Cluster", (v != null) ? (v.clusterKey != null) ? v.clusterKey.getKey() : "" : "");
        fields.put("clusterKey", clusterKey);

        releaseGroupKey = new JEditRef(
                factory, "releaseGroup", "ReleaseGroup", (v != null) ? (v.releaseGroupKey != null) ? v.releaseGroupKey.getKey() : "" : "");
        fields.put("releaseGroupKey", releaseGroupKey);

        enabled = new JEditBooleanField("enabled", "");
        fields.put("enabled", enabled);

        locked = new JEditBooleanField("locked", "");
        fields.put("locked", locked);

        ports = new JEditPortsField(factory, "ports", (v != null) ? (v.ports != null) ? v.ports : null : null);
        fields.put("ports", ports);

        instanceKey = new JEditKeyField("key", (k != null) ? k : "");
        fields.put("key", instanceKey);

    }

    @Override
    public JObjectFields<Instance, InstanceFilter> copy() {
        return new JFieldsForInstance(factory, key, value);
    }

    @Override
    public String shortName(Instance v) {
        return "" + v.instanceId;
    }

    @Override
    public Instance fieldsToObject() {
        return new Instance(Key.key(clusterKey.getValue(), "clusterKey"),
                Key.key(hostKey.getValue(), "hostKey"),
                Key.key(serviceKey.getValue(), "serviceKey"),
                Key.key(releaseGroupKey.getValue(), "userKey"),
                Integer.parseInt(instanceId.getValue()),
                Boolean.parseBoolean(enabled.getValue()),
                Boolean.parseBoolean(locked.getValue()));

    }

    @Override
    public Map<String, JField> objectFields() {
        return fields;
    }

    @Override
    public Key key() {
        return Key.key(instanceKey.getValue(), "instanceKey");
    }

    @Override
    public InstanceFilter fieldsToFilter() {
        Integer id = null;
        try {
            id = Integer.parseInt(instanceId.getValue());
        } catch (Exception x) {
        }
        InstanceFilter filter = InstanceFilter.create(
                FilterUtils.nullIfEmpty(Key.key(clusterKey.getValue(), "clusterKey")),
                FilterUtils.nullIfEmpty(Key.key(hostKey.getValue(), "hostKey")),
                FilterUtils.nullIfEmpty(Key.key(serviceKey.getValue(), "serviceKey")),
                FilterUtils.nullIfEmpty(Key.key(releaseGroupKey.getValue(), "userKey")),
                id,
                0, 100);
        return filter;
    }

    @Override
    public void update(Key key, Instance v) {
        instanceKey.setValue(key.getKey());
        instanceId.setValue("" + v.instanceId);
        clusterKey.setValue(v.clusterKey.getKey());
        serviceKey.setValue(v.serviceKey.getKey());
        releaseGroupKey.setValue(v.releaseGroupKey.getKey());
        hostKey.setValue(v.hostKey.getKey());
        enabled.setValue(Boolean.toString(v.enabled));
        locked.setValue(Boolean.toString(v.locked));
        ports.setValue(v.ports);

    }

    @Override
    public void updateFilter(Key key, Instance v) {
        instanceId.setValue("" + v.instanceId);
        clusterKey.setValue(v.clusterKey.getKey());
        serviceKey.setValue(v.serviceKey.getKey());
        releaseGroupKey.setValue(v.releaseGroupKey.getKey());
        hostKey.setValue(v.hostKey.getKey());
        enabled.setValue(Boolean.toString(v.enabled));
        locked.setValue(Boolean.toString(v.locked));
    }

    @Override
    public Key key(String key) {
        return Key.key(key, "instanceKey");
    }

    @Override
    public Instance valueFromJsonString(String value) {
        return null;//
    }

    @Override
    public String valueAsJsonString(Instance value) {
        return null; //return new JSONObject(value).toString();
    }

    @Override
    public String filterAsJsonString(InstanceFilter filter) {
        return new JSONObject(filter).toString();
    }

}
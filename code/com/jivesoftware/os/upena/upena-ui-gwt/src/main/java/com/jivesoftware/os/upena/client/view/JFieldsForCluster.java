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
import com.jivesoftware.os.upena.client.view.upena.shared.Cluster;
import com.jivesoftware.os.upena.client.view.upena.shared.ClusterFilter;
import com.jivesoftware.os.upena.client.view.upena.shared.Key;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class JFieldsForCluster implements JObjectFields<Cluster, ClusterFilter> {

    Map<String, JField> fields = new LinkedHashMap<String, JField>();
    JObjectFactory factory;
    String key;
    Cluster value;
    JEditKeyField clusterKey;
    JEditField name;
    JEditField description;
    JEditReleaseGroupsField defaultReleaseGroups;
    JEditReleaseGroupsField defaultAlternateReleaseGroups;

    public JFieldsForCluster(JObjectFactory factory, String k, Cluster v) {
        this.factory = factory;
        this.key = k;
        this.value = v;
        name = new JEditField("name", (v != null) ? v.getName() : "");
        fields.put("name", name);
        description = new JEditField("description", (v != null) ? v.getDescription() : "");
        fields.put("description", description);

        defaultReleaseGroups = new JEditReleaseGroupsField(factory, "defaultReleaseGroups", (v != null) ? v.getDefaultReleaseGroups() : null);
        fields.put("defaultReleaseGroups", defaultReleaseGroups);

        defaultAlternateReleaseGroups = new JEditReleaseGroupsField(factory, "defaultAlternateReleaseGroups",
                (v != null) ? v.getDefaultAlternateReleaseGroups() : null);
        fields.put("defaultAlternateReleaseGroups", defaultAlternateReleaseGroups);

        clusterKey = new JEditKeyField("key", (k != null) ? k : "");
        fields.put("key", clusterKey);

    }

    @Override
    public JObjectFields<Cluster, ClusterFilter> copy() {
        return new JFieldsForCluster(factory, key, value);
    }

    @Override
    public String shortName(Cluster v) {
        return v.getName();
    }

    @Override
    public Cluster fieldsToObject() {
        Map<Key, Key> serviceKeyToReleaseGroupKey = new TreeMap<Key, Key>();
        serviceKeyToReleaseGroupKey.putAll(defaultReleaseGroups.serviceKeyToReleaseGroupKey);

        Map<Key, Key> defaultAlternates = new TreeMap<Key, Key>();
        defaultAlternates.putAll(defaultAlternateReleaseGroups.serviceKeyToReleaseGroupKey);
        Cluster cluster = Cluster.create(name.getValue(),
                description.getValue(),
                serviceKeyToReleaseGroupKey,
                defaultAlternates);
        return cluster;
    }

    @Override
    public Map<String, JField> objectFields() {
        return fields;
    }

    @Override
    public Key key() {
        return Key.key(clusterKey.getValue(), "clusterKey");
    }

    @Override
    public ClusterFilter fieldsToFilter() {
        ClusterFilter filter = ClusterFilter.create(name.getValue(),
                description.getValue(), 0, 100);
        return filter;
    }

    @Override
    public void update(Key key, Cluster v) {
        clusterKey.setValue(key.getKey());
        name.setValue(v.getName());
        description.setValue(v.getDescription());
        defaultReleaseGroups.setValue(v.getDefaultReleaseGroups());
        defaultAlternateReleaseGroups.setValue(v.getDefaultAlternateReleaseGroups());
    }

    @Override
    public void updateFilter(Key key, Cluster v) {
        name.setValue(v.getName());
        description.setValue(v.getDescription());
    }

    @Override
    public Key key(String key) {
        return Key.key(key, "clusterKey");
    }

    @Override
    public Cluster valueFromJsonString(String value) {
        return Cluster.fromJson(value);
    }

    @Override
    public String valueAsJsonString(Cluster value) {
        return new JSONObject(value).toString();
    }

    @Override
    public String filterAsJsonString(ClusterFilter filter) {
        return new JSONObject(filter).toString();
    }
}
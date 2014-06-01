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
import com.jivesoftware.os.upena.client.view.upena.shared.Key;
import com.jivesoftware.os.upena.client.view.upena.shared.Service;
import com.jivesoftware.os.upena.client.view.upena.shared.ServiceFilter;
import java.util.LinkedHashMap;
import java.util.Map;

public class JFieldsForService implements JObjectFields<Service, ServiceFilter> {

    Map<String, JField> fields = new LinkedHashMap<String, JField>();
    String key;
    Service value;
    JEditKeyField serviceKey;
    JEditField name;
    JEditField description;

    public JFieldsForService(String k, Service v) {
        this.key = k;
        this.value = v;

        name = new JEditField("name", (v != null) ? v.getName() : "");
        fields.put("name", name);

        description = new JEditField("description", (v != null) ? v.getDescription() : "");
        fields.put("description", description);

        serviceKey = new JEditKeyField("key", (k != null) ? k : "");
        fields.put("key", serviceKey);

    }

    @Override
    public JObjectFields<Service, ServiceFilter> copy() {
        return new JFieldsForService(key, value);
    }

    @Override
    public String shortName(Service v) {
        return v.getName();
    }

    @Override
    public Service fieldsToObject() {
        return Service.create(name.getValue(),
                description.getValue());
    }

    @Override
    public Map<String, JField> objectFields() {
        return fields;
    }

    @Override
    public Key key() {
        return Key.key(serviceKey.getValue(), "serviceKey");
    }

    @Override
    public ServiceFilter fieldsToFilter() {
        ServiceFilter filter = ServiceFilter.create(name.getValue(),
                description.getValue(), 0, 100);
        return filter;
    }

    @Override
    public void update(Key key, Service v) {
        serviceKey.setValue(key.getKey());
        name.setValue(v.getName());
        description.setValue(v.getDescription());
    }

    @Override
    public void updateFilter(Key key, Service v) {
        name.setValue(v.getName());
        description.setValue(v.getDescription());
    }

    @Override
    public Key key(String key) {
        return Key.key(key, "serviceKey");
    }

    @Override
    public Service valueFromJsonString(String value) {
        return Service.fromJson(value);
    }

    @Override
    public String valueAsJsonString(Service value) {
        return new JSONObject(value).toString();
    }

    @Override
    public String filterAsJsonString(ServiceFilter filter) {
        return new JSONObject(filter).toString();
    }

}
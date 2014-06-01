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
import com.jivesoftware.os.upena.client.view.upena.shared.ReleaseGroup;
import com.jivesoftware.os.upena.client.view.upena.shared.ReleaseGroupFilter;
import java.util.LinkedHashMap;
import java.util.Map;

public class JFieldsForReleaseGroup implements JObjectFields<ReleaseGroup, ReleaseGroupFilter> {

    Map<String, JField> fields = new LinkedHashMap<String, JField>();
    JObjectFactory factory;
    String key;
    ReleaseGroup value;
    JEditKeyField releaseGroupKey;
    JEditField name;
    JEditField email;
    JEditField version;
    JEditField repository;
    JEditField description;

    public JFieldsForReleaseGroup(String k, ReleaseGroup v) {
        this.key = k;
        this.value = v;

        name = new JEditField("name", (v != null) ? v.getName() : "");
        fields.put("name", name);

        email = new JEditField("email", (v != null) ? v.getEmail() : "");
        fields.put("email", email);

        version = new JEditField("version", (v != null) ? v.getVersion() : "");
        fields.put("version", version);

        repository = new JEditField("repository", (v != null) ? v.getRepository() : "");
        fields.put("repository", repository);

        description = new JEditField("description", (v != null) ? v.getDescription() : "");
        fields.put("description", description);

        releaseGroupKey = new JEditKeyField("key", (k != null) ? k : "");
        fields.put("key", releaseGroupKey);

    }

    @Override
    public JObjectFields<ReleaseGroup, ReleaseGroupFilter> copy() {
        return new JFieldsForReleaseGroup(key, value);
    }

    @Override
    public String shortName(ReleaseGroup v) {
        return v.getName();
    }

    @Override
    public ReleaseGroup fieldsToObject() {
        return ReleaseGroup.create(name.getValue(),
                email.getValue(),
                version.getValue(),
                repository.getValue(),
                description.getValue());
    }

    @Override
    public Map<String, JField> objectFields() {
        return fields;
    }

    @Override
    public Key key() {
        return Key.key(releaseGroupKey.getValue(), "userKey");
    }

    @Override
    public ReleaseGroupFilter fieldsToFilter() {
        ReleaseGroupFilter filter = ReleaseGroupFilter.create(name.getValue(),
                email.getValue(),
                version.getValue(),
                repository.getValue(),
                description.getValue(), 0, 100);
        return filter;
    }

    @Override
    public void update(Key key, ReleaseGroup v) {
        releaseGroupKey.setValue(key.getKey());
        name.setValue(v.getName());
        email.setValue(v.getEmail());
        version.setValue(v.getVersion());
        description.setValue(v.getDescription());
    }

    @Override
    public void updateFilter(Key key, ReleaseGroup v) {
        name.setValue(v.getName());
        email.setValue(v.getEmail());
        version.setValue(v.getVersion());
        description.setValue(v.getDescription());
    }

    @Override
    public Key key(String key) {
        return Key.key(key, "userKey");
    }

    @Override
    public ReleaseGroup valueFromJsonString(String value) {
        return ReleaseGroup.fromJson(value);
    }

    @Override
    public String valueAsJsonString(ReleaseGroup value) {
        return new JSONObject(value).toString();
    }

    @Override
    public String filterAsJsonString(ReleaseGroupFilter filter) {
        return new JSONObject(filter).toString();
    }

}
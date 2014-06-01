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
import com.jivesoftware.os.upena.client.view.upena.shared.Tenant;
import com.jivesoftware.os.upena.client.view.upena.shared.TenantFilter;
import java.util.LinkedHashMap;
import java.util.Map;

public class JFieldsTenant implements JObjectFields<Tenant, TenantFilter> {

    Map<String, JField> fields = new LinkedHashMap<String, JField>();
    JObjectFactory factory;
    String key;
    Tenant value;
    JEditKeyField tenantKey;
    JEditField tenantId;
    JEditField description;
    JEditRef releaseGroupKey;
    JEditRef alternateReleaseGroupKey;

    public JFieldsTenant(JObjectFactory factory, String k, Tenant tenant) {
        this.factory = factory;
        this.key = k;
        this.value = tenant;

        tenantId = new JEditField("tenantId", (tenant != null) ? tenant.getTenantId() : "");
        fields.put("tenantId", tenantId);

        description = new JEditField("description", (tenant != null) ? tenant.getDescription() : "");
        fields.put("description", description);

        releaseGroupKey = new JEditRef(factory, "releaseGroupKey", "ReleaseGroup",
                (tenant != null) ? (tenant.getReleaseGroupKey() != null) ? tenant.getReleaseGroupKey().getKey() : "" : "");
        fields.put("releaseGroupKey", releaseGroupKey);

        alternateReleaseGroupKey = new JEditRef(factory, "alternateReleaseGroupKey", "ReleaseGroup",
                (tenant != null) ? (tenant.getAlternateReleaseGroupKey() != null) ? tenant.getAlternateReleaseGroupKey().getKey() : "" : "");
        fields.put("alternateReleaseGroupKey", alternateReleaseGroupKey);

        tenantKey = new JEditKeyField("key", (k != null) ? k : "");
        fields.put("key", tenantKey);

    }

    @Override
    public JObjectFields<Tenant, TenantFilter> copy() {
        return new JFieldsTenant(factory, key, value);
    }

    @Override
    public String shortName(Tenant v) {
        return v.getTenantId();
    }

    @Override
    public Tenant fieldsToObject() {
        return Tenant.create(tenantId.getValue(),
                description.getValue(),
                Key.key(releaseGroupKey.getValue(), "userKey"),
                Key.key(alternateReleaseGroupKey.getValue(), "userKey"));
    }

    @Override
    public Map<String, JField> objectFields() {
        return fields;
    }

    @Override
    public Key key() {
        return Key.key(tenantKey.getValue(), "tenantKey");
    }

    @Override
    public TenantFilter fieldsToFilter() {
        TenantFilter filter = TenantFilter.create(tenantId.getValue(),
                description.getValue(),
                FilterUtils.nullIfEmpty(Key.key(releaseGroupKey.getValue(), "userKey")),
                FilterUtils.nullIfEmpty(Key.key(alternateReleaseGroupKey.getValue(), "userKey")), 0, 100);
        return filter;
    }

    @Override
    public void update(Key key, Tenant v) {
        tenantKey.setValue(key.getKey());
        tenantId.setValue(v.getTenantId());
        description.setValue(v.getDescription());
        releaseGroupKey.setValue(v.getReleaseGroupKey().getKey());
        alternateReleaseGroupKey.setValue(v.getAlternateReleaseGroupKey().getKey());
    }

    @Override
    public void updateFilter(Key key, Tenant v) {
        tenantId.setValue(v.getTenantId());
        description.setValue(v.getDescription());
        releaseGroupKey.setValue(v.getReleaseGroupKey().getKey());
        alternateReleaseGroupKey.setValue(v.getAlternateReleaseGroupKey().getKey());
    }

    @Override
    public Key key(String key) {
        return Key.key(key, "tenantKey");
    }

    @Override
    public Tenant valueFromJsonString(String value) {
        return Tenant.fromJson(value);
    }

    @Override
    public String valueAsJsonString(Tenant value) {
        return new JSONObject(value).toString();
    }

    @Override
    public String filterAsJsonString(TenantFilter filter) {
        return new JSONObject(filter).toString();
    }
}

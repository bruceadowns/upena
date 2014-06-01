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
package com.jivesoftware.os.upena.client.view.upena.shared;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;

public class Tenant extends JavaScriptObject {

    protected Tenant() {
    }

    public static Tenant create(String tenantId,
            String description,
            Key releaseGroupKey,
            Key alternateReleaseGroupKey) {
        Tenant v = GWT.create(Tenant.class);
        v.setObjectType("tenant");
        v.setTenantId(tenantId);
        v.setDescription(description);
        v.setReleaseGroupKey(releaseGroupKey);
        v.setAlternateReleaseGroupKey(alternateReleaseGroupKey);
        return v;
    }

    public static Tenant fromJson(String json) {
        return (Tenant) JsonUtils.safeEval(json);
    }

    public final native void setObjectType(String value) /*-{ return this.objectType = value; }-*/;

    public final native String getObjectType() /*-{ return this.objectType; }-*/;


    public final native String getTenantId() /*-{ return this.tenantId; }-*/;

    public final native void setTenantId(String value) /*-{ this.tenantId = value; }-*/;

    public final native String getDescription() /*-{ return this.description; }-*/;

    public final native void setDescription(String value) /*-{ this.description = value; }-*/;

    public final native Key getReleaseGroupKey() /*-{ return this.releaseGroupKey; }-*/;

    public final native void setReleaseGroupKey(Key value) /*-{ this.releaseGroupKey = value; }-*/;

    public final native Key getAlternateReleaseGroupKey() /*-{ return this.alternateReleaseGroupKey; }-*/;

    public final native void setAlternateReleaseGroupKey(Key value) /*-{ this.alternateReleaseGroupKey = value; }-*/;

}

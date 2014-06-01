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

public class TenantFilter extends JavaScriptObject {

    protected TenantFilter() {
    }

    public static TenantFilter create(String tenantId,
            String description,
            Key releaseGroupKey,
            Key alternateReleaseGroupKey,
            int start,
            int count) {
        TenantFilter v = GWT.create(TenantFilter.class);
        v.setTenantId(tenantId);
        v.setDescription(description);
        v.setReleaseGroupKey(releaseGroupKey);
        v.setAlternateReleaseGroupKey(alternateReleaseGroupKey);
        v.setStart(start);
        v.setCount(count);
        return v;
    }

    public static TenantFilter fromJson(String json) {
        return (TenantFilter) JsonUtils.safeEval(json);
    }

    public final native String getTenantId() /*-{ return this.tenantId; }-*/;

    public final native void setTenantId(String value) /*-{ this.tenantId = value; }-*/;

    public final native String getDescription() /*-{ return this.description; }-*/;

    public final native void setDescription(String value) /*-{ this.description = value; }-*/;

    public final native Key getReleaseGroupKey() /*-{ return this.releaseGroupKey; }-*/;

    public final native void setReleaseGroupKey(Key value) /*-{ this.releaseGroupKey = value; }-*/;

    public final native Key getAlternateReleaseGroupKey() /*-{ return this.alternateReleaseGroupKey; }-*/;

    public final native void setAlternateReleaseGroupKey(Key value) /*-{ this.alternateReleaseGroupKey = value; }-*/;

    public final native String getStart() /*-{ return this.start; }-*/;

    public final native void setStart(int value) /*-{ this.start = value; }-*/;

    public final native String getCount() /*-{ return this.count; }-*/;

    public final native void setCount(int value) /*-{ this.count = value; }-*/;

}


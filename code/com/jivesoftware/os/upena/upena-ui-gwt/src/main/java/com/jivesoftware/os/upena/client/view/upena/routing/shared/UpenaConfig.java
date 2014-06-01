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
package com.jivesoftware.os.upena.client.view.upena.routing.shared;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import java.util.Map;

/*
public final String context;
    public final String instanceKey;
    public final Map<String, String> properties;

    public UpenaConfig(String context,
            String instanceKey,
            Map<String, String> properties) {
        this.context = context;
        this.instanceKey = instanceKey;
        this.properties = properties;
    }


*/

public class UpenaConfig extends JavaScriptObject {

    protected UpenaConfig() {
    }

    public static UpenaConfig create(String context,
            String instanceKey,
            Map<String, String> properties) {
        UpenaConfig v = GWT.create(UpenaConfig.class);
        v.setObjectType(context);
        v.setName(instanceKey);
        v.setHostName(properties);
        return v;
    }

    public static UpenaConfig fromJson(String json) {
        return (UpenaConfig) JsonUtils.safeEval(json);
    }

    public final native void setInstanceKey(String value) /*-{ return this.objectType = value; }-*/;

    public final native String getInstanceKey() /*-{ return this.objectType; }-*/;
}
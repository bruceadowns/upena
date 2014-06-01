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

public class Service extends JavaScriptObject {

    protected Service() {
    }

    public static Service create(String name, String description) {
        Service v = GWT.create(Service.class);
        v.setObjectType("service"); // HACK!!
        v.setName(name);
        v.setDescription(description);
        return v;
    }

    public static Service fromJson(String json) {
        return (Service) JsonUtils.safeEval(json);
    }

    public final native void setObjectType(String value) /*-{ return this.objectType = value; }-*/;

    public final native String getObjectType() /*-{ return this.objectType; }-*/;

    public final native String getName() /*-{ return this.name; }-*/;

    public final native void setName(String value) /*-{ this.name = value; }-*/;

    public final native String getDescription() /*-{ return this.description; }-*/;

    public final native void setDescription(String value) /*-{ this.description = value; }-*/;

}

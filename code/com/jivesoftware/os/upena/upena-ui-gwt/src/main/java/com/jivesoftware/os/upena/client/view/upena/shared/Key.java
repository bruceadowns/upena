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
import com.google.gwt.json.client.JSONObject;

public class Key extends JavaScriptObject {

    protected Key() {
    }

    public static Key key(String k, String type) {
        if (k == null || type == null) {
            return null;
        }
        Key key = GWT.create(Key.class);
        key.setObjectType(type);
        key.setKey(k);
        return key;
    }

    public static String asJson(Key key) {
        if (key == null) {
            return null;
        }
        return new JSONObject(key).toString();
    }

    public static Key create(String json) {
        if (json == null) {
            return null;
        }
        return (Key) JsonUtils.safeEval(json);
    }

    public final native void setObjectType(String value) /*-{ return this.objectType = value; }-*/;

    public final native String getObjectType() /*-{ return this.objectType; }-*/;

    public final native String getKey() /*-{ return this.key; }-*/;

    public final native void setKey(String value) /*-{ this.key = value; }-*/;

}

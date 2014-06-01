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

public class ClusterFilter extends JavaScriptObject {

    protected ClusterFilter() {
    }

    public static ClusterFilter create(String name,
            String description,
            int start,
            int count) {
        ClusterFilter v = GWT.create(ClusterFilter.class);
        v.setName(name);
        v.setDescription(description);
        v.setStart(start);
        v.setCount(count);
        return v;
    }

    public static ClusterFilter fromJson(String json) {
        return (ClusterFilter) JsonUtils.safeEval(json);
    }

    public final native String getName() /*-{ return this.name; }-*/;

    public final native void setName(String value) /*-{ this.name = value; }-*/;

    public final native String getDescription() /*-{ return this.description; }-*/;

    public final native void setDescription(String value) /*-{ this.description = value; }-*/;

    public final native String getStart() /*-{ return this.start; }-*/;

    public final native void setStart(int value) /*-{ this.start = value; }-*/;

    public final native String getCount() /*-{ return this.count; }-*/;

    public final native void setCount(int value) /*-{ this.count = value; }-*/;

}

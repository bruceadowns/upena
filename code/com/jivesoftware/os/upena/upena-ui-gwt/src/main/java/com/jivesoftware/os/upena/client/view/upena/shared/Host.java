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

public class Host extends JavaScriptObject {

    protected Host() {
    }

    public static Host create(String name,
            String hostName,
            int port,
            String workingDirectory,
            Key clusterKey) {
        Host v = GWT.create(Host.class);
        v.setObjectType("host");
        v.setName(name);
        v.setHostName(hostName);
        v.setPort(port);
        v.setWorkingDirectory(workingDirectory);
        v.setClusterKey(clusterKey);
        return v;
    }

    public static Host fromJson(String json) {
        return (Host) JsonUtils.safeEval(json);
    }

    public final native void setObjectType(String value) /*-{ return this.objectType = value; }-*/;

    public final native String getObjectType() /*-{ return this.objectType; }-*/;


    public final native String getName() /*-{ return this.name; }-*/;

    public final native void setName(String value) /*-{ this.name = value; }-*/;

    public final native String getHostName() /*-{ return this.hostName; }-*/;

    public final native void setHostName(String value) /*-{ this.hostName = value; }-*/;

    public final native int getPort() /*-{ return this.port; }-*/;

    public final native void setPort(int value) /*-{ this.port = value; }-*/;

    public final native String getWorkingDirectory() /*-{ return this.workingDirectory; }-*/;

    public final native void setWorkingDirectory(String value) /*-{ this.workingDirectory = value; }-*/;

    public final native Key getClusterKey() /*-{ return this.clusterKey; }-*/;

    public final native void setClusterKey(Key value) /*-{ this.clusterKey = value; }-*/;

    public final native String getStart() /*-{ return this.start; }-*/;

    public final native void setStart(int value) /*-{ this.start = value; }-*/;

    public final native String getCount() /*-{ return this.count; }-*/;

    public final native void setCount(int value) /*-{ this.count = value; }-*/;

}
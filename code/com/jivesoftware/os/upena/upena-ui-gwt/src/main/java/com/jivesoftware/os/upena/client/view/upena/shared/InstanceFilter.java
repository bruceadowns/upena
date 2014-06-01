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

public class InstanceFilter extends JavaScriptObject {

    protected InstanceFilter() {
    }

    public static InstanceFilter create(Key clusterKey,
            Key hostKey,
            Key serviceKey,
            Key releaseGroupKey,
            Integer logicalInstanceId,
            int start,
            int count) {
        InstanceFilter v = GWT.create(InstanceFilter.class);
        v.setClusterKey(clusterKey);
        v.setHostKey(hostKey);
        v.setServiceKey(serviceKey);
        v.setReleaseGroupKey(releaseGroupKey);
        v.setLogicalInstanceId(logicalInstanceId);

        v.setStart(start);
        v.setCount(count);
        return v;
    }

    public static ServiceFilter fromJson(String json) {
        return (ServiceFilter) JsonUtils.safeEval(json);
    }

    public final native Key getClusterKey() /*-{ return this.clusterKey; }-*/;

    public final native void setClusterKey(Key value) /*-{ this.clusterKey = value; }-*/;

    public final native Key getHostKey() /*-{ return this.hostKey; }-*/;

    public final native void setHostKey(Key value) /*-{ this.hostKey = value; }-*/;

    public final native Key getServiceKey() /*-{ return this.serviceKey; }-*/;

    public final native void setServiceKey(Key value) /*-{ this.serviceKey = value; }-*/;

    public final native Key getReleaseGroupKey() /*-{ return this.releaseGroupKey; }-*/;

    public final native void setReleaseGroupKey(Key value) /*-{ this.releaseGroupKey = value; }-*/;

    public final native Integer getLogicalInstanceId() /*-{ return this.logicalInstanceId; }-*/;

    public final native void setLogicalInstanceId(Integer value) /*-{ this.logicalInstanceId = value; }-*/;



    public final native String getStart() /*-{ return this.start; }-*/;

    public final native void setStart(int value) /*-{ this.start = value; }-*/;

    public final native String getCount() /*-{ return this.count; }-*/;

    public final native void setCount(int value) /*-{ this.count = value; }-*/;
}

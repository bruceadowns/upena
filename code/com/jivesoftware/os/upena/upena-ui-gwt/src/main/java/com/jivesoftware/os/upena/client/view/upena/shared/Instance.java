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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Instance {

    public static final String PORT_MAIN = "main";
    public static final String PORT_MANAGE = "manage";
    public static final String PORT_DEBUG = "debug";
    public static final String PORT_JMX = "jmx";
    public Key clusterKey;
    public Key hostKey;
    public Key serviceKey;
    public Key releaseGroupKey;
    public int instanceId;
    public boolean enabled;
    public boolean locked;
    public Map<String, Port> ports = new HashMap<String, Port>();

    public Instance(Key clusterKey,
            Key hostKey,
            Key serviceKey,
            Key releaseGroupKey,
            int instanceId,
            boolean enabled,
            boolean locked) {
        this.clusterKey = clusterKey;
        this.hostKey = hostKey;
        this.serviceKey = serviceKey;
        this.releaseGroupKey = releaseGroupKey;
        this.instanceId = instanceId;
        this.enabled = enabled;
        this.locked = locked;
    }

    public static class Port implements Serializable {

        public int port;
        public Map<String, String> properties = new HashMap<String, String>();

        public Port() {
        }

        public Port(int port, Map<String, String> properties) {
            this.port = port;
            this.properties = properties;
        }

        @Override
        public String toString() {
            return "Port{" + "port=" + port + ", properties=" + properties + '}';
        }
    }
}
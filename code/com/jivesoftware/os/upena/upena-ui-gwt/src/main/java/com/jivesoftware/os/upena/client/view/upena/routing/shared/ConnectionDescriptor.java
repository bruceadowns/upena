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

import java.util.Map;

public class ConnectionDescriptor {

    private final String host;
    private final int port;
    private final Map<String, String> properties;

    public ConnectionDescriptor(String host,
            int port,
            Map<String, String> properties) {
        this.host = host;
        this.port = port;
        this.properties = properties;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        return "ConnectionDescriptor{"
                + "host=" + host
                + ", port=" + port
                + ", properties=" + properties
                + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + hashCode(this.host);
        hash = 31 * hash + this.port;
        hash = 31 * hash + hashCode(this.properties);
        return hash;
    }

    public static int hashCode(Object o) {
        return o != null ? o.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConnectionDescriptor other = (ConnectionDescriptor) obj;
        if (!equals(this.host, other.host)) {
            return false;
        }
        if (this.port != other.port) {
            return false;
        }
        if (!equals(this.properties, other.properties)) {
            return false;
        }
        return true;
    }

    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }
}

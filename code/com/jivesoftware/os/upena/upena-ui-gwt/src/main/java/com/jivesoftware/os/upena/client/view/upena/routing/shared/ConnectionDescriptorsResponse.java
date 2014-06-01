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

import java.util.List;

public class ConnectionDescriptorsResponse {

    private final int returnCode;
    private final List<String> messages;
    private final String releaseGroup;
    private final List<ConnectionDescriptor> connections;
    private final String alternatReleaseGroup;
    private final List<ConnectionDescriptor> alternateConnections;

    public ConnectionDescriptorsResponse(int returnCode,
            List<String> messages,
            String releaseGroup,
            List<ConnectionDescriptor> connections,
            String alternatReleaseGroup,
            List<ConnectionDescriptor> alternateConnections) {
        this.returnCode = returnCode;
        this.messages = messages;
        this.releaseGroup = releaseGroup;
        this.connections = connections;
        this.alternatReleaseGroup = alternatReleaseGroup;
        this.alternateConnections = alternateConnections;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public List<String> getMessages() {
        return messages;
    }

    public String getReleaseGroup() {
        return releaseGroup;
    }

    public List<ConnectionDescriptor> getConnections() {
        return connections;
    }

    @Override
    public String toString() {
        return "ConnectionDescriptorsResponse{"
                + "returnCode=" + returnCode
                + ", messages=" + messages
                + ", releaseGroup=" + releaseGroup
                + ", connections=" + connections
                + ", alternatReleaseGroup=" + alternatReleaseGroup
                + ", alternateConnections=" + alternateConnections
                + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.returnCode;
        hash = 29 * hash + hashCode(this.messages);
        hash = 29 * hash + hashCode(this.releaseGroup);
        hash = 29 * hash + hashCode(this.connections);
        hash = 29 * hash + hashCode(this.alternatReleaseGroup);
        hash = 29 * hash + hashCode(this.alternateConnections);
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
        final ConnectionDescriptorsResponse other = (ConnectionDescriptorsResponse) obj;
        if (this.returnCode != other.returnCode) {
            return false;
        }
        if (!equals(this.messages, other.messages)) {
            return false;
        }
        if (!equals(this.releaseGroup, other.releaseGroup)) {
            return false;
        }
        if (!equals(this.connections, other.connections)) {
            return false;
        }
        if (!equals(this.alternatReleaseGroup, other.alternatReleaseGroup)) {
            return false;
        }
        if (!equals(this.alternateConnections, other.alternateConnections)) {
            return false;
        }
        return true;
    }

    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

}

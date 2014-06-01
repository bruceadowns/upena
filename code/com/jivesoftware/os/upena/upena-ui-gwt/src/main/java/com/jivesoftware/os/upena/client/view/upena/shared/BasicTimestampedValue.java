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

public class BasicTimestampedValue<V> {

    private final long timestamp;
    private final boolean tombstoned;
    private final V value;

    public BasicTimestampedValue(
            V value,
            long timestamp,
            boolean tombstoned) {
        this.timestamp = timestamp;
        this.value = value;
        this.tombstoned = tombstoned;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean getTombstoned() {
        return tombstoned;
    }

    public V getValue() {
        return value;
    }

}

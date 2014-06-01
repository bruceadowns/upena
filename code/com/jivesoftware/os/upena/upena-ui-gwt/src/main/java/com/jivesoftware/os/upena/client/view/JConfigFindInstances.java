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
package com.jivesoftware.os.upena.client.view;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.jivesoftware.os.upena.client.view.upena.shared.Key;

public class JConfigFindInstances extends HorizontalPanel {
    //Color color;
    JEditRef clusterId;
    JEditRef hostId;
    JEditRef serviceId;
    JEditRef releaseGroupId;
    TextBox instanceId;

    public JConfigFindInstances(JObjectFactory factory, final Runnable changed) {
        clusterId = new JEditRef(factory, "cluster", "Cluster", "");
        add(clusterId.getEditor(40, new IPicked() {
            @Override
            public void picked(Key key, Object v) {
                if (changed != null) {
                    changed.run();
                }
            }
        }));
        clusterId.setValue("");
        hostId = new JEditRef(factory, "host", "Host", "");
        add(hostId.getEditor(40, new IPicked() {
            @Override
            public void picked(Key key, Object v) {
                if (changed != null) {
                    changed.run();
                }
            }
        }));
        hostId.setValue("");
        serviceId = new JEditRef(factory, "service", "Service", "");
        add(serviceId.getEditor(40, new IPicked() {
            @Override
            public void picked(Key key, Object v) {
                if (changed != null) {
                    changed.run();
                }
            }
        }));
        serviceId.setValue("");
        releaseGroupId = new JEditRef(factory, "release-group", "ReleaseGroup", "");
        add(releaseGroupId.getEditor(40, new IPicked() {
            @Override
            public void picked(Key key, Object v) {
                if (changed != null) {
                    changed.run();
                }
            }
        }));
        releaseGroupId.setValue("");
        instanceId = new TextBox();
        instanceId.addChangeHandler(new ChangeHandler() {

            @Override
            public void onChange(ChangeEvent event) {
                if (changed != null) {
                    changed.run();
                }

            }
        });
        add(instanceId);
        Button clear = UV.iconButton("clear-left");
        clear.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                clusterId.setValue("");
                        hostId.setValue("");
                        serviceId.setValue("");
                        releaseGroupId.setValue("");
                        instanceId.setText("");
                        if (changed != null) {
                    changed.run();
                }
            }
        });
        add(clear);
    }

}

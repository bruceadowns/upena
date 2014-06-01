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

import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.jivesoftware.os.upena.client.view.upena.shared.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class JEditReleaseGroupsField implements JField {

    JObjectFactory factory;
    public String name;
    public Map<Key, Key> serviceKeyToReleaseGroupKey;
    public Grid editField;
    public Label viewer;

    public JEditReleaseGroupsField(JObjectFactory factory, String name, Map<Key, Key> field) {
        this.factory = factory;
        this.name = name;
        if (field == null) {
            field = new HashMap<Key, Key>();
        }
        this.serviceKeyToReleaseGroupKey = field;
    }

    @Override
    public <V, F> Column getColumn() {
        EditTextCell cell = new EditTextCell();
        Column<JObjectFields<V, F>, String> column = new Column<JObjectFields<V, F>, String>(cell) {
            @Override
            public String getValue(JObjectFields<V, F> v) {
                JEditReleaseGroupsField e = (JEditReleaseGroupsField) v.objectFields().get(name);
                return e.serviceKeyToReleaseGroupKey.toString();
            }
        };
        return column;
    }

    @Override
    public boolean isFilterable() {
        return false;
    }

    @Override
    public Widget getEditor(int w, final IPicked picked) {
        editField = new Grid();
        rebuild();
        return editField;
    }

    void rebuild() {
        if (editField != null) {

            Set<Map.Entry<Key, Key>> entrySet = serviceKeyToReleaseGroupKey.entrySet();
            int rows = entrySet.size() + 2;
            editField.clear();
            editField.resize(rows, 3);

            editField.setWidget(0, 0, new Label("Service"));
            editField.setWidget(0, 1, new Label("Release"));
            editField.setWidget(0, 2, new Label(""));

            int row = 1;

            for (Map.Entry<Key, Key> entry : entrySet) {
                final Key serviceKey = entry.getKey();
                final Key releaseGroupKey = entry.getValue();
                final JEditRef viewService = new JEditRef(factory, "service", "Service", "");
                editField.setWidget(row, 0, viewService.getViewer(50));
                viewService.setValue(serviceKey.getKey());

                final JEditRef release = new JEditRef(factory, "release", "ReleaseGroup", "");
                editField.setWidget(row, 1, release.getEditor(40, new IPicked() {
                    @Override
                    public void picked(Key key, Object v) {
                        serviceKeyToReleaseGroupKey.put(serviceKey, key);
                        rebuild();
                    }
                }));
                release.setValue(releaseGroupKey.getKey());

                Button b = UV.iconButton("clear-left");
                b.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        serviceKeyToReleaseGroupKey.remove(serviceKey);
                        rebuild();

                    }
                });
                editField.setWidget(row, 2, b);

                row++;
            }

            final JEditRef service = new JEditRef(factory, "service", "Service", "");
            editField.setWidget(row, 0, service.getEditor(40, new IPicked() {
                @Override
                public void picked(Key key, Object v) {
                    //refresh();
                }
            }));

            final JEditRef release = new JEditRef(factory, "release", "ReleaseGroup", "");
            editField.setWidget(row, 1, release.getEditor(40, new IPicked() {
                @Override
                public void picked(Key key, Object v) {
                    //refresh();
                }
            }));

            Button b = new Button("add");
            b.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    serviceKeyToReleaseGroupKey.put(Key.key(service.getValue() ,"service"), Key.key(release.getValue(), "userKey"));
                    rebuild();

                }
            });
            editField.setWidget(row, 2, b);

        }
    }

    @Override
    public Widget getViewer(int w) {
        viewer = new Label(serviceKeyToReleaseGroupKey.toString());
        return viewer;
    }

    @Override
    public void clear() {
        Map<Key, Key> empty = new HashMap<Key, Key>();
        setValue(empty);
    }

    public void setValue(Map<Key, Key> value) {
        serviceKeyToReleaseGroupKey = value;
        rebuild();
    }

    @Override
    public Widget getClear(final IPicked picked) {
        Button b = UV.iconButton("clear-left");
        b.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                clear();
                if (picked != null) {
                    picked.picked(null, null);
                }
            }
        });
        return b;
    }

    @Override
    public String name() {
        return name;
    }
}

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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.jivesoftware.os.upena.client.view.upena.shared.Instance;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class JEditPortsField implements JField {

    JObjectFactory factory;
    public String name;
    public Map<String, Instance.Port> field;
    public Grid editField;
    public Widget viewer;

    public JEditPortsField(JObjectFactory factory, String name, Map<String, Instance.Port> field) {
        this.factory = factory;
        this.name = name;
        if (field == null) {
            field = new HashMap<String, Instance.Port>();
        }
        this.field = field;
    }

    @Override
    public <V, F> Column getColumn() {
        EditTextCell cell = new EditTextCell();
        Column<JObjectFields<V, F>, String> column = new Column<JObjectFields<V, F>, String>(cell) {
            @Override
            public String getValue(JObjectFields<V, F> v) {
                JEditPortsField e = (JEditPortsField) v.objectFields().get(name);
                return e.field.toString();
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
            editField.clear();
            Set<Map.Entry<String, Instance.Port>> entrySet = field.entrySet();

            editField.resize(entrySet.size() + 2, 3);

            int row = 0;
            editField.setWidget(row, 0, new Label("PortName"));
            editField.setWidget(row, 1, new Label("PortNumber"));
            editField.setWidget(row, 2, new Label(""));
            row++;
            for (Map.Entry<String, Instance.Port> entry : field.entrySet()) {
                final Instance.Port port = entry.getValue();
                final TextBox portName = new TextBox();
                portName.setText(entry.getKey());
                editField.setWidget(row, 0, portName);
                final TextBox portNumber = new TextBox();
                portNumber.setText("" + port.port);
                editField.setWidget(row, 1, portNumber);

                Button b = UV.iconButton("clear-left");
                b.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        field.remove(portName.getText());
                        rebuild();

                    }
                });
                editField.setWidget(row, 2, b);
                row++;
            }

            final TextBox portName = new TextBox();
            editField.setWidget(row, 0, portName);
            final TextBox portNumber = new TextBox();
            editField.setWidget(row, 1, portNumber);
            Button b = new Button("add");
            b.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    Map<String, String> properties = new HashMap<String, String>();
                    Instance.Port port = new Instance.Port(Integer.parseInt(portNumber.getText()), properties);
                    field.put(portName.getText(), port);
                    rebuild();

                }
            });
            editField.setWidget(row, 2, b);

        }
    }

    @Override
    public Widget getViewer(int w) {
        viewer = new Label(field.toString());
        return viewer;
    }

    @Override
    public void clear() {
        Map<String, Instance.Port> empty = new HashMap<String, Instance.Port>();
        setValue(empty);
    }

    public void setValue(Map<String, Instance.Port> value) {
        field = value;
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

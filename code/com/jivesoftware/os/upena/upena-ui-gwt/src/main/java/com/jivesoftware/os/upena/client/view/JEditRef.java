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
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.jivesoftware.os.upena.client.view.upena.shared.Key;

public class JEditRef implements JField {

    public JObjectFactory factory;
    public String name;
    public String valueClass;
    public String ref;
    public String viewValue;
    public Button editField;
    public Label viewField;

    public JEditRef(JObjectFactory factory, String name, String valueClass, String ref) {
        this.factory = factory;
        this.name = name;
        this.valueClass = valueClass;
        this.ref = ref;
        this.viewValue = "null";
    }

    @Override
    public <V, F> Column getColumn() {
        EditTextCell cell = new EditTextCell();
        Column<JObjectFields<V, F>, String> column = new Column<JObjectFields<V, F>, String>(cell) {
            @Override
            public String getValue(JObjectFields<V, F> v) {
                JEditRef e = (JEditRef) v.objectFields().get(name);
                return e.ref;
            }
        };
        return column;
    }

    @Override
    public boolean isFilterable() {
        return true;
    }

    @Override
    public Widget getEditor(int w, final IPicked picked) {
        editField = new Button(viewValue);
        editField.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                String title = "Choose a " + valueClass;

                final DecoratedPopupPanel d = new DecoratedPopupPanel(true);
                JObject<?, ?> create = factory.create(valueClass, false, new IPicked() {
                    @Override
                    public void picked(Key key, Object v) {
                        d.setVisible(false);
                        if (key == null) {
                            setValue("");
                        } else {
                            setValue(key.getKey());
                        }
                        if (picked != null) {
                            picked.picked(key, v);
                        }
                    }
                });

                VerticalPanel vp = new VerticalPanel();
                vp.add(new Label(title));
                vp.add(create);

                d.add(vp);

                Widget source = (Widget) event.getSource();
                int left = source.getAbsoluteLeft();
                int top = source.getAbsoluteTop() + source.getOffsetHeight();
                d.setPopupPosition(left, top);
                d.show();
                create.find(null);
            }
        });
        return editField;
    }

    @Override
    public Widget getViewer(int w) {
        viewField = new Label(viewValue);
        return viewField;
    }

    public String getValue() {
        return ref;
    }

    @Override
    public void clear() {
        setValue("");
    }

    public void setValue(String value) {
        ref = value;
        if (value == null || value.length() == 0) {
            viewValue = "null";
            if (editField != null) {
                editField.setText("Pick a " +valueClass);
            }
            if (viewField != null) {
                viewField.setText("Pick a " + valueClass);
            }
        } else {
            final JObject vobjects = factory.create(valueClass, false, null);
            vobjects.get(vobjects.objectFields.key(value), new IPicked() {
                @Override
                public void picked(Key key, Object v) {
                    if (v != null) {
                        String shortName = vobjects.objectFields.shortName(v);
                        viewValue = shortName;
                        if (editField != null) {
                            editField.setText(shortName);
                        }
                        if (viewField != null) {
                            viewField.setText(viewValue);
                        }
                    }
                }
            });
        }
    }

    @Override
    public Widget getClear(final IPicked picked) {
        Button b = UV.iconButton("clear-left");
        b.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                setValue("");
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
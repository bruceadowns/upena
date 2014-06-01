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

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;

class JEditBooleanField implements JField {

    public String name;
    public String field;
    public ToggleButton toggle;
    public Label viewer;

    public JEditBooleanField(String name, String field) {
        this.name = name;
        this.field = field;
    }

    @Override
    public <V,F> Column getColumn() {
        CheckboxCell cell = new CheckboxCell(true, false);
        Column<JObjectFields<V, F>, Boolean> column = new Column<JObjectFields<V, F>, Boolean>(cell) {
            @Override
            public Boolean getValue(JObjectFields<V, F> v) {
                JEditBooleanField e = (JEditBooleanField) v.objectFields().get(name);
                return Boolean.parseBoolean(e.field);
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
        toggle = new ToggleButton((field.length() == 0) ? "false" : field);
        toggle.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                setValue(Boolean.toString(toggle.isDown()));
                if (picked != null) {
                    picked.picked(null, null);
                }

            }
        });
        return toggle;
    }

    @Override
    public Widget getViewer(int w) {
        viewer = new Label(field);
        return viewer;
    }

    public String getValue() {
        return field;
    }

    public void setValue(String value) {
        field = value;
        if (toggle != null) {
            if (value.length() > 0) {
                toggle.setText(value);
                toggle.setDown(Boolean.parseBoolean(value));
            } else {
                toggle.setText("null");
            }
        }
        if (viewer != null) {
            viewer.setText(value);
            //viewer.revalidate();
        }
    }

    @Override
    public void clear() {
        setValue("");
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

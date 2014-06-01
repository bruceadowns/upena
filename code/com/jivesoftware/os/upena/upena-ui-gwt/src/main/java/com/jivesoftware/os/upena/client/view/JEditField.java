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
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

class JEditField implements JField {

    public String name;
    public String field;
    public TextBox editField;
    public Label viewer;

    public JEditField(String name, String field) {
        this.name = name;
        this.field = field;
    }

    @Override
    public <V, F> Column getColumn() {
        EditTextCell cell = new EditTextCell();
        Column<JObjectFields<V, F>, String> column = new Column<JObjectFields<V, F>, String>(cell) {
            @Override
            public String getValue(JObjectFields<V, F> v) {
                JEditField e = (JEditField) v.objectFields().get(name);
                return e.field;
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
        editField = new TextBox(); //, w);
        editField.setText(field);
        editField.addChangeHandler(new ChangeHandler() {

            @Override
            public void onChange(ChangeEvent event) {
                if (picked != null) {
                    picked.picked(null, null);
                }
            }
        });
        return editField;
    }

    @Override
    public Widget getViewer(int w) {
        viewer = new Label(field);
        return viewer;
    }

    public String getValue() {
        field = editField.getText();
        return field;
    }

    @Override
    public void clear() {
        setValue("");
    }

    public void setValue(String value) {
        field = value;
        if (editField != null) {
            editField.setText(value);
            //editField.revalidate();
        }
        if (viewer != null) {
            viewer.setText(value);
            //viewer.revalidate();
        }
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

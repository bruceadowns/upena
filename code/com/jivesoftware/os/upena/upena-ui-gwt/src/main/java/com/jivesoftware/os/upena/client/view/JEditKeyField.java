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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class JEditKeyField implements JField {

    public String name;
    public String field;
    public Button editField;

    public JEditKeyField(String name, String field) {
        this.name = name;
        this.field = field;
    }

    @Override
    public <V, F> Column getColumn() {
        EditTextCell cell = new EditTextCell();
        Column<JObjectFields<V, F>, String> column = new Column<JObjectFields<V, F>, String>(cell) {
            @Override
            public String getValue(JObjectFields<V, F> v) {
                JEditKeyField e = (JEditKeyField) v.objectFields().get(name);
                return e.field;
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
        editField = new Button();
        editField.setText(field);
        editField.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (picked != null) {
                    picked.picked(null, null);
                }
                //StringSelection selection = new StringSelection(field);
                //Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                //clipboard.setContents(selection, selection);
            }
        });
        return editField;
    }

    @Override
    public Widget getViewer(int w) {
        return new Label(field);
    }

    public String getValue() {
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

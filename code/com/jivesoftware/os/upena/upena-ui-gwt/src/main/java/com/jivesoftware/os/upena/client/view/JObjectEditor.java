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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.jivesoftware.os.upena.client.view.upena.shared.Key;
import java.util.Map;

public class JObjectEditor<V, F>
        extends Grid {

    public static final int CREATE = 0;
    public static final int UPDATE = 1;
    final JObjectFields<V, F> objectFields;
    final JExecutor<V, F> executor;
    final IPicked<V> picked;
    Button create;
    Button update;
    final int mode;

    public JObjectEditor(
            final int mode,
            final PopupPanel popupPanel,
            final Panel viewResults,
            final JObjectFields<V, F> objectFields,
            final JExecutor<V, F> executor,
            final IPicked<V> picked) {

        this.mode = mode;
        this.objectFields = objectFields;
        this.executor = executor;
        this.picked = picked;

        HorizontalPanel inputMenu = new HorizontalPanel();

        if (mode == CREATE) {
            create = new Button(" create ");
            create.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    executor.create(objectFields, picked);
                    if (popupPanel != null) {
                        popupPanel.hide();
                    }
                }
            });

            inputMenu.add(create);
        }

        if (mode == UPDATE) {

            update = new Button(" update ");
            update.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    executor.update(objectFields, picked);
                    if (popupPanel != null) {
                        popupPanel.hide();
                    }
                }
            });
            inputMenu.add(update);
        }
        resize(2, 1);
        setWidget(0, 0, inputView());
        setWidget(1, 0, inputMenu);

    }

    public void get(Key key, final Panel viewResults, IPicked<V> picked) {
        executor.get(objectFields, key, viewResults, picked);
    }

    final public Panel inputView() {
        Map<String, JField> fields = objectFields.objectFields();
        Grid grid = new Grid(fields.values().size(), 3);
        int row = 0;
        for (JField f : fields.values()) {
            if (mode == CREATE && f instanceof JEditKeyField) {
                f.getClear(null);
                continue;
            }
            grid.setWidget(row, 0, new Label(f.name()));
            grid.setWidget(row, 1, f.getEditor(50, new IPicked() {
                @Override
                public void picked(Key key, Object v) {
                }
            }));
            grid.setWidget(row, 2, f.getClear(new IPicked() {
                @Override
                public void picked(Key key, Object v) {
                }
            }));
            row++;
        }

        return grid;
    }
}

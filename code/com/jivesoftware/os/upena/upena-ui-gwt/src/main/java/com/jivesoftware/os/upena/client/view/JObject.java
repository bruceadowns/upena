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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.jivesoftware.os.upena.client.view.upena.shared.Key;
import java.util.Map;

public class JObject<V, F>
        extends Grid {

    final Panel viewResults;
    final JObjectFields<V, F> objectFields;
    final JExecutor<V, F> executor;
    final IPicked<V> picked;
    final Button reset;
    final Button filter;
    final TextBox query;
    final boolean hasPopup;

    public JObject(
            final JObjectFields<V, F> objectFields,
            final JExecutor<V, F> executor,
            final boolean hasPopup,
            final IPicked<V> picked) {

        this.viewResults = new VerticalPanel();
        this.objectFields = objectFields;
        this.executor = executor;
        this.picked = picked;
        this.hasPopup = hasPopup;



        Button create = new Button(" create ");
        create.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                DecoratedPopupPanel pp = new DecoratedPopupPanel(true);
                DockPanel dp = new DockPanel();
                dp.add(new Label("New "), DockPanel.NORTH);

                try {
                    JObjectEditor editor = new JObjectEditor(JObjectEditor.CREATE,
                            pp,
                            new VerticalPanel(),
                            objectFields.copy(),
                            executor,
                            new IPicked<V>() {
                                @Override
                                public void picked(Key key, V v) {
                                    find(null);
                                }
                            });
                    dp.add(editor, DockPanel.CENTER);
                } catch (Throwable x) {
                    String s = "";
                    for (StackTraceElement e : x.getStackTrace()) {
                        s += e.toString();
                    }
                    Window.alert(x.toString() + ":" + s);
                }
                pp.add(dp);

                Widget source = (Widget) event.getSource();
                int left = source.getAbsoluteLeft();
                int top = source.getAbsoluteTop() + source.getOffsetHeight();
                pp.setPopupPosition(left, top);
                pp.show();
            }
        });


        final DecoratedPopupPanel filterMenu = new DecoratedPopupPanel(true);
        filterMenu.add(inputView());
        filter = new Button(" filter ");
        filter.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                find(null);
                Widget source = (Widget) event.getSource();
                int left = source.getAbsoluteLeft();
                int top = source.getAbsoluteTop() + source.getOffsetHeight();
                filterMenu.setPopupPosition(left, top);
                filterMenu.show();
            }
        });

        query = new TextBox(); //"", 50);
        query.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                F filterObject = null;
                try {
                    filterObject = null; ////new ObjectMapper().readValue(query.getText(), objectFields.filterClass());
                } catch (Exception x) {
                    x.printStackTrace();
                }
                find(filterObject);

            }
        });

        reset = UV.iconButton("clear-left");
        reset.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                for (JField f : objectFields.objectFields().values()) {
                    f.clear();
                }
                find(null);

            }
        });

        Button refresh = UV.iconButton("refresh");
        refresh.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                find(null);
            }
        });

        Grid menu = new Grid();
        menu.resize(1, 5);
        HTMLTable.CellFormatter formatter = menu.getCellFormatter();
        formatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
        formatter.setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
        formatter.setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_LEFT);
        formatter.setHorizontalAlignment(0, 3, HasHorizontalAlignment.ALIGN_LEFT);
        formatter.setHorizontalAlignment(0, 4, HasHorizontalAlignment.ALIGN_LEFT);
        formatter.setWidth(0, 0, "100px");
        formatter.setWidth(0, 1, "100px");
        formatter.setWidth(0, 2, "400px");
        formatter.setWidth(0, 3, "64px");
        formatter.setWidth(0, 4, "64px");

        menu.setWidth("100%");
        menu.setWidget(0, 0, create);
        menu.setWidget(0, 1, filter);
        menu.setWidget(0, 2, query);
        menu.setWidget(0, 3, reset);
        menu.setWidget(0, 4, refresh);

        DecoratorPanel demenu = new DecoratorPanel();
        demenu.add(menu);

        resize(2, 1);
        formatter = getCellFormatter();
        formatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
        formatter.setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
        formatter.setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_LEFT);
        formatter.setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
        setWidget(0, 0, demenu);
        setWidget(1, 0, viewResults);

    }

    public void clear() {
    }

    public void find(final F filter) {
        F f = filter;
        if (filter == null) {
            f = objectFields.fieldsToFilter();
        }
        String filterString = "";
        try {
            filterString = objectFields.filterAsJsonString(f);
        } catch (Exception x) {
            filterString = x.getMessage();
        }
        query.setText(filterString);

        final F find = f;
        executor.find(find, objectFields, hasPopup, viewResults, new IPicked<V>() {
            @Override
            public void picked(Key key, V v) {
                find(find);
                if (picked != null) {
                    picked.picked(key, v);
                }
            }
        });

    }

    public void get(Key key, IPicked<V> picked) {
        executor.get(objectFields, key, viewResults, picked);
    }

    final public Panel inputView() {
        Map<String, JField> fields = objectFields.objectFields();
        Grid grid = new Grid(fields.size(), 3);
        int row = 0;
        for (JField f : fields.values()) {
            if (!f.isFilterable()) {
                continue;
            }
            grid.setWidget(row, 0, new Label(f.name()));
            Widget editor = f.getEditor(50, new IPicked() {
                @Override
                public void picked(Key key, Object v) {
                    find(null);
                }
            });
            grid.setWidget(row, 1, editor);
            grid.setWidget(row, 2, f.getClear(new IPicked() {
                @Override
                public void picked(Key key, Object v) {
                    find(null);
                }
            }));
            row++;
        }
        return grid;
    }
}

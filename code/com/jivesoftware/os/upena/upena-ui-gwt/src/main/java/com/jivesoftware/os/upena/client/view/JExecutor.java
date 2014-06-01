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
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.jivesoftware.os.upena.client.view.upena.shared.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JExecutor<V, F> {

    private final RequestHelperProvider requestHelperProvider;
    private final String context;

    public JExecutor(RequestHelperProvider requestHelperProvider, String context) {
        this.requestHelperProvider = requestHelperProvider;
        this.context = context;
    }

    public void get(final JObjectFields<V, F> objectFields, final Key key, final Panel viewResults, final IPicked<V> picked) {
        if (picked != null) {
            String keyAsString = objectFields.key().getKey();
            requestHelperProvider.get().postJson(keyAsString, "/upena/" + context + "/get", new JsonCallback() {

                @Override
                public void result(String jsonString) {
                    picked.picked(key, objectFields.valueFromJsonString(jsonString));
                }

                @Override
                public void error() {
                    picked.picked(null, null);
                }
            });
        }
    }

    public void remove(final JObjectFields<V, F> objectFields, final Panel viewResults) {
        final Key key = objectFields.key();
        requestHelperProvider.get().postJson(Key.asJson(key), "/upena/" + context + "/remove", new JsonCallback() {

            @Override
            public void result(String jsonString) {
                viewResults.clear();
                viewResults.add(new Label("Removed:" + key));
            }

            @Override
            public void error() {
                viewResults.clear();
                viewResults.add(new Label("Failed to remove:" + key));
            }
        });
    }

    public void create(final JObjectFields<V, F> objectFields, final IPicked<V> picked) {
        final V v = objectFields.fieldsToObject();
        requestHelperProvider.get().postJson(objectFields.valueAsJsonString(v), "/upena/" + context + "/add", new JsonCallback() {

            @Override
            public void result(final String r) {
                final Key key = objectFields.key(r);
                requestHelperProvider.get().postJson(Key.asJson(key), "/upena/" + context + "/get", new JsonCallback() {

                    @Override
                    public void result(String jsonString) {
                        if (jsonString != null) {
                            picked.picked(key, v);
                        }
                    }

                    @Override
                    public void error() {
                        Window.alert("Failed to get key:" + key);
                    }
                });

            }

            @Override
            public void error() {
                Window.alert("Failed to get add:" + v);
            }

        });
    }

    public void update(final JObjectFields<V, F> objectFields, final IPicked<V> picked) {
        final V v = objectFields.fieldsToObject();
        String objectKey = objectFields.key().getKey();
        requestHelperProvider.get().postJson(objectFields.valueAsJsonString(v), "/upena/" + context + "/update?key="+objectKey, new JsonCallback() {

            @Override
            public void result(final String r) {
                final Key key = objectFields.key(r);
                requestHelperProvider.get().postJson(Key.asJson(key), "/upena/" + context + "/get", new JsonCallback() {

                    @Override
                    public void result(String jsonString) {
                        if (jsonString != null) {
                            picked.picked(key, v);
                        }
                    }

                    @Override
                    public void error() {
                        Window.alert("Failed to get key:" + key);
                    }
                });
            }

            @Override
            public void error() {
                 Window.alert("Failed to get update:" + v);
            }

        });


//        V v = objectFields.fieldsToObject();
//        String objectKey = objectFields.key().getKey();
//        K key = requestHelperProvider.get().executeRequest(v, "/upena/" + context + "/update?key=" + objectKey, objectFields.keyClass(), null);
//        if (key != null) {
//            V result = requestHelperProvider.get().executeRequest(key, "/upena/" + context + "/get", objectFields.valueClass(), null);
//            if (result != null) {
//                picked.picked(key, v);
//            }
//        } else {
//            //JOptionPane.showMessageDialog(null, v.toString(), "Failed to Create", JOptionPane.WARNING_MESSAGE);
//        }

    }

    public void find(final F filter, final JObjectFields<V, F> objectFields, final boolean hasPopup, final Panel viewResults, final IPicked<V> picked) {

        requestHelperProvider.get().postJson(objectFields.filterAsJsonString(filter), "/upena/" + context + "/find", new JsonCallback() {

            @Override
            public void result(String jsonString) {
                viewResults.clear();
                final CellTable<JObjectFields<V, F>> table = new CellTable<JObjectFields<V, F>>();
                table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

                final SingleSelectionModel<JObjectFields<V, F>> selectionModel = new SingleSelectionModel<JObjectFields<V, F>>();
                //table.setSelectionModel(selectionModel, DefaultSelectionEventManager.<JObjectFields<V, F>> createCheckboxManager());

                table.setSelectionModel(selectionModel);
                table.addCellPreviewHandler(new CellPreviewEvent.Handler<JObjectFields<V, F>>() {

                    @Override
                    public void onCellPreview(CellPreviewEvent<JObjectFields<V, F>> event) {
                        if (Event.getTypeInt(event.getNativeEvent().getType()) == Event.ONCLICK) {

                            final JObjectFields<V, F> selected = event.getValue();

                            if (hasPopup) {
                                final VerticalPanel menu = new VerticalPanel();
                                Button menuItem = new Button("Filter");
                                menuItem.setWidth("100px");
                                menuItem.addClickHandler(new ClickHandler() {
                                    @Override
                                    public void onClick(ClickEvent event) {
                                        objectFields.updateFilter(selected.key(), selected.fieldsToObject());
                                        if (picked != null) {
                                            picked.picked(selected.key(), selected.fieldsToObject());
                                        }
                                    }
                                });
                                menu.add(menuItem);
                                menuItem = new Button("Edit");
                                menuItem.setWidth("100px");
                                menu.add(menuItem);
                                menuItem.addClickHandler(new ClickHandler() {
                                    @Override
                                    public void onClick(ClickEvent event
                                    ) {

                                        DecoratedPopupPanel pp = new DecoratedPopupPanel(true);
                                        DockPanel dp = new DockPanel();
                                        dp.add(new Label("Edit "), DockPanel.NORTH);

                                        try {
                                            JObjectEditor editor = new JObjectEditor(JObjectEditor.UPDATE,
                                                    pp,
                                                    new VerticalPanel(),
                                                    selected.copy(),
                                                    JExecutor.this,
                                                    picked);
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
                                menuItem = new Button("Copy");
                                menuItem.setWidth("100px");
                                menu.add(menuItem);
                                menuItem.addClickHandler(new ClickHandler() {
                                    @Override
                                    public void onClick(ClickEvent event) {

                                        DecoratedPopupPanel pp = new DecoratedPopupPanel(true);
                                        DockPanel dp = new DockPanel();
                                        dp.add(new Label("Copying "), DockPanel.NORTH);

                                        try {
                                            JObjectEditor editor = new JObjectEditor(JObjectEditor.CREATE,
                                                    pp,
                                                    new VerticalPanel(),
                                                    selected.copy(),
                                                    JExecutor.this,
                                                    picked);
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

                                menuItem = new Button("Remove");
                                menuItem.setWidth("100px");
                                menu.add(menuItem);
                                menuItem.addClickHandler(new ClickHandler() {
                                    @Override
                                    public void onClick(ClickEvent event
                                    ) {
                                        if (picked != null) {
                                            remove(selected, viewResults);
                                            picked.picked(null, null);
                                        }
                                    }
                                });

                                DecoratedPopupPanel pp = new DecoratedPopupPanel(true);
                                DockPanel dp = new DockPanel();

                                try {
                                    dp.add(menu, DockPanel.CENTER);
                                } catch (Throwable x) {
                                    String s = "";
                                    for (StackTraceElement e : x.getStackTrace()) {
                                        s += e.toString();
                                    }
                                    Window.alert(x.toString() + ":" + s);
                                }
                                pp.add(dp);

//                            menu.add(new Label("index:"+event.getIndex()));
//                            menu.add(new Label("column:"+event.getColumn()));
//                            menu.add(new Label("event:"+event.getNativeEvent()));
                                int x = table.getRowElement(event.getIndex()).getCells().getItem(event.getColumn()).getAbsoluteLeft();
                                int y = table.getRowElement(event.getIndex()).getCells().getItem(event.getColumn()).getAbsoluteTop();

//                            Widget source = (Widget) event.getSource();
//                            int left = source.getAbsoluteLeft();
//                            int top = source.getAbsoluteTop() + source.getOffsetHeight();
                                pp.setPopupPosition(x, y);
                                pp.show();

                            } else {
                                if (picked != null) {
                                    picked.picked(selected.key(), selected.fieldsToObject());
                                }
                            }
                        }
                    }
                });

                Column<JObjectFields<V, F>, Boolean> checkColumn = new Column<JObjectFields<V, F>, Boolean>(
                        new CheckboxCell(true, false)) {
                            @Override
                            public Boolean getValue(JObjectFields<V, F> object) {
                                // Get the value from the selection model.
                                return selectionModel.isSelected(object);
                            }
                        };
                table.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
                table.setColumnWidth(checkColumn, 40, Unit.PX);

                for (final JField field : objectFields.objectFields().values()) {
                    table.addColumn(field.getColumn(), field.name());
                }

                List<JObjectFields<V, F>> rows = new ArrayList<JObjectFields<V, F>>();
                JavaScriptObject safeEval = JsonUtils.safeEval(jsonString);
                JSONObject result = new JSONObject(safeEval);
                for (String key : result.keySet()) {
                    JSONObject timetampedValue = (JSONObject) result.get(key);
                    JSONValue tombstoned = timetampedValue.get("tombstoned");
                    if (tombstoned.isBoolean() != null && tombstoned.isBoolean().booleanValue() == false) {
                        JSONValue value = timetampedValue.get("value");
                        V v = objectFields.valueFromJsonString(value.toString());
                        JObjectFields<V, F> copy = objectFields.copy();
                        copy.update(objectFields.key(key), v);
                        rows.add(copy);
                    }
                }

                table.setRowCount(rows.size(), true);
                table.setRowData(0, rows);
                viewResults.add(table);
            }

            @Override
            public void error() {
                viewResults.clear();
                viewResults.add(new Label("No results"));
            }

        });

//        TreeMap<String, TimestampedValue<V>> results = requestHelperProvider.get().executeRequest(filter,
//                "/upena/" + context + "/find", objectFields.responseClass(), null);
//        if (results != null) {
//            viewResults.clear();
//
//            int columns = addTitles(viewResults, objectFields);
//
//            int count = 0;
//            for (final Map.Entry<String, TimestampedValue<V>> e : results.entrySet()) {
//                if (!e.getValue().getTombstoned()) {
//                    toListItem(viewResults, objectFields.key(e.getKey()), e.getValue().getValue(), objectFields, hasPopup, picked);
//                }
//                count++;
//            }
//        } else {
//            viewResults.clear();
//            viewResults.add(new Label("No results"));
//        }
    }

    class KeyValue<Key, V> {

        public final Key key;
        public final V value;

        public KeyValue(Key key, V value) {
            this.key = key;
            this.value = value;
        }

    }

    int addTitles(Panel viewResults, final JObjectFields<V, F> objectFields) {
        Collection<JField> values = objectFields.objectFields().values();
        HorizontalPanel m = new HorizontalPanel();
        for (JField f : values) {
            Label jLabel = new Label(f.name());
            viewResults.add(jLabel);
        }
        return values.size();
    }
}

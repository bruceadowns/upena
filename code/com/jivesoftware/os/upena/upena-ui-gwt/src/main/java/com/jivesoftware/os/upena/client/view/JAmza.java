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

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class JAmza extends DockPanel {

    RequestHelperProvider requestHelperProvider;
    Panel viewResults;
    TextBox hostName;
    TextBox port;

    public JAmza(RequestHelperProvider requestHelperProvider) {
        this.requestHelperProvider = requestHelperProvider;
        initComponents();
    }

    private void initComponents() {

        viewResults = new VerticalPanel();

        hostName = new TextBox(); //"", 120);

        port = new TextBox(); //"", 120);

        HorizontalPanel buttons = new HorizontalPanel();

        Button tableNames = new Button("List Tables");
        tableNames.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                tables();
            }
        });
        buttons.add(tableNames);

        Button list = new Button("List Ring");
        list.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                ring();

            }
        });
        buttons.add(list);

        Button add = new Button("Add Amza Host");
        add.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                addRingHost();
            }
        });
        buttons.add(add);

        Button remove = new Button("Remove Amza Remove");
        remove.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                removeRingHost();
            }
        });
        buttons.add(remove);

        Grid connection = new Grid(2, 2);

        connection.setWidget(0, 0, new Label("host-name"));
        connection.setWidget(0, 1, new Label("port"));
        connection.setWidget(1, 0, hostName);
        connection.setWidget(1, 1, port);

        add(connection, DockPanel.NORTH);
        add(buttons, DockPanel.NORTH);
        add(viewResults, DockPanel.CENTER);
        //ring();
    }

    public void tables() {
        RequestHelper requestHelper = requestHelperProvider.get();
        requestHelper.postJson("{}", "amza/tables", new JsonCallback() {

            @Override
            public void result(String jsonString) {
                viewResults.clear();
                JsArray<AmzaTable> tables = AmzaTables.create(jsonString);
                for (int i = 0; i < tables.length(); i++) {
                    AmzaTable table = tables.get(i);
                    viewResults.add(new Label("Table:" + table.getRingName() + " " + table.getTableName()));
                }
            }

            @Override
            public void error() {
                viewResults.clear();
                Window.confirm("Couldn't retrieve JSON");
            }
        });
    }

    static public class AmzaTable extends JavaScriptObject {

        protected AmzaTable() {
        }

        public static AmzaTable create(String json) {
            return (AmzaTable) JsonUtils.safeEval(json);
        }

        public final native String getRingName() /*-{ return this.ringName; }-*/;

        public final native void setRingName(String value) /*-{ this.ringName = value; }-*/;

        public final native String getTableName() /*-{ return this.tableName; }-*/;

        public final native void setTableName(String value) /*-{ this.tableName = value; }-*/;
    }

    static class AmzaTables extends JsArray<AmzaTable> {

        protected AmzaTables() {
        }

        public static AmzaTables create(String json) {
            return (AmzaTables) JsonUtils.safeEval(json);
        }

    }

    static public class AmzaRingHost extends JavaScriptObject {

        protected AmzaRingHost() {
        }

        public static AmzaRingHost create(String json) {
            return (AmzaRingHost) JsonUtils.safeEval(json);
        }

        public final native String getHost() /*-{ return this.host; }-*/;

        public final native void setHost(String value) /*-{ this.host = value; }-*/;

        public final native String getPort() /*-{ return this.port; }-*/;

        public final native void setPort(int value) /*-{ this.port = value; }-*/;
    }

    static class AmzaRingHosts extends JsArray<AmzaRingHost> {

        protected AmzaRingHosts() {
        }

        public static AmzaRingHosts create(String json) {
            return (AmzaRingHosts) JsonUtils.safeEval(json);
        }
    }

    public void ring() {

        requestHelperProvider.get().postJson("{}", "amza/ring", new JsonCallback() {

            @Override
            public void result(String jsonString) {
                viewResults.clear();
                JsArray<AmzaRingHost> tables = AmzaRingHosts.create(jsonString);
                for (int i = 0; i < tables.length(); i++) {
                    AmzaRingHost ringHost = tables.get(i);
                    viewResults.add(new Label("Amza host:" + ringHost.getHost() + " " + ringHost.getPort()));
                }
            }

            @Override
            public void error() {
                viewResults.clear();
                Window.confirm("Couldn't retrieve JSON");
            }
        });

    }

    public void addRingHost() {

        AmzaRingHost ringHost = GWT.create(AmzaRingHost.class);
        ringHost.setHost(hostName.getText());
        ringHost.setPort(Integer.parseInt(port.getText()));

        requestHelperProvider.get().postJson(new JSONObject(ringHost).toString(), "amza/ring/add", new JsonCallback() {

            @Override
            public void result(String jsonString) {
                viewResults.clear();
                ring();
            }

            @Override
            public void error() {
                viewResults.clear();
                Window.confirm("Couldn't retrieve JSON");
            }
        });
    }

    public void removeRingHost() {

        AmzaRingHost ringHost = AmzaRingHost.create("{}");
        ringHost.setHost(hostName.getText());
        ringHost.setPort(Integer.parseInt(port.getText()));

        requestHelperProvider.get().postJson(new JSONObject(ringHost).toString(), "amza/ring/remove", new JsonCallback() {

            @Override
            public void result(String jsonString) {
                viewResults.clear();
                ring();
            }

            @Override
            public void error() {
                viewResults.clear();
                Window.confirm("Couldn't retrieve JSON");
            }
        });
    }
}

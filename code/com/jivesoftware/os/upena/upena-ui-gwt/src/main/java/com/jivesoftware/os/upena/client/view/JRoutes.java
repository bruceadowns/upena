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

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jivesoftware.os.upena.client.view.upena.shared.Key;

public class JRoutes extends VerticalPanel {

    RequestHelperProvider requestHelperProvider;
    JObjectFactory factory;
    Panel viewResults;
    JEditRef tenantId;
    JEditRef instanceId;
    JEditRef connectToServiceNamed;
    TextBox portName;

    public JRoutes(RequestHelperProvider requestHelperProvider, JObjectFactory factory) {
        this.requestHelperProvider = requestHelperProvider;
        this.factory = factory;
        initComponents();
    }

    private void initComponents() {

        viewResults = new VerticalPanel();
        Grid m = new Grid(2, 5);

        m.setWidget(0, 0, new Label("tenant-id"));
        m.setWidget(0, 1, new Label("instance"));
        m.setWidget(0, 2, new Label("connect-to"));
        m.setWidget(0, 3, new Label("port-name"));
        m.setWidget(0, 4, new Label(""));

        tenantId = new JEditRef(factory, "tenant", "Tenant", "");
        m.setWidget(0, 0, tenantId.getEditor(40, new IPicked() {
            @Override
            public void picked(Key key, Object v) {
                refresh();
            }
        }));
        tenantId.setValue("");

        instanceId = new JEditRef(factory, "instance", "Instance", "");
        m.setWidget(0, 1, instanceId.getEditor(40, new IPicked() {
            @Override
            public void picked(Key key, Object v) {
                refresh();
            }
        }));
        instanceId.setValue("");

        connectToServiceNamed = new JEditRef(factory, "service", "Service", "");
        m.setWidget(0, 2, connectToServiceNamed.getEditor(40, new IPicked() {
            @Override
            public void picked(Key key, Object v) {
                refresh();
            }
        }));
        connectToServiceNamed.setValue("");

        portName = new TextBox();
        portName.setText("main"); //, 120);
        portName.addChangeHandler(new ChangeHandler() {

            @Override
            public void onChange(ChangeEvent event) {
                refresh();
            }
        });
        m.setWidget(0, 3, portName);

        Button refresh = UV.iconButton("refresh");
        refresh.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                refresh();
            }

        });
        m.setWidget(0, 4, refresh);

        DockPanel routes = new DockPanel();
        routes.add(m, DockPanel.NORTH);

        ScrollPanel scrollRoutes = new ScrollPanel();
        scrollRoutes.add(viewResults);

        add(scrollRoutes);

    }

    public void refresh() {

        //final MutableObject serviceName = new MutableObject("");
        //JExecutor<ServiceKey, Service, ServiceFilter> vExecutor = new JExecutor<ServiceKey, Service, ServiceFilter>(requestHelperProvider, "service");
//        final CountDownLatch latch = new CountDownLatch(1);
//        vExecutor.get(Service.class, new ServiceKey(connectToServiceNamed.getValue()), new VerticalPanel(), new IPicked<ServiceKey, Service>() {
//            @Override
//            public void picked(ServiceKey key, Service v) {
//                if (v != null) {
//                    System.out.println(v.name);
//                    //serviceName.setValue(v.name);
//                }
//                latch.countDown();
//            }
//        });
//        try {
//            latch.await(5, TimeUnit.SECONDS);
//        } catch (Exception x) {
//            viewResults.clear();
//            viewResults.add(new Label("Failed to find service name."));
//            //viewResults.revalidate();
//            return;
//        }

//        ConnectionDescriptorsRequest connectionDescriptorsRequest = new ConnectionDescriptorsRequest(tenantId.getValue(),
//                instanceId.getValue(), "serviceName.getValue().toString()", portName.getText());
//        ConnectionDescriptorsResponse connectionDescriptorsResponse = requestHelperProvider.get().executeRequest(connectionDescriptorsRequest,
//                "/upena/request/connections", ConnectionDescriptorsResponse.class, null);
//
//        if (connectionDescriptorsResponse != null) {
//            viewResults.clear();
//            int count = 0;
//
//            if (connectionDescriptorsResponse.getConnections() != null) {
//                for (ConnectionDescriptor cd : connectionDescriptorsResponse.getConnections()) {
//                    viewResults.add(new Label("Route: " + cd.getHost() + ":" + cd.getPort() + " " + cd.getProperties()));
//                    count++;
//                }
//            }
//            for (String e : connectionDescriptorsResponse.getMessages()) {
//                viewResults.add(new Label("Message:" + e));
//                count++;
//            }
//        } else {
//            viewResults.clear();
//        }
    }
}

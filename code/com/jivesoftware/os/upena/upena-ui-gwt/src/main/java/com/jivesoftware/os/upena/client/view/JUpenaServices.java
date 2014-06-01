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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.jivesoftware.os.upena.client.CustomPanel;

public class JUpenaServices extends VerticalPanel {

    RequestHelperProvider requestHelperProvider;
    JObjectFactory factory;
    Grid g = new Grid(1,2);

    public JUpenaServices(RequestHelperProvider requestHelperProvider, JObjectFactory factory) {
        this.requestHelperProvider = requestHelperProvider;
        this.factory = factory;
        initComponents();
    }

    private void initComponents() {

        Image image = new Image(GWT.getHostPageBaseURL() + "icons/cluster.png");
            image.setSize("100%", "64px");

        HTMLTable.CellFormatter formatter = g.getCellFormatter();
        formatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
        formatter.setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
        formatter.setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
        formatter.setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
        g.setCellPadding(10);

        final VerticalPanel vp = new VerticalPanel();
        JCluster cluster = new JCluster(requestHelperProvider, factory);
        vp.add(new ViewButton(cluster, "Status"));

        final JObject<?, ?> instances = factory.create("Instance", true, null);
        vp.add(new ViewButton(instances, "Instances"));
        vp.add(new ViewButton(factory.create("Cluster", true, null), "Clusters"));
        vp.add(new ViewButton(factory.create("ReleaseGroup", true, null), "Releases"));
        vp.add(new ViewButton(factory.create("Host", true, null), "Hosts"));
        vp.add(new ViewButton(factory.create("Service", true, null), "Services"));
        vp.add(new ViewButton(factory.create("Tenant", true, null), "Tenants"));
        JRoutes routes = new JRoutes(requestHelperProvider, factory);
        vp.add(new ViewButton(routes, "Routes"));

        JConfig config = new JConfig(requestHelperProvider, factory);
        vp.add(new ViewButton(config, "Config"));

        JAmza amza = new JAmza(requestHelperProvider);
        vp.add(new ViewButton(amza, "Admin"));

        CustomPanel cp = new CustomPanel("dp-bar");
        cp.add(vp);
        g.setWidget(0, 0, cp);

        cp = new CustomPanel("dp-bar");
        cp.add(instances);
        g.setWidget(0, 1, cp);

        cp = new CustomPanel("dp-menu");
        cp.add(image);

        add(cp);
        add(g);
        
        instances.clear();
        instances.find(null);
        setWidth("100%");

        for (String s : new String[]{"main", "banner", "pickpage", "seperator", "menu", "bar"}) {
            cp = new CustomPanel("dp-" + s);
            cp.add(new Label("dp-" + s));
            add(cp);
        }


    }

    class ViewButton extends Button implements ClickHandler {
        private final Widget widget;
        public ViewButton(Widget widget, String label) {
            super(label);
            CustomPanel cp = new CustomPanel("dp-bar");
            cp.add(widget);
            this.widget = cp;
            addClickHandler(this);
            setWidth("200");
        }

        @Override
        public void onClick(ClickEvent event) {
            g.setWidget(0, 1, widget);
            if (widget instanceof JObject) {
                JObject jObject = (JObject) widget;
                jObject.clear();
                jObject.find(null);
            }
        }
    }
}

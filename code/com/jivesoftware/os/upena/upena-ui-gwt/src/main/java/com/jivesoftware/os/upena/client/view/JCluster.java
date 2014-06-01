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
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.jivesoftware.os.upena.client.view.upena.shared.Host;
import com.jivesoftware.os.upena.client.view.upena.shared.Key;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JCluster extends DockPanel {

    List<JNannyReport> visibleInstanceDescriptors = new ArrayList<JNannyReport>();
    RequestHelperProvider requestHelperProvider;
    JObjectFactory factory;
    VerticalPanel viewResults;
    JEditRef hostId;
    Host host;

    public JCluster(RequestHelperProvider requestHelperProvider, JObjectFactory factory) {
        this.requestHelperProvider = requestHelperProvider;
        this.factory = factory;
        initComponents();
    }

    private void initComponents() {

        viewResults = new VerticalPanel();

        hostId = new JEditRef(factory, "host", "Host", "");
        Widget editor = hostId.getEditor(40, new IPicked() {
            @Override
            public void picked(Key key, Object v) {
                host = (Host) v;
                refresh();
            }
        });
        hostId.setValue("");


        Button refresh = UV.iconButton("refresh");
        refresh.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                refresh();
            }
        });

        HorizontalPanel menu = new HorizontalPanel();

        menu.add(editor);
        menu.add(refresh);

        add(menu, DockPanel.NORTH);
        add(viewResults, DockPanel.CENTER);
    }

    public void refresh() {

        viewResults.clear();
        visibleInstanceDescriptors.clear();
        //try {
//            String reportString = ""; ///Curl.create().curl("http://" + host.hostName + ":" + host.port + "/uba/report");
//            if (reportString != null) {
//                UbaReport ubaReport = null; //new ObjectMapper().readValue(reportString, UbaReport.class);
//                for (NannyReport report : ubaReport.nannyReports) {
//
//                    JNannyReport jid = new JNannyReport(report);
//                    viewResults.add(jid);
//                    visibleInstanceDescriptors.add(jid);
//                }
//            } else {
//                viewResults.add(new Label("No results"));
//                //viewResults.revalidate();
//            }
        //} catch (IOException ex) {
        //    ex.printStackTrace();
        //}

    }

    class JNannyReport extends VerticalPanel {

        //private final NannyReport nannyReport;
        private final TextArea tail;

        public JNannyReport() { //final NannyReport nannyReport) {
//            this.nannyReport = nannyReport;
//            final InstanceDescriptor id = nannyReport.instanceDescriptor;
//
//            //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//
//            VerticalPanel name = new VerticalPanel();
//            //name.setLayout(new BoxLayout(name, BoxLayout.X_AXIS));
//
//            name.add(new Label(id.clusterName));
//            //name.add(Box.createHorizontalStrut(10));
//            name.add(new Label(id.serviceName));
//            //name.add(Box.createHorizontalStrut(10));
//            name.add(new Label("" + id.instanceName));
//            //name.add(Box.createHorizontalStrut(10));
//            name.add(new Label(id.releaseGroupName));
//            //name.add(Box.createHorizontalStrut(10));
//            for (Entry<String, InstanceDescriptorPort> p : id.ports.entrySet()) {
//                name.add(new Label(p.getKey() + "=" + p.getValue().port));
//                //name.add(Box.createHorizontalStrut(10));
//            }

            VerticalPanel buttons = new VerticalPanel();
            //buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
            Button button = new Button("Manage");
//            button.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    Util.invokeLater(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                openWebpage(new URI("http://" + host.hostName + ":" + id.ports.get("manage").port + "/manage/help"));
//                            } catch (URISyntaxException ex) {
//                                ex.printStackTrace();
//                            }
//                        }
//                    });
//                }
//            });

            buttons.add(button);

            button = new Button("Properties");
//            button.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    Util.invokeLater(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                status("/manage/configuration/properties");
//                            } catch (IOException ex) {
//                                ex.printStackTrace();
//                            }
//                        }
//                    });
//                }
//            });

            buttons.add(button);

            button = new Button("Status");
//            button.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    Util.invokeLater(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                status("/manage/status");
//                            } catch (IOException ex) {
//                                ex.printStackTrace();
//                            }
//                        }
//                    });
//                }
//            });

            buttons.add(button);

            button = new Button("Ping");
//            button.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    Util.invokeLater(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                status("/manage/ping");
//                            } catch (IOException ex) {
//                                ex.printStackTrace();
//                            }
//                        }
//                    });
//                }
//            });

            buttons.add(button);

            button = new Button("Tail");
//            button.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    Util.invokeLater(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                status("/manage/tail");
//                            } catch (IOException ex) {
//                                ex.printStackTrace();
//                            }
//                        }
//                    });
//                }
//            });

            buttons.add(button);

            button = new Button("Routes");
//            button.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    Util.invokeLater(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                status("/manage/tenant/routing/report");
//                            } catch (IOException ex) {
//                                ex.printStackTrace();
//                            }
//                        }
//                    });
//                }
//            });

            buttons.add(button);

            button = new Button("Purge Routes");
//            button.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    Util.invokeLater(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                status("/manage/tenant/routing/invaliateAll");
//                            } catch (IOException ex) {
//                                ex.printStackTrace();
//                            }
//                        }
//                    });
//                }
//            });

            buttons.add(button);

            button = new Button("Shutdown");
//            button.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    Util.invokeLater(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                status("/manage/shutdown?userName=" + requestHelperProvider.editUserName.getText());
//                            } catch (IOException ex) {
//                                ex.printStackTrace();
//                            }
//                        }
//                    });
//                }
//            });

            buttons.add(button);

            tail = new TextArea();
            ScrollPanel scrollTail = new ScrollPanel();
            scrollTail.add(tail);
//                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
//                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//            scrollTail.setPreferredSize(new Dimension(-1, 400));

//            for (String m : nannyReport.messages) {
//                //tail.append(m + "\n");
//            }
//
//            add(name);
            add(buttons);
            add(scrollTail);
        }

        private void status(String manageEndpoint) throws IOException {
            //String url = "http://" + host.hostName + ":" + nannyReport.instanceDescriptor.ports.get("manage").port + manageEndpoint;
            //try {
            //    String curl = Curl.create().curl(url);
            //    tail.setText(curl);
            //} catch (Exception x) {
            //    tail.setText("failed to call " + url + " " + new Date());
            //    x.printStackTrace();
            //}
            //tail.revalidate();
//            revalidate();
//            Container parent = getParent();
//            if (parent != null) {
//                revalidate();
//            }
        }
    }

//    public static void openWebpage(URI uri) {
//        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
//        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
//            try {
//                desktop.browse(uri);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    //   }
}

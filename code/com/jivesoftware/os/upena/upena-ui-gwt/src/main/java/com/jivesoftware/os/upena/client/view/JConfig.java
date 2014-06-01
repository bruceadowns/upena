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

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jivesoftware.os.upena.client.view.upena.routing.shared.UpenaConfig;
import com.jivesoftware.os.upena.client.view.upena.shared.Cluster;
import com.jivesoftware.os.upena.client.view.upena.shared.Host;
import com.jivesoftware.os.upena.client.view.upena.shared.Instance;
import com.jivesoftware.os.upena.client.view.upena.shared.InstanceFilter;
import com.jivesoftware.os.upena.client.view.upena.shared.Key;
import com.jivesoftware.os.upena.client.view.upena.shared.ReleaseGroup;
import com.jivesoftware.os.upena.client.view.upena.shared.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class JConfig extends DockPanel {

    RequestHelperProvider requestHelperProvider;
    JObjectFactory factory;
    DockPanel viewResults;

    Map<String, String> aNames = new HashMap<String, String>();
    Map<String, String> bNames = new HashMap<String, String>();

    Map<String, String> aConfigKeys = new TreeMap<String, String>();
    Map<String, DefaultAndOverride> aConfigs = new TreeMap<String, DefaultAndOverride>();

    Map<String, String> bConfigKeys = new TreeMap<String, String>();
    Map<String, DefaultAndOverride> bConfigs = new TreeMap<String, DefaultAndOverride>();

    JConfigFindInstances aFindInstances;
    JConfigFindInstances bFindInstances;

    TextBox filterKeys;
    TextBox filterValues;
    ToggleButton hideDefaults;

    public JConfig(RequestHelperProvider requestHelperProvider, JObjectFactory factory) {
        this.requestHelperProvider = requestHelperProvider;
        this.factory = factory;
        Runnable changed = new Runnable() {

            @Override
            public void run() {
                refresh();
            }
        };
        aFindInstances = new JConfigFindInstances(factory, changed); //, new Color(255, 245, 240), changed);
        bFindInstances = new JConfigFindInstances(factory, changed); //, new Color(245, 240, 255), changed);
        initComponents();
    }

    private void initComponents() {

        viewResults = new DockPanel();

        HorizontalPanel filter = new HorizontalPanel(); //new SpringLayout());
        hideDefaults = new ToggleButton("Overriden"); //, false);
//        hideDefaults.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Util.invokeLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        filter();
//                    }
//                });
//            }
//        });
        filter.add(hideDefaults);
        filter.add(new Label("filter keys:"));
        filterKeys = new TextBox();
//        filterKeys.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Util.invokeLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        filter();
//                    }
//                });
//            }
//        });
        filter.add(filterKeys);
        //filter.add(Box.createHorizontalStrut(10));
        filter.add(new Label("filter values:"));
        filterValues = new TextBox();
//        filterValues.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Util.invokeLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        filter();
//                    }
//                });
//            }
//        });
        filter.add(filterValues);

        //SpringUtils.makeCompactGrid(filter, 1, 8, 16, 16, 16, 16);

        DockPanel panel = new DockPanel();
        panel.add(filter, DockPanel.NORTH);
        ScrollPanel scrollRoutes = new ScrollPanel();
        scrollRoutes.add(viewResults);
//                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panel.add(scrollRoutes, DockPanel.CENTER);
        //panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        DockPanel avsb = new DockPanel();
        avsb.add(aFindInstances, DockPanel.NORTH);
        avsb.add(new Label("- vs -"), DockPanel.CENTER);
        avsb.add(bFindInstances, DockPanel.SOUTH);

        add(avsb, DockPanel.NORTH);
        add(panel, DockPanel.CENTER);

        FlowPanel commit = new FlowPanel(); //new FlowLayout());
        Button commitButton = new Button("Commit");
//        commitButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Util.invokeLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        CommitValues commitValues = new CommitValues();
//                        commitValues.add();
//                        final JFrame d = new JFrame("Save config changes?");
//                        commitValues.d = d;
//                        d.setPreferredSize(new Dimension(800, 600));
//                        d.getContentPane().add(commitValues);
//                        d.pack();
//                        d.setLocationRelativeTo(null);
//                        d.setVisible(true);
//                    }
//                });
//            }
//        });
        commit.add(commitButton);
        add(commit, DockPanel.SOUTH);
    }

    class CommitValues extends DockPanel {

        VerticalPanel d;
        DockPanel commitableValues;

        public CommitValues() {

            commitableValues = new DockPanel();

            ScrollPanel scrollRoutes = new ScrollPanel();
            scrollRoutes.add(commitableValues);
//                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            add(scrollRoutes, DockPanel.CENTER);

            Button save = new Button("Save");
//            save.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    Util.invokeLater(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (d != null) {
//                                d.setVisible(false);
//                            }
//                            save();
//                        }
//                    });
//                }
//            });
            add(save, DockPanel.SOUTH);
//            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        }

        public void add() {
            VerticalPanel changes = new VerticalPanel(); //new SpringLayout());
            int count = 0;
            for (DefaultAndOverride dao : aConfigs.values()) {
                if (!dao.changes.isEmpty()) {
                    changes.add(new Label(idToString(dao.instanceKey(), aNames) + " " + dao.changes));
                    count++;
                }
            }
            for (DefaultAndOverride dao : bConfigs.values()) {
                if (!dao.changes.isEmpty()) {
                    changes.add(new Label(idToString(dao.instanceKey(), bNames) + " " + dao.changes));
                    count++;
                }
            }
            //SpringUtils.makeCompactGrid(changes, count, 1, 24, 24, 16, 16);
            commitableValues.add(changes, DockPanel.CENTER);
        }

        public void save() {
            for (DefaultAndOverride dao : aConfigs.values()) {
                if (!dao.changes.isEmpty()) {
                    dao.override.properties.clear();
                    boolean update = false;
                    for (String key : dao.changes.keySet()) {
                        String change = dao.changes.get(key);
                        if (change == null || change.length() == 0) { // clears out override
                        } else if (change.equals(dao.config.properties.get(key))) { // clears out override
                        } else { // apply override
                            dao.override.properties.put(key, change);
                            update = true;
                        }
                    }
                    if (update) {
                        //UpenaConfig gotUpdated = requestHelperProvider.get().executeRequest(dao.override, "/upenaConfig/set", UpenaConfig.class, null);
                        //System.out.println("Updated:" + gotUpdated);
                    }
                    boolean remove = false;
                    for (String key : dao.changes.keySet()) {
                        String change = dao.changes.get(key);
                        if (change == null || change.length() == 0) { // clears out override
                            dao.override.properties.put(key, "");
                            remove = true;
                        } else if (change.equals(dao.config.properties.get(key))) { // clears out override
                            dao.override.properties.put(key, "");
                            remove = true;
                        }
                    }
                    if (remove) {
                        //UpenaConfig gotRemoved = requestHelperProvider.get().executeRequest(dao.override, "/upenaConfig/remove", UpenaConfig.class, null);
                        //System.out.println("Removed:" + gotRemoved);
                    }
                }
            }
            refresh();
        }

    }

    public void refresh() {
        aNames.clear();
        bNames.clear();
        refresh(aFindInstances, aConfigKeys, aConfigs, aNames);
        refresh(bFindInstances, bConfigKeys, bConfigs, bNames);
        filter();

    }

    void refresh(JConfigFindInstances find,
            Map<String, String> configKeys,
            Map<String, DefaultAndOverride> configs,
            Map<String, String> names) {

        try {
            configs.clear();
            configKeys.clear();

            InstanceFilter filter = InstanceFilter.create((find.clusterId.getValue().length() > 0) ? Key.key(find.clusterId.getValue(), "clusterKey") : null,
                    (find.hostId.getValue().length() > 0) ? Key.key(find.hostId.getValue(), "hostKey") : null,
                    (find.serviceId.getValue().length() > 0) ? Key.key(find.serviceId.getValue(), "serviceKey") : null,
                    (find.releaseGroupId.getValue().length() > 0) ? Key.key(find.releaseGroupId.getValue(), "userKey") : null,
                    (find.instanceId.getText().length() > 0) ? Integer.parseInt(find.instanceId.getText()) : null,
                    0, 1000);

            if (filter.getClusterKey() == null
                    && filter.getHostKey() == null
                    && filter.getServiceKey() == null
                    && filter.getReleaseGroupKey() == null
                    && filter.getLogicalInstanceId() == null) {
                return;
            }

//            TreeMap<Key, TimestampedValue<Instance>> results = requestHelperProvider.get().executeRequest(filter,
//                    "/upena/instance/find", InstanceFilter.Results.class, null);
//            if (results != null) {
//
//                for (Map.Entry<Key, TimestampedValue<Instance>> e : results.entrySet()) {
//                    if (!e.getValue().getTombstoned()) {
//                        getProperties(e.getKey(), e.getValue().getValue(), configs, names, filter, configKeys);
//                    }
//                }
//            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    boolean getProperties(Key instanceKey,
            Instance instance,
            Map<String, DefaultAndOverride> configs,
            Map<String, String> names,
            InstanceFilter filter,
            Map<String, String> configKeys) {
        UpenaConfig get = new UpenaConfig("default",
                instanceKey.getKey(),
                new HashMap<String, String>());

        String key = key(get);
        if (configs.containsKey(key)) {
            return true;
        }
        String k = instance.clusterKey.getKey();
        if (!names.containsKey(k)) {
            Cluster cluster = get(Cluster.class, Key.key(k, "clusterKey"), "cluster");
            if (cluster != null) {
                names.put(k, cluster.getName());
            }
        }
        k = instance.hostKey.getKey();
        if (!names.containsKey(k)) {
            Host host = get(Host.class, Key.key(k, "hostKey"), "host");
            if (host != null) {
                names.put(k, host.getName());
            }
        }
        k = instance.serviceKey.getKey();
        if (!names.containsKey(k)) {
            Service service = get(Service.class, Key.key(k, "serviceKey"), "service");
            if (service != null) {
                names.put(k, service.getName());
            }
        }
        k = instance.releaseGroupKey.getKey();
        if (!names.containsKey(k)) {
            ReleaseGroup releaseGroup = get(ReleaseGroup.class, Key.key(k, "userKey"), "releaseGroup");
            if (releaseGroup != null) {
                names.put(k, releaseGroup.getName());
            }
        }
        String name = "";
        if (filter.getClusterKey() == null) {
            name += names.get(instance.clusterKey.getKey()) + " - ";
        }
        if (filter.getHostKey() == null) {
            name += names.get(instance.hostKey.getKey()) + " - ";
        }
        if (filter.getServiceKey() == null) {
            name += names.get(instance.serviceKey.getKey()) + " - ";
        }
        if (filter.getReleaseGroupKey() == null) {
            name += names.get(instance.releaseGroupKey.getKey()) + " - ";
        }
        name += instance.instanceId;
        names.put(instanceKey.getKey(), name);

//        UpenaConfig gotDefault = requestHelperProvider.get().executeRequest(get, "/upenaConfig/get", UpenaConfig.class, null);
//        UpenaConfig getOverride = new UpenaConfig("override",
//                instanceKey.getKey(),
//                new HashMap<String, String>());
//        UpenaConfig gotOverride = requestHelperProvider.get().executeRequest(getOverride, "/upenaConfig/get", UpenaConfig.class, null);
//
//        if (gotDefault != null && gotOverride != null) {
//            for (String pk : gotDefault.properties.keySet()) {
//                configKeys.put(pk, pk);
//            }
//            configs.put(key, new DefaultAndOverride(gotDefault, gotOverride));
//        }
        return false;
    }

    void filter() {
        viewResults.clear();
        Map<String, String> allKeys = new TreeMap<String, String>();
        allKeys.putAll(aConfigKeys);
        allKeys.putAll(bConfigKeys);

        VerticalPanel r = new VerticalPanel(); //new SpringLayout());
        int count = 0;
        for (final String propertyKey : allKeys.keySet()) {

            if (filterKeys.getText().length() > 0) {
                if (!propertyKey.contains(filterKeys.getText())) {
                    continue;
                }
            }

            PropertyValues aPropertyValues = new PropertyValues(propertyKey, aNames, false);
            for (final DefaultAndOverride config : aConfigs.values()) {
                if (config.containsKey(propertyKey)) {
                    String value = config.value(propertyKey);
                    String override = config.override(propertyKey);
                    if (filterValues.getText().length() > 0) {
                        if (!value.contains(filterValues.getText()) && !override.contains(filterValues.getText())) {
                            continue;
                        }
                    }
                    if (hideDefaults.isDown()) {
                        if (override == null && !config.changes.containsKey(propertyKey)) {
                            continue;
                        }
                    }
                    aPropertyValues.add(config);
                }
            }

            PropertyValues bPropertyValues = new PropertyValues(propertyKey, bNames, true);
            for (final DefaultAndOverride config : bConfigs.values()) {
                if (config.containsKey(propertyKey)) {
                    String value = config.value(propertyKey);
                    String override = config.override(propertyKey);
                    if (filterValues.getText().length() > 0) {
                        if (!value.contains(filterValues.getText()) && !override.contains(filterValues.getText())) {
                            continue;
                        }
                    }
                    if (hideDefaults.isDown()) {
                        if (override == null && !config.changes.containsKey(propertyKey)) {
                            continue;
                        }
                    }
                    bPropertyValues.add(config);
                }
            }

            if (!aPropertyValues.linkablePropertys.isEmpty() || !bPropertyValues.linkablePropertys.isEmpty()) {

//                aPropertyValues.setBackground(aFindInstances.color);
//                bPropertyValues.setBackground(bFindInstances.color);

                VerticalPanel v = new VerticalPanel();
                //v.setLayout(new BoxLayout(v, BoxLayout.X_AXIS));
                v.add(aPropertyValues);
                v.add(bPropertyValues);

                DockPanel p = new DockPanel();
                Label keyLabel = new Label(propertyKey);
                //keyLabel.setFont(new Font("system", Font.BOLD, 16));
                p.add(keyLabel, DockPanel.LINE_START);
                p.add(v, DockPanel.CENTER);
                //p.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
                r.add(p);
                count++;
            }
        }

        //SpringUtils.makeCompactGrid(r, count, 1, 8, 8, 8, 8);

        viewResults.add(r, DockPanel.CENTER);

    }

    class PropertyValues extends VerticalPanel {

        private final List<LinkableProperty> linkablePropertys = new ArrayList<LinkableProperty>();
        private final String propertyKey;
        private final Map<String, String> idNames;
        private final boolean leftToRight;

        public PropertyValues(String propertyKey, Map<String, String> idNames, boolean leftToRight) {
            this.propertyKey = propertyKey;
            this.idNames = idNames;
            this.leftToRight = leftToRight;
            //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        }

        class LinkableProperty extends VerticalPanel {

            final ToggleButton linked;
            final DefaultAndOverride config;
            final TextBox editValue;

            LinkableProperty(final DefaultAndOverride config) {
                this.config = config;
                //setOpaque(false);
                //setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

                linked = new ToggleButton(""); //, true);
                //linked.setIcon(Util.icon("linked"));
                //linked.setPressedIcon(Util.icon("unlinked"));
                //linked.setSelectedIcon(Util.icon("linked"));
//                linked.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        Util.invokeLater(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (linked.isSelected()) {
//                                    linked.setIcon(Util.icon("linked"));
//                                    linked.setPressedIcon(Util.icon("unlinked"));
//                                    linked.setSelectedIcon(Util.icon("linked"));
//                                } else {
//                                    linked.setIcon(Util.icon("unlinked"));
//                                    linked.setPressedIcon(Util.icon("linked"));
//                                    linked.setSelectedIcon(Util.icon("unlinked"));
//                                }
//                            }
//                        });
//                    }
//                });

                Label instanceLabel = new Label(idToString(config.instanceKey(), idNames));

                String override = config.override(propertyKey);
                if (config.changes.containsKey(propertyKey)) {
                    override = config.changes.get(propertyKey);
                }

                editValue = new TextBox() {

//                    @Override
//                    protected void paintBorder(Graphics g) {
//                        super.paintBorder(g);
//                        if (config.changes.containsKey(propertyKey) || config.override.properties.containsKey(propertyKey)) {
//                            g.setColor(Color.orange);
//                            g.drawRect(0, 0, getWidth(), getHeight());
//                        }
//                    }

                };
                setTitle(override);
//                editValue.setMinimumSize(new Dimension(50, 24));
//                editValue.setPreferredSize(new Dimension(100, 24));
//                editValue.setMaximumSize(new Dimension(600, 24));
//                editValue.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        if (linked.isSelected()) {
//                            for (LinkableProperty linkableProperty : linkablePropertys) {
//                                if (linkableProperty.linked.isSelected()) {
//                                    linkableProperty.editValue.setText(editValue.getText());
//                                    linkableProperty.config.set(propertyKey, editValue.getText());
//                                }
//                            }
//                        } else {
//                            config.set(propertyKey, editValue.getText());
//                        }
//                    }
//                });

                String value = config.value(propertyKey);
                Label defaultValue = new Label(value);
                //defaultValue.setForeground(Color.gray);

                if (leftToRight) {
                    add(linked);
                    add(defaultValue);
                    add(editValue);
                    add(instanceLabel);

                } else {
                    add(instanceLabel);
                    add(editValue);
                    add(defaultValue);
                    add(linked);
                }
            }
        }

        public void add(DefaultAndOverride config) {
            LinkableProperty linkableProperty = new LinkableProperty(config);
            add(linkableProperty);
            linkablePropertys.add(linkableProperty);
        }
    }

    String key(UpenaConfig config) {
        return config.context + " " + config.instanceKey;
    }

    public String idToString(String id, Map<String, String> names) {
        String name = names.get(id);
        if (name == null) {
            System.out.println("hmm " + id);
            return id;
        }
        return name;
    }

    public <K, V> V get(final Class<V> valueClass, final K key, String context) {
//        V v = requestHelperProvider.get().executeRequest(key, "/upena/" + context + "/get", valueClass, null);
//        System.out.println(v);
//        return v;
        return null;
    }

    static class DefaultAndOverride {

        final UpenaConfig config;
        final UpenaConfig override;
        final Map<String, String> changes = new TreeMap<String, String>();

        public DefaultAndOverride(UpenaConfig config, UpenaConfig override) {
            this.config = config;
            this.override = override;
        }

        String instanceKey() {
            return config.instanceKey;
        }

        boolean containsKey(String key) {
            if (config.properties.containsKey(key)) {
                return true;
            }
            return override.properties.containsKey(key);
        }

        String value(String key) {
            return config.properties.get(key);
        }

        String override(String key) {
            return override.properties.get(key);
        }

        void set(String key, String value) {
            changes.put(key, value);
        }
    }
}

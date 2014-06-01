package com.jivesoftware.os.upena.client.view;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


public class RequestHelperProvider extends VerticalPanel {

    final TextBox editHost;
    final TextBox editPort;
    final TextBox editUserName;
    final PasswordTextBox editPassword;

    public RequestHelperProvider() {

        add(new Label("host:"));
        editHost = new TextBox();
        editHost.setText("localhost");
        add(editHost);

        add(new Label("port:"));
        editPort = new TextBox();
        editPort.setText("1175");
        add(editPort);

        add(new Label("user:"));
        editUserName = new TextBox();
        editHost.setText("annoymous");
        add(editUserName);

        add(new Label("password:"));
        editPassword = new PasswordTextBox();
        add(editPassword);
    }

    RequestHelper get() {
        return new RequestHelper(editHost.getText(), Integer.parseInt(editPort.getText()));
    }
}

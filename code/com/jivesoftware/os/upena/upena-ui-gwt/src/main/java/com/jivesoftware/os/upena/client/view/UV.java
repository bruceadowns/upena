package com.jivesoftware.os.upena.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;

public class UV {
    public static Button iconButton(String iconName) {
        Image img = new Image(GWT.getHostPageBaseURL()+"icons/"+iconName+".png");
        img.setSize("24px", "24px");
        Button button = new Button();
        button.getElement().appendChild(img.getElement());
        return button;
    }

    public static String simpleName(Class clazz) {
        if (clazz == null) {
            return "null";
        }
        String name = clazz.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf < 0) {
            return name;
        }
        return name.substring(lastIndexOf+1);
    }
}

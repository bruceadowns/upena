package com.jivesoftware.os.upena.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jivesoftware.os.upena.client.view.JObjectFactory;
import com.jivesoftware.os.upena.client.view.JUpenaServices;
import com.jivesoftware.os.upena.client.view.RequestHelperProvider;

/**
 * Main entry point.
 */
public class MainEntryPoint implements EntryPoint {

    /**
     * The entry point method, called automatically by loading a module that declares an implementing class as an entry-point
     */
    public void onModuleLoad() {

        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            public void onUncaughtException(Throwable e) {
                Throwable unwrapped = unwrap(e);
                VerticalPanel trace = new VerticalPanel();
                trace.add(new Label(e.getMessage()));
                for (StackTraceElement t : e.getStackTrace()) {
                    trace.add(new Label(t.toString()));
                }
                PopupPanel pp = new PopupPanel(true);
                pp.add(trace);
                pp.center();
                pp.show();

            }

            public Throwable unwrap(Throwable e) {
                if (e instanceof UmbrellaException) {
                    UmbrellaException ue = (UmbrellaException) e;
                    if (ue.getCauses().size() == 1) {
                        return unwrap(ue.getCauses().iterator().next());
                    }
                }
                return e;
            }
        });

        final GWTServiceAsync s = GWT.create(GWTService.class);

        RequestHelperProvider helperProvider = new RequestHelperProvider();

            JUpenaServices upena = new JUpenaServices(helperProvider, new JObjectFactory(helperProvider));

        RootPanel.get().add(upena);

//        s.getMessages(new AsyncCallback<String[]>() {
//            public void onFailure(Throwable caught) {
//                Window.alert("GWT service call error");
//            }
//
//            public void onSuccess(String[] result) {
//                messages.clear();
//                for (String s: result)
//                    messages.add(new Label(s));
//            }
//        });
    }
}

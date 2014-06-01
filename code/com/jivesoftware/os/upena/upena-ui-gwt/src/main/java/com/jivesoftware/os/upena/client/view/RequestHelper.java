package com.jivesoftware.os.upena.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;

/**
 *
 * @author jonathan
 */
public class RequestHelper {

    private final String host;
    private final int port;

    public RequestHelper(String host, int port) {
        this.host = host;
        this.port = port;
    }


    void getJson(String url,final JsonCallback callback) {
        String hack = GWT.getHostPageBaseURL();
        int lastIndexOf = hack.lastIndexOf("/", hack.length()-2);
        hack = hack.substring(0,lastIndexOf+1);
        RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, URL.encode(hack + url));
        rb.setHeader("Content-Type", "application/json");
        try {
            Request request = rb.sendRequest(null, new RequestCallback() {
                @Override
                public void onError(Request request, Throwable exception) {
                    callback.error();
                }

                @Override
                public void onResponseReceived(Request request, Response response) {
                    if (200 == response.getStatusCode()) {
                        callback.result(response.getText());
                    } else {
                        callback.error();
                    }
                }
            });
        } catch (RequestException e) {
            callback.error();
            Window.alert("Couldn't retrieve JSON" + e);
        }
    }

    void postJson(String post, String url, final JsonCallback callback) {
        String hack = GWT.getHostPageBaseURL();
        int lastIndexOf = hack.lastIndexOf("/", hack.length()-2);
        hack = hack.substring(0,lastIndexOf+1);
        RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, URL.encode(hack + url));
        rb.setHeader("Content-Type", "application/json");
        try {
            Request request = rb.sendRequest(post, new RequestCallback() {
                @Override
                public void onError(Request request, Throwable exception) {
                    callback.error();
                     Window.alert("error"+request+exception);
                }

                @Override
                public void onResponseReceived(Request request, Response response) {
                    if (200 == response.getStatusCode()) {
                        callback.result(response.getText());
                    } else {
                        callback.error();
                    }
                }
            });
        } catch (RequestException e) {
            callback.error();
            Window.alert("Couldn't retrieve JSON" + e);
        }
    }

}

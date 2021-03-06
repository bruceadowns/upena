package com.jivesoftware.os.upena.deployable.okta.client.framework;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public abstract class ApiObject {

    @JsonAnyGetter
    public Map<String, Object> getUnmapped() {
        return unmapped;
    }

    private Map<String, Object> unmapped = new HashMap<String, Object>();

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
        // TODO: changeLog a warning
        unmapped.put(key, value);
    }
}

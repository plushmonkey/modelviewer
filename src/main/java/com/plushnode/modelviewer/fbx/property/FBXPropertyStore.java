package com.plushnode.modelviewer.fbx.property;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FBXPropertyStore {
    private Map<String, FBXProperty> properties = new HashMap<>();

    public void addProperty(FBXProperty property) {
        this.properties.put(property.getName(), property);
    }

    public FBXProperty getProperty(String name) {
        return properties.get(name);
    }

    public Collection<FBXProperty> getProperties() {
        return properties.values();
    }
}

package com.plushnode.modelviewer.fbx;

import java.util.Map;

public class FBXModel {
    private String name;
    private Map<String, AnyType> properties;

    public FBXModel(String name) {
        this.name = name;
    }

    public void addProperty(String name, Object value) {
        properties.put(name, new AnyType(value));
    }

    public String getName() {
        return name;
    }

}

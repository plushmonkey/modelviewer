package com.plushnode.modelviewer.fbx.node;

import java.util.*;

public class FBXNode extends FBXNodeContainer {
    private String name;
    private List<FBXNodeProperty> properties;

    public FBXNode(String name) {
        this.name = name;
        this.properties = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public FBXNodeProperty getProperty(int index) {
        return properties.get(index);
    }

    public List<FBXNodeProperty> getProperties() {
        return properties;
    }

    public void addProperty(FBXNodeProperty property) {
        properties.add(property);
    }
}

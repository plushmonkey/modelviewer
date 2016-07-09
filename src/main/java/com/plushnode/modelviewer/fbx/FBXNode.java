package com.plushnode.modelviewer.fbx;

import java.util.*;

public class FBXNode {
    private String name;
    private Map<String, FBXNode> children;
    private List<Object> properties;

    public FBXNode(String name) {
        this.name = name;
        this.children = new HashMap<>();
        this.properties = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public FBXNode getNode(String name) {
        return children.get(name);
    }

    public Collection<FBXNode> getNodes() {
        return children.values();
    }

    public void addNode(FBXNode node) {
        children.put(node.getName(), node);
    }

    public Object getProperty(int index) {
        return properties.get(index);
    }

    public List<Object> getProperties() {
        return properties;
    }

    public void addProperty(Object property) {
        properties.add(property);
    }
}

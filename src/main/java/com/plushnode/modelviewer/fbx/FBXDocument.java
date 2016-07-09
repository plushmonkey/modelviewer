package com.plushnode.modelviewer.fbx;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FBXDocument {
    private int version;
    private Map<String, FBXNode> nodes;

    public FBXDocument(int version) {
        this.version = version;
        this.nodes = new HashMap<>();
    }

    public int getVersion() {
        return version;
    }

    public FBXNode getNode(String name) {
        return nodes.get(name);
    }

    public Collection<FBXNode> getNodes() {
        return nodes.values();
    }

    public void addNode(FBXNode node) {
        nodes.put(node.getName(), node);
    }
}

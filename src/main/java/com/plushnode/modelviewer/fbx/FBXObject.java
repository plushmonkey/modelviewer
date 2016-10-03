package com.plushnode.modelviewer.fbx;

import com.plushnode.modelviewer.fbx.node.FBXNode;

public class FBXObject {
    private long id;
    private String name;
    private FBXNode node;

    public FBXObject(long id, String name, FBXNode node) {
        this.id = id;
        this.name = name;
        this.node = node;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public FBXNode getNode() {
        return this.node;
    }
}

package com.plushnode.modelviewer.fbx;

import com.plushnode.modelviewer.fbx.node.FBXNode;
import com.plushnode.modelviewer.fbx.node.FBXNodeContainer;

import java.util.HashMap;
import java.util.Map;

public class FBXDocument extends FBXNodeContainer {
    private int version;
    private Map<String, FBXNode> nodes;

    public FBXDocument(int version) {
        this.version = version;
        this.nodes = new HashMap<>();
    }

    public int getVersion() {
        return version;
    }


}

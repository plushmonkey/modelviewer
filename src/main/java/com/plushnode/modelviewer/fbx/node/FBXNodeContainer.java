package com.plushnode.modelviewer.fbx.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class FBXNodeContainer {
    private Map<String, List<FBXNode>> nodes = new HashMap<>();

    public FBXNode getNode(String name) {
        List<FBXNode> nodeList = nodes.get(name);
        if (nodeList == null) return null;
        return nodeList.get(0);
    }

    public List<FBXNode> getNodes() {
        List<FBXNode> result = new ArrayList<>();

        for (List<FBXNode> nodeList : nodes.values()) {
            result.addAll(nodeList);
        }
        return result;
    }

    public List<FBXNode> getNodes(String name) {
        return nodes.get(name);
    }

    public void addNode(FBXNode node) {
        List<FBXNode> list = nodes.get(node.getName());
        if (list == null) {
            list = new ArrayList<>();
            nodes.put(node.getName(), list);
        }

        list.add(node);
    }
}

package com.plushnode.modelviewer.scene;

import com.plushnode.modelviewer.geometry.Model;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.ArrayList;
import java.util.List;

public class SceneNode {
    private Model model;
    private Transform transform;
    private List<SceneNode> children = new ArrayList<>();

    public SceneNode(Model model, Transform transform) {
        this.model = model;
        this.transform = transform;
    }

    public Model getModel() {
        return this.model;
    }

    public Transform getTransform() {
        return this.transform;
    }

    public void addChild(SceneNode child) {
        this.children.add(child);
    }

    public List<SceneNode> getChildren() {
        return children;
    }

    public Vector3D getSize() {
        // todo: calculate boundary
        return Vector3D.ZERO;
    }
}

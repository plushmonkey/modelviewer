package com.plushnode.modelviewer.scene;

import com.plushnode.modelviewer.geometry.Model;

public class Actor {
    private Model model;
    // Transform within the scene
    private Transform transform = new Transform();

    public Actor(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return this.model;
    }

    public Transform getTransform() {
        return this.transform;
    }

}

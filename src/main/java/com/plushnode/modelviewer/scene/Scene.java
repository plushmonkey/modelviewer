package com.plushnode.modelviewer.scene;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private List<Actor> actors = new ArrayList<>();
    private Transform transform = new Transform();

    public Scene() {

    }

    public Scene(Transform transform) {
        this.transform = transform;
    }

    public Transform getTransform() {
        return this.transform;
    }

    public void addActor(Actor actor) {
        actors.add(actor);
    }

    public List<Actor> getActors() {
        return actors;
    }

    public Vector3D getSize() {
        for (Actor actor : actors) {
            Vector3D translation = actor.getTransform().getTranslation();
            // todo: calculate boundary around all the models
        }
        return Vector3D.ZERO;
    }
}

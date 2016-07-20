package com.plushnode.modelviewer.scene;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Transform {
    // Uniform scale
    private double scale;
    private Rotation rotation;
    private Vector3D translation;

    public Transform() {
        this.scale = 1.0;
        this.rotation = Rotation.IDENTITY;
        this.translation = Vector3D.ZERO;
    }

    public double getScale() {
        return scale;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public Vector3D getTranslation() {
        return translation;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void rotate(Rotation rotation) {
        if (this.rotation == null) {
            this.rotation = rotation;
        } else {
            this.rotation = rotation.applyTo(this.rotation);
        }
    }

    // Sets the absolute rotation
    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }

    public void translate(Vector3D amount) {
        this.translation = this.translation.add(amount);
    }

    public void setTranslation(Vector3D translation) {
        this.translation = translation;
    }
}

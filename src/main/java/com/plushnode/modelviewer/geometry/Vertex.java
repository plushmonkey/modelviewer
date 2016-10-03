package com.plushnode.modelviewer.geometry;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class Vertex {
    private Vector3D position;
    private Vector2D uv = Vector2D.ZERO;

    public Vertex(Vector3D position) {
        this.position = position;
    }

    public Vertex(Vector3D position, Vector2D uv) {
        this.position = position;
        this.uv = uv;
    }

    public Vector2D getUV() {
        return uv;
    }

    public void setUV(Vector2D uv) {
        this.uv = uv;
    }

    public Vector3D getPosition() {
        return position;
    }
}

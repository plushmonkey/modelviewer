package com.plushnode.modelviewer.fill;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.Set;


public interface TriangleFiller {
    /**
     * Turns three vertices of a triangle into a list of locations to fill.
     */
    Set<Vector3D> fill(Vector3D vertexA, Vector3D vertexB, Vector3D vertexC);
}

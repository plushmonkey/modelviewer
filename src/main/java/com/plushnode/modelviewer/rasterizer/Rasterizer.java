package com.plushnode.modelviewer.rasterizer;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.Set;


public interface Rasterizer {
    /**
     * Turns three vertices of a triangle into a list of locations to fill.
     */
    Set<Vector3D> rasterize(Vector3D vertexA, Vector3D vertexB, Vector3D vertexC);
}

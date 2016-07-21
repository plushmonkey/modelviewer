package com.plushnode.modelviewer.fill;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.List;
import java.util.Set;


public interface PolygonFiller {
    /**
     * Turns three vertices of a triangle into a list of locations to fill.
     */
    Set<Vector3D> fill(List<Vector3D> vertices);
}

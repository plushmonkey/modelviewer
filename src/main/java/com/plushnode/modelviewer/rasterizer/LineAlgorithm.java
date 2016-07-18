package com.plushnode.modelviewer.rasterizer;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.Set;

public interface LineAlgorithm {
    Set<Vector3D> draw(Vector3D from, Vector3D to);
}

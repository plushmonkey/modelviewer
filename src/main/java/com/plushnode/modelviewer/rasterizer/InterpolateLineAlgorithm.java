package com.plushnode.modelviewer.rasterizer;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.HashSet;
import java.util.Set;

public class InterpolateLineAlgorithm implements LineAlgorithm {
    @Override
    public Set<Vector3D> draw(Vector3D from, Vector3D to) {
        Set<Vector3D> result = new HashSet<>();
        double dist = from.distance(to);
        Vector3D between = to.subtract(from);

        for (double i = 0; i <= dist; ++i) {
            Vector3D interpolated = from.add(between.scalarMultiply(i / dist));
            //Vector3D rounded = new Vector3D(Math.round(interpolated.getX()), Math.round(interpolated.getY()), Math.round(interpolated.getZ()));

            result.add(interpolated);
        }
        return result;
    }
}

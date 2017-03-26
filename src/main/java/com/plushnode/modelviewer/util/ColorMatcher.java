package com.plushnode.modelviewer.util;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.HashMap;
import java.util.Map;

public abstract class ColorMatcher {
    protected Map<Vector3D, ColorType> types = new HashMap<>();
    protected ColorType defaultType = new ColorType(1, (byte)0);

    public ColorType getDefaultType() {
        return this.defaultType;
    }

    // Returns the best match of a block type from color
    // note: this is the simplest check, but not the most accurate
    public ColorType getTypeFromColor(Vector3D color) {
        double best_dist = Double.MAX_VALUE;
        ColorType best_match = null;

        for (Map.Entry<Vector3D, ColorType> entry : types.entrySet()) {
            double dist = entry.getKey().distance(color);

            if (dist < best_dist) {
                best_dist = dist;
                best_match = entry.getValue();
            }
        }

        if (best_match == null)
            return new ColorType(1, (byte)0);

        return best_match;
    }
}

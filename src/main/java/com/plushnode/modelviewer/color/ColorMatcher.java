package com.plushnode.modelviewer.color;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ColorMatcher {
    protected Map<Vector3D, ColorType> types = new HashMap<>();
    protected ColorType defaultType = new ColorType(1, (byte)0);

    public ColorType getDefaultType() {
        return this.defaultType;
    }

    // Returns the best match of a block type from color
    public ColorType getTypeFromColor(Vector3D color) {
        double best_dist = Double.MAX_VALUE;
        ColorType best_match = null;

        color = ColorUtil.RGBtoCIELab(color);

        for (Map.Entry<Vector3D, ColorType> entry : types.entrySet()) {
            double dist = entry.getKey().distanceSq(color);

            if (dist < best_dist) {
                best_dist = dist;
                best_match = entry.getValue();
            }
        }

        if (best_match == null)
            return new ColorType(1, (byte)0);

        return best_match;
    }

    protected void convertTypesToCIELab() {
        types = types.entrySet().stream()
                .collect(Collectors.toMap(e -> ColorUtil.RGBtoCIELab(e.getKey()), Map.Entry::getValue));
    }
}

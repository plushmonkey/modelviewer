package com.plushnode.modelviewer.color;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ColorMatcher {
    protected Map<Vector3D, Material> types = new HashMap<>();
    protected Material defaultType = Material.DIRT;

    public Material getDefaultType() {
        return this.defaultType;
    }

    // Returns the best match of a block type from color
    public Material getTypeFromColor(Vector3D color) {
        double best_dist = Double.MAX_VALUE;
        Material best_match = null;

        color = ColorUtil.RGBtoCIELab(color);

        for (Map.Entry<Vector3D, Material> entry : types.entrySet()) {
            double dist = entry.getKey().distanceSq(color);

            if (dist < best_dist) {
                best_dist = dist;
                best_match = entry.getValue();
            }
        }

        if (best_match == null)
            return defaultType;

        return best_match;
    }

    protected void convertTypesToCIELab() {
        types = types.entrySet().stream()
                .collect(Collectors.toMap(e -> ColorUtil.RGBtoCIELab(e.getKey()), Map.Entry::getValue));
    }
}

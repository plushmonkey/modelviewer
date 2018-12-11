package com.plushnode.modelviewer.color;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.bukkit.Material;

public class StaticMaterialMatcher extends ColorMatcher {
    Material color;

    public StaticMaterialMatcher(Material material) {
        this.color = material;
    }

    @Override
    public Material getTypeFromColor(Vector3D color) {
        return this.color;
    }
}

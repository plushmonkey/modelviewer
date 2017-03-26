package com.plushnode.modelviewer.util;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class BlockColorMatcher extends ColorMatcher {
    ColorType color;

    public BlockColorMatcher(int id, byte data) {
        this.color = new ColorType(id, data);
    }

    @Override
    public ColorType getTypeFromColor(Vector3D color) {
        return this.color;
    }
}

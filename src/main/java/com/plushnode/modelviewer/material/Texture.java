package com.plushnode.modelviewer.material;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public interface Texture {
    // Sampled directly from texture in texture space. x and y must be between 0 and width/height.
    Vector3D sample(int x, int y);

    int getWidth();
    int getHeight();
}

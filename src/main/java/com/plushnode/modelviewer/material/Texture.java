package com.plushnode.modelviewer.material;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public interface Texture {
    // Samples from the texture. u and v should be between 0 and 1.
    Vector3D sample(double u, double v);
}

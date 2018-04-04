package com.plushnode.modelviewer.material;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public interface TextureFilter {
    Vector3D sample(Texture texture, double u, double v);
}

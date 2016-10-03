package com.plushnode.modelviewer.material;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public interface Material {
    Vector3D getDiffuseColor();
    Texture getTexture();
}

package com.plushnode.modelviewer.material;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public abstract class BaseTextureFilter implements TextureFilter {
    public Vector2D getCoords(Texture texture, double u, double v) {
        u = u % 1.0f;
        v = v % 1.0f;

        if (u < 0)
            u += 1.0;
        if (v < 0)
            v += 1.0;

        u = Math.max(0.0f, Math.min(u, 1.0f));
        v = Math.max(0.0f, Math.min(v, 1.0f));

        v = 1.0 - v;

        return new Vector2D((texture.getWidth() - 1) * u, (texture.getHeight() - 1) * v);
    }
}

package com.plushnode.modelviewer.material;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class NearestNeighborFilter extends BaseTextureFilter {
    @Override
    public Vector3D sample(Texture texture, double u, double v) {
        Vector2D coords = getCoords(texture, u ,v);

        int x = (int)Math.round(coords.getX());
        int y = (int)Math.round(coords.getY());

        return texture.sample(x, y);
    }
}

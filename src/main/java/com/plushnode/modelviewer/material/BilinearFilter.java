package com.plushnode.modelviewer.material;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class BilinearFilter extends BaseTextureFilter {
    @Override
    public Vector3D sample(Texture texture, double u, double v) {
        Vector2D coords = getCoords(texture, u, v);

        double x = coords.getX();
        double y = coords.getY();

        // Values between 0 and 1.0 that are used to interpolate.
        double xt = x % 1.0;
        double yt = y % 1.0;

        Vector3D a = texture.sample((int)x, (int)y);
        Vector3D b = texture.sample((int)x, (int)(y + 1));
        Vector3D c = texture.sample((int)(x + 1), (int)y);
        Vector3D d = texture.sample((int)(x + 1), (int)(y + 1));

        // Interpolate horizontally
        Vector3D lerp1 = lerp(a, c, xt);
        Vector3D lerp2 = lerp(b, d, xt);

        // Interpolate vertically
        return lerp(lerp1, lerp2, yt);
    }

    private Vector3D lerp(Vector3D a, Vector3D b, double t) {
        return a.scalarMultiply(1.0 - t).add(b.scalarMultiply(t));
    }
}

package com.plushnode.modelviewer.color;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public final class ColorUtil {
    private ColorUtil() {

    }

    // Converts an rgb vector (0.0 - 1.0) to CIE Lab color space
    public static Vector3D RGBtoCIELab(Vector3D rgb) {
        Vector3D xyz = RGBtoXYZ(rgb);

        // D65 - Daylight, sRGB, Adobe-RGB
        double refX = 0.95047;
        double refY = 1.0;
        double refZ = 1.08883;

        double x = xyz.getX() / (refX * 100.0);
        double y = xyz.getY() / (refY * 100.0);
        double z = xyz.getZ() / (refZ * 100.0);

        x = (x > 0.008856) ? Math.pow(x, 1.0 / 3.0) : ((7.787 * x) + (16.0 / 116.0));
        y = (y > 0.008856) ? Math.pow(y, 1.0 / 3.0) : ((7.787 * y) + (16.0 / 116.0));
        z = (z > 0.008856) ? Math.pow(z, 1.0 / 3.0) : ((7.787 * z) + (16.0 / 116.0));

        double L = (116.0 * y) - 16.0;
        double a = 500.0 * (x - y);
        double b = 200.0 * (y - z);

        return new Vector3D(L, a, b);
    }

    // Convert from RGB (0.0 - 1.0) to CIE 1931 XYZ color space.
    public static Vector3D RGBtoXYZ(Vector3D rgb) {
        double r = rgb.getX();
        double g = rgb.getY();
        double b = rgb.getZ();

        r = ((r > 0.04045) ? Math.pow((r + 0.055) / 1.055, 2.4) : r / 12.92) * 100.0;
        g = ((g > 0.04045) ? Math.pow((g + 0.055) / 1.055, 2.4) : g / 12.92) * 100.0;
        b = ((b > 0.04045) ? Math.pow((b + 0.055) / 1.055, 2.4) : b / 12.92) * 100.0;

        double x = r * 0.4124 + g * 0.3576 + b * 0.1805;
        double y = r * 0.2126 + g * 0.7152 + b * 0.0722;
        double z = r * 0.0193 + g * 0.1192 + b * 0.9505;

        return new Vector3D(x, y, z);
    }
}

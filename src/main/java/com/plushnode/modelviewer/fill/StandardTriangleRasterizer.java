package com.plushnode.modelviewer.fill;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StandardTriangleRasterizer implements PolygonFiller {
    private static final double JUMP = 0.1;

    private double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(val, max));
    }

    private double interpolate(double min, double max, double t) {
        return min + (max - min) * clamp(t, 0, 1);
    }

    private Set<Vector3D> processScanLine(double y, Vector3D pa, Vector3D pb, Vector3D pc, Vector3D pd) {
        Set<Vector3D> result = new HashSet<>();

        double gradient1 = pa.getY() != pb.getY() ? (y - pa.getY()) / (pb.getY() - pa.getY()) : 1;
        double gradient2 = pc.getY() != pd.getY() ? (y - pc.getY()) / (pd.getY() - pc.getY()) : 1;

        double sx = interpolate(pa.getX(), pb.getX(), gradient1);
        double ex = interpolate(pc.getX(), pd.getX(), gradient2);

        double z1 = interpolate(pa.getZ(), pb.getZ(), gradient1);
        double z2 = interpolate(pc.getZ(), pd.getZ(), gradient2);

        for (double x = sx; x < ex; x += JUMP) {
            double gradient = (x - sx) / (ex - sx);
            double z = interpolate(z1, z2, gradient);

            Vector3D location = new Vector3D((int)x, (int)y, (int)z);

            result.add(location);
        }

        return result;
    }

    private double cross2d(double x0, double y0, double x1, double y1) {
        return x0 * y1 - x1 * y0;
    }

    private double lineSide2D(Vector3D p, Vector3D from, Vector3D to) {
        return cross2d(p.getX() - from.getX(), p.getY() - from.getY(), to.getX() - from.getX(), to.getY() - from.getY());
    }

    @Override
    public Set<Vector3D> fill(List<Vector3D> vertices) {
        Vector3D[] points = new Vector3D[] { vertices.get(0), vertices.get(1), vertices.get(2) };

        Arrays.sort(points, (Vector3D v1, Vector3D v2) -> {
            double r = v1.getY() - v2.getY();
            if (r < 0) return -1;
            if (r == 0) return 0;
            return 1;
        });

        Vector3D p1 = points[0];
        Vector3D p2 = points[1];
        Vector3D p3 = points[2];

        Set<Vector3D> result = new HashSet<>();

        if (lineSide2D(p2, p1, p3) > 0) {
            for (double y = p1.getY(); y <= p3.getY(); y += JUMP) {
                if (y < p2.getY()) {
                    result.addAll(processScanLine(y, p1, p3, p1, p2));
                } else {
                    result.addAll(processScanLine(y, p1, p3, p2, p3));
                }
            }
        } else {
            for (double y = p1.getY(); y <= p3.getY(); y += JUMP) {
                if (y < p2.getY()) {
                    result.addAll(processScanLine(y, p1, p2, p1, p3));
                } else {
                    result.addAll(processScanLine(y, p2, p3, p1, p3));
                }
            }
        }

        return result;
    }
}

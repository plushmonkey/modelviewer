package com.plushnode.modelviewer.fill;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.HashSet;
import java.util.Set;

public class LineTriangleFiller implements TriangleFiller {
    private LineAlgorithm algorithm;

    public LineTriangleFiller() {
        this.algorithm = new InterpolateLineAlgorithm();
    }

    public LineTriangleFiller(LineAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public Set<Vector3D> fill(Vector3D vertexA, Vector3D vertexB, Vector3D vertexC) {
        Set<Vector3D> result = new HashSet<>();
        Vector3D btoc = vertexC.subtract(vertexB);
        double dist = vertexB.distance(vertexC);
        double jump = getJump(vertexA, vertexB, vertexC);

        for (double i = 0; i <= dist; i += jump) {
            Vector3D interpolated = vertexB.add(btoc.scalarMultiply(i / dist));
            Set<Vector3D> linePoints = algorithm.draw(vertexA, interpolated);
            result.addAll(linePoints);
        }
        return result;
    }

    private double getJump(Vector3D vertexA, Vector3D vertexB, Vector3D vertexC) {
        double atob = vertexA.distance(vertexB);
        double atoc = vertexA.distance(vertexC);
        double maxDist = Math.max(atob, atoc);

        return 1.0 / Math.max(maxDist, 20);
    }
}

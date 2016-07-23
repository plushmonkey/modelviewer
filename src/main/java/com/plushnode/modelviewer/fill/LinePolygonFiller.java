package com.plushnode.modelviewer.fill;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LinePolygonFiller implements PolygonFiller {
    private LineAlgorithm algorithm;

    public LinePolygonFiller() {
        this.algorithm = new InterpolateLineAlgorithm();
    }

    public LinePolygonFiller(LineAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public Set<Vector3D> fill(List<Vector3D> vertices) {
        Set<Vector3D> result = new HashSet<>();

        if (vertices.isEmpty()) return result;

        Vector3D pivot = vertices.get(0);

        for (int i = 1; i < vertices.size() - 1; ++i) {
            Vector3D current = vertices.get(i);
            Vector3D next = vertices.get(i + 1);

            Vector3D currentToNext = next.subtract(current);
            double dist = current.distance(next);
            double jump = getJump(pivot, current, next);

            for (double j = 0; j <= dist; j += jump) {
                Vector3D interpolated = current.add(currentToNext.scalarMultiply(j / dist));
                Set<Vector3D> linePoints = algorithm.draw(pivot, interpolated);
                result.addAll(linePoints);
            }
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

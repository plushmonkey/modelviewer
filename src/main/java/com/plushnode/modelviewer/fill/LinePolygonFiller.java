package com.plushnode.modelviewer.fill;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class LinePolygonFiller implements PolygonFiller {
    private LineAlgorithm algorithm;

    public LinePolygonFiller() {
        this.algorithm = new InterpolateLineAlgorithm();
    }

    public LinePolygonFiller(LineAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public void fill(List<Vector3D> vertices, Consumer<Vector3D> callback) {
        if (vertices.isEmpty()) return;

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

                linePoints.forEach(callback);
            }
        }
    }

    private double getJump(Vector3D vertexA, Vector3D vertexB, Vector3D vertexC) {
        double atob = vertexA.distance(vertexB);
        double atoc = vertexA.distance(vertexC);
        double maxDist = Math.max(atob, atoc);

        return 1.0 / Math.max(maxDist, 20);
    }
}

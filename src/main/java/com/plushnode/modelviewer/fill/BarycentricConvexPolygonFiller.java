package com.plushnode.modelviewer.fill;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Triangulates a set of vertices and interpolates using barycentric coordinates
public class BarycentricConvexPolygonFiller implements PolygonFiller {
    @Override
    public Set<Vector3D> fill(List<Vector3D> vertices) {
        Set<Vector3D> result = new HashSet<>();
        if (vertices.size() < 3) return result;

        List<Triangle> triangles;

        if (vertices.size() > 3)
            triangles = triangulate(vertices);
        else {
            triangles = new ArrayList<>();
            triangles.add(new Triangle(vertices.get(0), vertices.get(1), vertices.get(2)));
        }

        for (Triangle triangle : triangles) {
            Vector3D aVertex = triangle.vertices[0];
            Vector3D bVertex = triangle.vertices[1];
            Vector3D cVertex = triangle.vertices[2];

            double vJump = GetInterpolationJump(aVertex, bVertex);
            double wJump = GetInterpolationJump(aVertex, cVertex);

            for (double v = 0.0; v <= 1.0; v += vJump) {
                Vector3D vBA = bVertex.subtract(aVertex).scalarMultiply(v);

                for (double w = 0.0; w <= 1.0; w += wJump) {
                    if (v + w <= 1.0) {
                        Vector3D wCA = cVertex.subtract(aVertex).scalarMultiply(w);
                        Vector3D point = aVertex.add(vBA).add(wCA);

                        result.add(point);
                    }
                }
            }
        }

        return result;
    }

    private double GetInterpolationJump(Vector3D first, Vector3D second) {
        double jump = first.distance(second);
        if (jump == 0.0) jump = 1.0;
        return 1.0 / (2 * jump);
    }

    // Assumes convex polygon to simplify triangulation
    // Pick a base vertex and create triangles from it
    private List<Triangle> triangulate(List<Vector3D> vertices) {
        List<Triangle> triangles = new ArrayList<>();

        Vector3D base = vertices.get(0);

        for (int i = 1; i < vertices.size() - 1; ++i) {
            Triangle tri = new Triangle(base, vertices.get(i), vertices.get(i + 1));
            triangles.add(tri);
        }

        return triangles;
    }

    private class Triangle {
        Vector3D[] vertices = new Vector3D[3];

        Triangle(Vector3D first, Vector3D second, Vector3D third) {
            vertices[0] = first;
            vertices[1] = second;
            vertices[2] = third;
        }
    }
}

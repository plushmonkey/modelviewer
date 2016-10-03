package com.plushnode.modelviewer.geometry;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<Vertex> vertices = new ArrayList<>();
    private List<Face> faces = new ArrayList<>();
    private List<Vector2D> uvs = new ArrayList<>();
    private String name;

    private Vector3D translation = Vector3D.ZERO;
    private Vector3D rotation = Vector3D.ZERO;
    private Vector3D scale = new Vector3D(1, 1, 1);

    public Model(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Vector3D getTranslation() {
        return this.translation;
    }

    public Vector3D getRotation() {
        return this.rotation;
    }

    public Vector3D getScale() {
        return this.scale;
    }

    public void setTranslation(Vector3D translation) {
        this.translation = translation;
    }

    public void setRotation(Vector3D rotation) {
        this.rotation = rotation;
    }

    public void setScale(Vector3D scale) {
        this.scale = scale;
    }

    public void addFace(Face face) {
        this.faces.add(face);
    }

    public void addUV(double u, double v) {
        this.uvs.add(new Vector2D(u, v));
        System.out.println("Adding UV (" + u + ", " + v + ") to model.");
    }

    public List<Vector2D> getUVs() {
        return this.uvs;
    }

    public void addVertex(Vertex vertex) {
        this.vertices.add(vertex);
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Face> getFaces() {
        return faces;
    }

    public Vector3D getMin() {
        List<Face> faces = getFaces();
        List<Vertex> vertices = getVertices();

        double xMin = 0, yMin = 0, zMin = 0;

        for (int i = 0; i < faces.size(); ++i) {
            Face face = faces.get(i);

            for (int a = 0; a < 3; ++a) {
                Vector3D vertex = vertices.get(face.getIndex(a)).getPosition();

                xMin = Math.min(vertex.getX(), xMin);
                yMin = Math.min(vertex.getY(), yMin);
                zMin = Math.min(vertex.getZ(), zMin);
            }
        }
        return new Vector3D(xMin, yMin, zMin);
    }

    public Vector3D getMax() {
        List<Face> faces = getFaces();
        List<Vertex> vertices = getVertices();

        double xMax = 0, yMax = 0, zMax = 0;

        for (int i = 0; i < faces.size(); ++i) {
            Face face = faces.get(i);

            for (int a = 0; a < 3; ++a) {
                Vector3D vertex = vertices.get(face.getIndex(a)).getPosition();

                xMax = Math.max(vertex.getX(), xMax);
                yMax = Math.max(vertex.getY(), yMax);
                zMax = Math.max(vertex.getZ(), zMax);
            }
        }
        return new Vector3D(xMax, yMax, zMax);
    }

    public Vector3D getSize() {
        Vector3D min = getMin();
        Vector3D max = getMax();

        return new Vector3D(max.getX() - min.getX(), max.getY() - min.getY(), max.getZ() - min.getZ());
    }
}

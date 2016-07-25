package com.plushnode.modelviewer.geometry;

import com.plushnode.modelviewer.fbx.node.FBXNode;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private FBXNode node;
    private FBXNode geometryNode;
    private List<Vector3D> vertices = new ArrayList<>();
    private List<Face> faces = new ArrayList<>();
    private List<FBXNode> materials = new ArrayList<>();

    public Model(FBXNode node, FBXNode geometry) {
        this.node = node;
        this.geometryNode = geometry;
    }

    public void addMaterial(FBXNode material) {
        this.materials.add(material);
    }

    public List<FBXNode> getMaterials() {
        return materials;
    }

    public FBXNode getNode() {
        return node;
    }

    public FBXNode getGeometry() {
        return geometryNode;
    }

    public void addFace(Face face) {
        this.faces.add(face);
    }

    public void addVertex(Vector3D vertex) {
        this.vertices.add(vertex);
    }

    public List<Vector3D> getVertices() {
        return vertices;
    }

    public List<Face> getFaces() {
        return faces;
    }

    public Vector3D getMin() {
        List<Face> faces = getFaces();
        List<Vector3D> vertices = getVertices();

        double xMin = 0, yMin = 0, zMin = 0;

        for (int i = 0; i < faces.size(); ++i) {
            Face face = faces.get(i);

            for (int a = 0; a < 3; ++a) {
                Vector3D vertex = vertices.get(face.getIndex(a));

                xMin = Math.min(vertex.getX(), xMin);
                yMin = Math.min(vertex.getY(), yMin);
                zMin = Math.min(vertex.getZ(), zMin);
            }
        }
        return new Vector3D(xMin, yMin, zMin);
    }

    public Vector3D getMax() {
        List<Face> faces = getFaces();
        List<Vector3D> vertices = getVertices();

        double xMax = 0, yMax = 0, zMax = 0;

        for (int i = 0; i < faces.size(); ++i) {
            Face face = faces.get(i);

            for (int a = 0; a < 3; ++a) {
                Vector3D vertex = vertices.get(face.getIndex(a));

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

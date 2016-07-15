package com.plushnode.modelviewer.geometry;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private List<Vector> vertices = new ArrayList<>();
    private List<Face> faces = new ArrayList<>();

    public void addFace(Face face) {
        this.faces.add(face);
    }

    public void addVertex(Vector vertex) {
        this.vertices.add(vertex);
    }

    public List<Vector> getVertices() {
        return vertices;
    }

    public List<Face> getFaces() {
        return faces;
    }
}

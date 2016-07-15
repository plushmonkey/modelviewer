package com.plushnode.modelviewer;

import com.plushnode.modelviewer.fbx.FBXDocument;
import com.plushnode.modelviewer.fbx.FBXNode;
import com.plushnode.modelviewer.geometry.Face;
import com.plushnode.modelviewer.geometry.Model;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ModelLoader {
    public static Model load(FBXDocument document) {
        FBXNode geometryNode = document.getNode("Objects").getNode("Geometry");
        if (geometryNode == null) return null;

        FBXNode vertexNode = geometryNode.getNode("Vertices");
        if (vertexNode == null) return null;

        FBXNode indicesNode = geometryNode.getNode("PolygonVertexIndex");
        if (indicesNode == null) return null;

        Model model = new Model();

        @SuppressWarnings("unchecked")
        ArrayList<Double> verticesList = (ArrayList<Double>)vertexNode.getProperties().get(0);

        for (int i = 0; i < verticesList.size(); i += 3)
            model.addVertex(new Vector(verticesList.get(i), verticesList.get(i + 1), verticesList.get(i + 2)));

        @SuppressWarnings("unchecked")
        List<Integer> indices = (ArrayList<Integer>)indicesNode.getProperties().get(0);

        for (int i = 0; i < indices.size(); i += 3) {
            Face face = new Face(indices.get(i), indices.get(i + 1), ~indices.get(i + 2));

            model.addFace(face);
        }

        return model;
    }
}

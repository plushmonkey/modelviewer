package com.plushnode.modelviewer.util;

import com.plushnode.modelviewer.fbx.FBXDocument;
import com.plushnode.modelviewer.fbx.node.FBXNode;
import com.plushnode.modelviewer.fbx.node.FBXNodeProperty;
import com.plushnode.modelviewer.fbx.node.FBXNodePropertyType;
import com.plushnode.modelviewer.geometry.Face;
import com.plushnode.modelviewer.geometry.Model;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.ArrayList;
import java.util.List;

public class ModelLoader {
    private static String getDepthDisplay(int depth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; ++i)
            sb.append('-');
        return sb.toString();
    }
    private static void displayNode(FBXNode node, int depth) {
        if (node == null) return;

        String depthStr = getDepthDisplay(depth);

        System.out.println(depthStr + node.getName());

        if (node.getName().equals("Properties70")) {
            displayPropertyTypes(node);
        }

        for (FBXNode subnode : node.getNodes()) {
            displayNode(subnode, depth + 1);
        }
    }

    private static void displayPropertyTypes(FBXNode node) {
        System.out.println(node.getName() + " properties:");
        for (FBXNodeProperty property : node.getProperties()) {
            System.out.println("-- " + property.getType());
        }
    }

    private static int getMaxFaceSize(List<Integer> indices) {
        int max = 0;
        int current = 0;

        for (int i = 0; i < indices.size(); ++i) {
            current += 1;
            if (indices.get(i) < 0) {
                if (current > max)
                    max = current;
                current = 0;
            }
        }
        return max;
    }

    private static void displayDocumentNodes(FBXDocument document) {
        for (FBXNode node : document.getNodes()) {
            displayNode(node, 0);
        }
    }

    public static List<Model> load(FBXDocument document) {
        //displayDocumentNodes(document);
        FBXNode objectNode = document.getNode("Objects");
        if (objectNode == null) return null;

        FBXNode definitionsNode = document.getNode("Definitions");
        if (definitionsNode == null) return null;

        List<FBXNode> geometryNodes = objectNode.getNodes("Geometry");
        List<FBXNode> modelNodes = objectNode.getNodes("Model");

        List<Model> models = new ArrayList<>();

        for (int i = 0; i < geometryNodes.size(); ++i) {
            FBXNode geometry = geometryNodes.get(i);

            Model model = new Model();

            FBXNode vertexNode = geometry.getNode("Vertices");
            if (vertexNode == null) continue;

            FBXNode indicesNode = geometry.getNode("PolygonVertexIndex");
            if (indicesNode == null) continue;

            List<FBXNodeProperty> vertexProperties = vertexNode.getProperties();
            if (vertexProperties.isEmpty() || vertexProperties.get(0).getType() != FBXNodePropertyType.DOUBLE_LIST)
                return null;

            List<Double> verticesList = vertexProperties.get(0).getDoubleList();

            for (int j = 0; j < verticesList.size(); j += 3)
                model.addVertex(new Vector3D(verticesList.get(j), verticesList.get(j + 1), verticesList.get(j + 2)));

            List<FBXNodeProperty> indicesProperties = indicesNode.getProperties();
            if (indicesProperties.isEmpty() || indicesProperties.get(0).getType() != FBXNodePropertyType.INTEGER_LIST)
                return null;

            List<Integer> indices = indicesProperties.get(0).getIntList();

            int faceSize = getMaxFaceSize(indices);
            if (faceSize > 3) {
                System.out.println("Found face with " + " vertices");
                continue;
            }

            for (int j = 0; j < indices.size(); j += 3) {
                Face face = new Face(indices.get(j), indices.get(j + 1), ~indices.get(j + 2));

                model.addFace(face);
            }

            models.add(model);
        }

        return models;
    }
}

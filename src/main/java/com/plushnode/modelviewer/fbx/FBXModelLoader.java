package com.plushnode.modelviewer.fbx;

import com.plushnode.modelviewer.fbx.node.FBXNode;
import com.plushnode.modelviewer.fbx.node.FBXNodeContainer;
import com.plushnode.modelviewer.fbx.node.FBXNodeProperty;
import com.plushnode.modelviewer.fbx.node.FBXNodePropertyType;
import com.plushnode.modelviewer.fbx.property.FBXPropertiesLoader;
import com.plushnode.modelviewer.fbx.property.FBXProperty;
import com.plushnode.modelviewer.fbx.property.FBXPropertyStore;
import com.plushnode.modelviewer.geometry.Face;
import com.plushnode.modelviewer.geometry.Model;
import com.plushnode.modelviewer.geometry.Vertex;
import com.plushnode.modelviewer.scene.Scene;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FBXModelLoader {
    private static void displayProperties(FBXNode node) {
        for (FBXNodeProperty prop : node.getProperties()) {
            System.out.println(prop.getType());
            if (prop.getType() == FBXNodePropertyType.STRING) {
                System.out.println(prop.getString());
            }
            if (prop.getType() == FBXNodePropertyType.LONG) {
                System.out.println(prop.getLong());
            }
        }
        System.out.println("------");
    }

    public static List<Model> load(FBXDocument document, Scene scene) {
        FBXNode objectNode = document.getNode("Objects");
        if (objectNode == null) return null;

        List<FBXNode> geometryNodes = objectNode.getNodes("Geometry");
        Map<Long, FBXNode> meshNodes = getMeshModels(objectNode.getNodes("Model"));

        List<Model> models = new ArrayList<>();

        Map<Long, Long> connections = new HashMap<>();

        for (FBXNode connectionNode : document.getNode("Connections").getNodes()) {
            Long from = connectionNode.getProperty(1).getLong();
            Long to = connectionNode.getProperty(2).getLong();

            connections.put(from, to);
        }

        for (int i = 0; i < geometryNodes.size(); ++i) {
            FBXNode geometry = geometryNodes.get(i);

            String geometryType = geometry.getProperty(2).getString();
            if (!geometryType.equalsIgnoreCase("mesh")) continue;

            Long geometryId = geometry.getProperty(0).getLong();
            Long parentId = connections.get(geometryId);

            if (parentId == null) {
                System.out.println("No parent for geometry");
                continue;
            }

            FBXNode meshNode = meshNodes.get(parentId);
            if (meshNode == null) {
                System.out.println("MeshNode null");
                continue;
            }

            Long modelId = meshNode.getProperty(0).getLong();
            String name = meshNode.getProperty(1).getString();
            Model model = new Model(name);

            FBXNode vertexNode = geometry.getNode("Vertices");
            if (vertexNode == null) continue;

            FBXNode indicesNode = geometry.getNode("PolygonVertexIndex");
            if (indicesNode == null) continue;

            FBXNode uvLayer = geometry.getNode("LayerElementUV");
            FBXNode uvNode = null;
            FBXNode uvIndicesNode = null;

            if (uvLayer != null) {
                uvNode = uvLayer.getNode("UV");
                uvIndicesNode = uvLayer.getNode("UVIndex");
            }

            if (uvNode != null) {
                List<Double> uvs = uvNode.getProperties().get(0).getDoubleList();

                for (int j = 0; j < uvs.size() - 1; j += 2)
                    model.addUV(uvs.get(j), uvs.get(j + 1));
            }

            List<FBXNodeProperty> vertexProperties = vertexNode.getProperties();
            if (vertexProperties.isEmpty() || vertexProperties.get(0).getType() != FBXNodePropertyType.DOUBLE_LIST)
                continue;

            List<Double> verticesList = vertexProperties.get(0).getDoubleList();

            for (int j = 0; j < verticesList.size(); j += 3) {
                Vector3D position = new Vector3D(verticesList.get(j), verticesList.get(j + 1), verticesList.get(j + 2));
                Vertex vertex = new Vertex(position);

                model.addVertex(vertex);
            }

            List<FBXNodeProperty> indicesProperties = indicesNode.getProperties();
            if (indicesProperties.isEmpty() || indicesProperties.get(0).getType() != FBXNodePropertyType.INTEGER_LIST)
                continue;

            List<Integer> indices = indicesProperties.get(0).getIntList();
            List<Integer> uvIndices = null;

            if (uvIndicesNode != null)
                uvIndices = uvIndicesNode.getProperties().get(0).getIntList();

            List<Face> faces = getFacesFromIndices(indices, uvIndices);
            FBXNode layerMaterialNode = geometry.getNode("LayerElementMaterial");

            if (layerMaterialNode != null) {
                FBXNode materials = layerMaterialNode.getNode("Materials");
                String mapping = geometry.getNode("LayerElementMaterial").getNode("MappingInformationType").getProperty(0).getString();
                List<Integer> materialIndices = materials.getProperties().get(0).getIntList();

                if (mapping.equalsIgnoreCase("AllSame")) {
                    int materialIndex = materialIndices.get(materialIndices.get(0));

                    for (int faceIndex = 0; faceIndex < faces.size(); ++faceIndex) {

                        faces.get(faceIndex).setMaterialIndex(materialIndex);
                    }
                } else {
                    for (int faceIndex = 0; faceIndex < faces.size(); ++faceIndex) {
                        int materialIndex = materialIndices.get(faceIndex);

                        faces.get(faceIndex).setMaterialIndex(materialIndex);
                    }
                }
            }

            faces.forEach((Face face) -> model.addFace(face));

            FBXPropertyStore modelPropertyStore = FBXPropertiesLoader.loadProperties(meshNode.getNode("Properties70"));

            FBXProperty translation = modelPropertyStore.getProperty("Lcl Translation");
            FBXProperty rotation = modelPropertyStore.getProperty("Lcl Rotation");
            FBXProperty scaling = modelPropertyStore.getProperty("Lcl Scaling");

            if (translation != null)
                model.setTranslation(translation.getValue().asVector());
            if (rotation != null)
                model.setRotation(rotation.getValue().asVector());
            if (scaling != null)
                model.setScale(scaling.getValue().asVector());

            models.add(model);
            scene.addModel(modelId, model);
        }

        return models;
    }


    private static Map<Long, FBXNode> getMaterials(List<FBXNode> materialNodes) {
        Map<Long, FBXNode> materials = new HashMap<>();

        for (FBXNode node : materialNodes) {
            List<FBXNodeProperty> nodeProperties = node.getProperties();

            Long id = node.getProperty(0).getLong();

            materials.put(id, node);
        }
        return materials;
    }

    private static Map<Long, FBXNode> getMeshModels(List<FBXNode> models) {
        Map<Long, FBXNode> meshes = new HashMap<>();

        for (FBXNode model : models) {
            List<FBXNodeProperty> nodeProperties = model.getProperties();

            if (nodeProperties.size() < 3) continue;

            String type = nodeProperties.get(2).getString();

            if (type.equalsIgnoreCase("mesh"))
                meshes.put(nodeProperties.get(0).getLong(), model);
        }
        return meshes;
    }

    private static List<Face> getFacesFromIndices(List<Integer> indices, List<Integer> uvIndices) {
        List<Face> faces = new ArrayList<>();
        Face face = new Face();

        for (int i = 0; i < indices.size(); ++i) {
            int index = indices.get(i);

            if (uvIndices != null)
                face.addUvIndex(uvIndices.get(i));

            if (index < 0) {
                face.addIndex(~index);
                faces.add(face);
                face = new Face();
            } else {
                face.addIndex(index);
            }
        }

        return faces;
    }

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
            FBXPropertyStore store = FBXPropertiesLoader.loadProperties(node);

            for (FBXProperty prop : store.getProperties()) {
                System.out.println(depthStr + " *" + prop);
            }
            //System.out.println(store);
        } else {
            for (FBXNodeProperty prop : node.getProperties()) {
                System.out.println(depthStr + " *" + prop);
            }

            for (FBXNode subnode : node.getNodes()) {
                displayNode(subnode, depth + 1);
            }
        }
    }
}

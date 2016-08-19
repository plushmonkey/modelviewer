package com.plushnode.modelviewer.fbx;

import com.plushnode.modelviewer.fbx.node.FBXNode;
import com.plushnode.modelviewer.fbx.property.FBXPropertiesLoader;
import com.plushnode.modelviewer.fbx.property.FBXProperty;
import com.plushnode.modelviewer.fbx.property.FBXPropertyStore;
import com.plushnode.modelviewer.geometry.Model;
import com.plushnode.modelviewer.scene.SceneCreator;
import com.plushnode.modelviewer.scene.SceneNode;
import com.plushnode.modelviewer.scene.Transform;
import com.plushnode.modelviewer.util.ColorMatcher;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.*;

public class FBXSceneCreator implements SceneCreator {
    private FBXDocument document;
    private Map<Long, List<Long>> connections = new HashMap<>();

    public FBXSceneCreator(FBXDocument document) {
        this.document = document;
    }

    public SceneNode createScene() {
        SceneNode root = new SceneNode("Scene", null, new Transform());
        List<Model> models = FBXModelLoader.load(document);

        loadConnections();
        Map<Long, Material> materials = getMaterials();

        root.setId(0);

        Map<Long, SceneNode> sceneNodeMap = new HashMap<>();

        if (models == null) return root;

        sceneNodeMap.put((long)0, root);

        for (int i = 0; i < models.size(); ++i) {
            Model model = models.get(i);

            SceneNode node = new SceneNode(model, new Transform());

            node.setId(model.getNode().getProperty(0).getLong());

            sceneNodeMap.put(node.getId(), node);
        }

        for (SceneNode node : sceneNodeMap.values()) {
            List<Long> toList = connections.get(node.getId());

            if (toList == null) continue;

            for (Long to : toList) {
                if (to != null) {
                    SceneNode parent = sceneNodeMap.get(to);

                    if (parent != null) {
                        System.out.println("Formed connection: " + node.getId() + " -> " + parent.getId());
                        transformNode(node, parent);
                        parent.addChild(node);
                    }
                }
            }
        }


        /*for (Map.Entry<Long, List<Long>> entry : connections.entrySet()) {
            Long from = entry.getKey();
            List<Long> toList = entry.getValue();

            Material material = materials.get(from);

            if (material == null) continue;

            for (Long to : toList) {
                SceneNode node = sceneNodeMap.get(to);

                if (node == null) continue;

                node.addMaterial(material.node);

                System.out.println("Connecting material to " + node.getName());
                FBXProperty diffuseColorProperty = material.properties.getProperty("DiffuseColor");

                if (diffuseColorProperty == null) continue;

                Vector3D color = diffuseColorProperty.getValue().asVector();

                System.out.println("Color: " + color);

                ColorMatcher.Type type = ColorMatcher.getInstance().getTypeFromColor(color);

                node.setType(type.id, type.data);
            }
        }*/

        List<FBXNode> connectionNodes = document.getNode("Connections").getNodes();
        //Collections.reverse(connectionNodes);
        for (FBXNode connectionNode : connectionNodes) {
            Long from = connectionNode.getProperty(1).getLong();
            Long to = connectionNode.getProperty(2).getLong();

            Material material = materials.get(from);
            if (material == null) continue;

            SceneNode node = sceneNodeMap.get(to);
            if (node == null) continue;

            node.addMaterial(material.node);
        }

        /*for (Material material : materials.values()) {
            List<Long> toList = connections.get(material.id);

            if (toList == null) continue;

            for (Long to : toList) {
                SceneNode node = sceneNodeMap.get(to);

                if (node == null) continue;

                node.addMaterial(material.node);

                System.out.println("Connecting material to " + node.getName());
                FBXProperty diffuseColorProperty = material.properties.getProperty("DiffuseColor");

                if (diffuseColorProperty == null) continue;

                Vector3D color = diffuseColorProperty.getValue().asVector();

                System.out.println("Color: " + color);

                ColorMatcher.Type type = ColorMatcher.getInstance().getTypeFromColor(color);

                node.setType(type.id, type.data);
            }
        }*/

        return root;
    }

    private void loadConnections() {
        for (FBXNode connectionNode : document.getNode("Connections").getNodes()) {
            Long from = connectionNode.getProperty(1).getLong();
            Long to = connectionNode.getProperty(2).getLong();

            List<Long> toList = this.connections.get(from);
            if (toList == null)
                toList = new ArrayList<>();

            toList.add(to);
            this.connections.put(from, toList);
        }
    }

    private Map<Long, Material> getMaterials() {
        Map<Long, Material> materials = new HashMap<>();
        List<FBXNode> materialNodes = document.getNode("Objects").getNodes("Material");

        if (materialNodes == null) return materials;

        for (FBXNode node : materialNodes) {
            FBXNode propertiesNode = node.getNode("Properties70");
            FBXPropertyStore store = FBXPropertiesLoader.loadProperties(propertiesNode);
            long id = node.getProperty(0).getLong();

            Material material = new Material(id, node, store);
            materials.put(id, material);
        }
        return materials;
    }

    private void transformNode(SceneNode sceneNode, SceneNode parent) {
        Rotation xRot = new Rotation(Vector3D.PLUS_I, 0);
        Rotation yRot = new Rotation(Vector3D.PLUS_J, 0);
        Rotation zRot = new Rotation(Vector3D.PLUS_K, 0);

        Vector3D modelTranslation = Vector3D.ZERO;
        Vector3D modelScaling = new Vector3D(1, 1, 1);

        for (FBXNode node : sceneNode.getModel().getNode().getNodes()) {
            if (node.getName().equalsIgnoreCase("Properties70")) {
                FBXPropertyStore properties = FBXPropertiesLoader.loadProperties(node);
                FBXProperty translation = properties.getProperty("Lcl Translation");
                FBXProperty scaling = properties.getProperty("Lcl Scaling");
                FBXProperty rotation = properties.getProperty("Lcl Rotation");

                if (scaling != null) {
                    modelScaling = scaling.getValue().asVector();
                    System.out.println("Scale: " + modelScaling);
                }

                if (translation != null) {
                    modelTranslation = translation.getValue().asVector();
                    System.out.println("Translation: " + modelTranslation);
                }

                if (rotation != null) {
                    Vector3D value = rotation.getValue().asVector();
                    System.out.println("Rotation: " + value);

                    /*xRot = new Rotation(Vector3D.PLUS_I, value.getX());
                    yRot = new Rotation(Vector3D.PLUS_J, value.getY());
                    zRot = new Rotation(Vector3D.PLUS_K, value.getZ());*/
                }
            }
        }

        if (parent.getId() == 0)
            modelTranslation = modelTranslation.scalarMultiply(1/100.0);

        sceneNode.getTransform().setTranslation(modelTranslation);

        sceneNode.getTransform().rotate(xRot);
        sceneNode.getTransform().rotate(yRot);
        sceneNode.getTransform().rotate(zRot);
    }

    private class Material {
        long id;
        FBXPropertyStore properties;
        FBXNode node;

        public Material(long id, FBXNode node, FBXPropertyStore properties) {
            this.id = id;
            this.node = node;
            this.properties = properties;
        }
    }
}

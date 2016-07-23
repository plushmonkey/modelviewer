package com.plushnode.modelviewer.fbx;

import com.plushnode.modelviewer.fbx.node.FBXNode;
import com.plushnode.modelviewer.fbx.property.FBXPropertiesLoader;
import com.plushnode.modelviewer.fbx.property.FBXProperty;
import com.plushnode.modelviewer.fbx.property.FBXPropertyStore;
import com.plushnode.modelviewer.geometry.Model;
import com.plushnode.modelviewer.scene.SceneCreator;
import com.plushnode.modelviewer.scene.SceneNode;
import com.plushnode.modelviewer.scene.Transform;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.*;

public class FBXSceneCreator implements SceneCreator {
    private FBXDocument document;
    private Map<Long, Long> connections = new HashMap<>();

    public FBXSceneCreator(FBXDocument document) {
        this.document = document;
    }

    public SceneNode createScene() {
        SceneNode root = new SceneNode("Scene", null, new Transform());
        List<Model> models = FBXModelLoader.load(document);

        loadConnections();
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
            Long to = connections.get(node.getId());

            if (to != null) {
                SceneNode parent = sceneNodeMap.get(to);

                if (parent != null) {
                    System.out.println("Formed connection: " + node.getId() + " -> " + parent.getId());
                    transformNode(node, parent);
                    parent.addChild(node);
                }
            }
        }

        return root;
    }

    private void loadConnections() {
        for (FBXNode connectionNode : document.getNode("Connections").getNodes()) {
            Long from = connectionNode.getProperty(1).getLong();
            Long to = connectionNode.getProperty(2).getLong();

            this.connections.put(from, to);
        }
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
}

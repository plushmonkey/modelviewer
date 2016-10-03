package com.plushnode.modelviewer.fbx;

import com.plushnode.modelviewer.fbx.node.FBXNode;
import com.plushnode.modelviewer.fbx.node.FBXNodeProperty;
import com.plushnode.modelviewer.fbx.property.*;
import com.plushnode.modelviewer.geometry.Model;
import com.plushnode.modelviewer.material.Material;
import com.plushnode.modelviewer.scene.Scene;
import com.plushnode.modelviewer.scene.SceneCreator;
import com.plushnode.modelviewer.scene.SceneNode;
import com.plushnode.modelviewer.scene.Transform;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FBXSceneCreator implements SceneCreator {
    private FBXDocument document;

    public FBXSceneCreator(FBXDocument document) {
        this.document = document;
    }

    public Scene createScene() {
        SceneNode root = new SceneNode("Scene", null, new Transform());
        Scene scene = new Scene();

        root.setId(0);
        scene.setRootNode(root);

        if (document.getNode("Objects") == null) return scene;

        FBXModelLoader.load(document, scene);

        loadMaterials(scene);
        loadTextures(scene);

        Map<Long, SceneNode> sceneNodeMap = loadSceneNodes(scene);

        FBXNode connectionsNode = document.getNode("Connections");
        if (connectionsNode == null) return scene;

        List<FBXNode> connectionNodes = connectionsNode.getNodes();
        for (FBXNode connectionNode : connectionNodes) {
            Long childId = connectionNode.getProperty(1).getLong();
            Long parentId = connectionNode.getProperty(2).getLong();

            SceneNode childSceneNode = sceneNodeMap.get(childId);
            if (childSceneNode != null) {
                SceneNode parentSceneNode = sceneNodeMap.get(parentId);

                if (parentSceneNode != null) {
                    parentSceneNode.addChild(childSceneNode);

                    System.out.println("Formed connection: " + childSceneNode.getId() + " -> " + parentSceneNode.getId());
                }

                continue;
            }

            Material childMaterial = scene.getMaterial(childId);
            if (childMaterial != null) {
                SceneNode parentSceneNode = sceneNodeMap.get(parentId);

                if (parentSceneNode != null) {
                    parentSceneNode.addMaterial(childMaterial);
                    System.out.println("Added material to scene node.");
                }
                continue;
            }

            FBXImageTexture childTexture = (FBXImageTexture)scene.getTexture(childId);
            if (childTexture != null) {
                FBXMaterial parentMaterial = (FBXMaterial)scene.getMaterial(parentId);

                if (parentMaterial != null) {
                    parentMaterial.setTexture(childTexture);
                    System.out.println("Added " + childTexture.getName() + " texture to material.");
                }
            }
        }

        transformChildren(scene.getRootNode());

        for (SceneNode child : scene.getRootNode().getChildren()) {
            Vector3D translation = child.getTransform().getTranslation();

            child.getTransform().setTranslation(translation.scalarMultiply(1 / 100.0));
        }

        return scene;
    }

    private void loadMaterials(Scene scene) {
        List<FBXNode> materialNodes = document.getNode("Objects").getNodes("Material");

        for (FBXNode node : materialNodes) {
            List<FBXNodeProperty> nodeProperties = node.getProperties();

            if (nodeProperties.size() < 2) continue;

            Long id = nodeProperties.get(0).getLong();
            String name = nodeProperties.get(1).getString();

            FBXMaterial material = new FBXMaterial(id, name, node);

            FBXPropertyStore store = FBXPropertiesLoader.loadProperties(node.getNode("Properties70"));

            FBXProperty diffuseProperty = store.getProperty("DiffuseColor");
            Vector3D diffuseColor = Vector3D.ZERO;

            if (diffuseProperty != null)
                diffuseColor = diffuseProperty.getValue().asVector();

            material.setDiffuseColor(diffuseColor);
            scene.addMaterial(id, material);
        }
    }

    private void loadTextures(Scene scene) {
        List<FBXNode> textureNodes = document.getNode("Objects").getNodes("Texture");

        if (textureNodes == null) {
            System.out.println("No textures in FBX Document");
            return;
        }

        for (FBXNode node : textureNodes) {
            List<FBXNodeProperty> nodeProperties = node.getProperties();

            if (nodeProperties.size() < 2) continue;

            Long id = nodeProperties.get(0).getLong();
            String name = nodeProperties.get(1).getString();

            FBXNode filenameNode = node.getNode("FileName");
            if (filenameNode == null) {
                System.out.println("Texture has no filename node.");
                continue;
            }

            String filename = filenameNode.getProperty(0).getString();

            try {
                BufferedImage image = ImageIO.read(new File(filename));

                if (image != null) {
                    System.out.println("Loaded texture " + filename + ".");

                    FBXImageTexture texture = new FBXImageTexture(id, name, node, image);

                    scene.addTexture(id, texture);
                } else {
                    System.out.println("Failed to load texture " + filename);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Map<Long, SceneNode> loadSceneNodes(Scene scene) {
        Map<Long, SceneNode> sceneNodeMap = new HashMap<>();

        sceneNodeMap.put((long)0, scene.getRootNode());

        Map<Long, Model> models = scene.getModels();

        for (Map.Entry<Long, Model> entry : models.entrySet()) {
            Long id = entry.getKey();
            Model model = entry.getValue();

            SceneNode node = new SceneNode(model, new Transform());
            node.setId(id);
            sceneNodeMap.put(id, node);
        }

        return sceneNodeMap;
    }

    private void transformChildren(SceneNode current) {
        transformNode(current);

        for (SceneNode child : current.getChildren())
            transformChildren(child);
    }

    private void transformNode(SceneNode sceneNode) {
        if (sceneNode.getModel() == null) return;

        Rotation xRot = new Rotation(Vector3D.PLUS_I, 0);
        Rotation yRot = new Rotation(Vector3D.PLUS_J, 0);
        Rotation zRot = new Rotation(Vector3D.PLUS_K, 0);

        Vector3D modelTranslation = sceneNode.getModel().getTranslation();
        Vector3D modelScaling = sceneNode.getModel().getScale();

        System.out.println("Scale: " + modelScaling);
        System.out.println("Translation: " + modelTranslation);
        System.out.println("Rotation: " + sceneNode.getModel().getRotation());

        /*xRot = new Rotation(Vector3D.PLUS_I, value.getX());
        yRot = new Rotation(Vector3D.PLUS_J, value.getY());
        zRot = new Rotation(Vector3D.PLUS_K, value.getZ());*/

        sceneNode.getTransform().setTranslation(modelTranslation);

        sceneNode.getTransform().rotate(xRot);
        sceneNode.getTransform().rotate(yRot);
        sceneNode.getTransform().rotate(zRot);
    }
}

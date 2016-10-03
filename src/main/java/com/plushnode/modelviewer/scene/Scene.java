package com.plushnode.modelviewer.scene;

import com.plushnode.modelviewer.geometry.Model;
import com.plushnode.modelviewer.material.Material;
import com.plushnode.modelviewer.material.Texture;

import java.util.HashMap;
import java.util.Map;

public class Scene {
    private Map<Long, Model> models = new HashMap<>();
    private Map<Long, Material> materials = new HashMap<>();
    private Map<Long, Texture> textures = new HashMap<>();

    private SceneNode rootNode;

    public void addMaterial(Long id, Material material) {
        materials.put(id, material);
    }

    public Material getMaterial(Long id) {
        return materials.get(id);
    }

    public void addTexture(Long id, Texture texture) {
        textures.put(id, texture);
    }

    public Texture getTexture(Long id) {
        return textures.get(id);
    }

    public void addModel(Long id, Model model) {
        models.put(id, model);
    }

    public Model getModel(Long id) {
        return models.get(id);
    }

    public Map<Long, Model> getModels() {
        return this.models;
    }

    public void setRootNode(SceneNode node) {
        this.rootNode = node;
    }

    public SceneNode getRootNode() {
        return this.rootNode;
    }
}

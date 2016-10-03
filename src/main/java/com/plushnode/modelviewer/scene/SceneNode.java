package com.plushnode.modelviewer.scene;

import com.plushnode.modelviewer.geometry.Model;
import com.plushnode.modelviewer.material.Material;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.ArrayList;
import java.util.List;

public class SceneNode {
    private String name;
    private Model model;
    private Transform transform;
    private List<SceneNode> children = new ArrayList<>();
    private long id;
    private List<Material> materials = new ArrayList<>();

    // Material to render with
    private int typeId = 1;
    private byte typeData = 4;

    public SceneNode(Model model, Transform transform) {
        this.model = model;
        this.transform = transform;

        this.name = this.model.getName();
    }

    public SceneNode(String name, Model model, Transform transform) {
        this.model = model;
        this.transform = transform;
        this.name = name;
    }

    public void addMaterial(Material material) {
        this.materials.add(material);
    }

    public Material getMaterial(int index) {
        return this.materials.get(index);
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setType(int typeId, byte typeData) {
        this.typeId = typeId;
        this.typeData = typeData;

        for (SceneNode child : children)
            child.setType(typeId, typeData);
    }

    public int getTypeId() {
        return this.typeId;
    }

    public byte getTypeData() {
        return this.typeData;
    }

    public String getName() {
        return name;
    }

    public Model getModel() {
        return this.model;
    }

    public Transform getTransform() {
        return this.transform;
    }

    public void addChild(SceneNode child) {
        this.children.add(child);
    }

    public List<SceneNode> getChildren() {
        return children;
    }

    public SceneNode getNodeById(long id) {
        if (this.id == id) return this;

        for (SceneNode child : children) {
            if (child.getId() == id) return child;
            return child.getNodeById(id);
        }

        return null;
    }

    public SceneNode getNodeByName(String name) {
        if (this.getName().equals(name))
            return this;

        for (SceneNode child : children) {
            if (child.getName().equals(name))
                return child;

            return child.getNodeByName(name);
        }

        return null;
    }

    public Vector3D getSize() {
        // todo: calculate boundary
        return Vector3D.ZERO;
    }
}

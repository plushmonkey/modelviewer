package com.plushnode.modelviewer.fbx.property;

import com.plushnode.modelviewer.fbx.FBXObject;
import com.plushnode.modelviewer.fbx.node.FBXNode;
import com.plushnode.modelviewer.material.Material;
import com.plushnode.modelviewer.material.Texture;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class FBXMaterial extends FBXObject implements Material {
    private Vector3D diffuseColor;
    private Texture texture;

    public FBXMaterial(long id, String name, FBXNode node) {
        super(id, name, node);
    }

    public FBXMaterial(long id, String name, FBXNode node, Vector3D diffuseColor) {
        super(id, name, node);

        setDiffuseColor(diffuseColor);
    }

    public FBXMaterial(long id, String name, FBXNode node, Texture texture) {
        super(id, name, node);

        setTexture(texture);
    }

    @Override
    public Vector3D getDiffuseColor() {
        return diffuseColor;
    }

    void setDiffuseColor(Vector3D diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    void setTexture(Texture texture) {
        this.texture = texture;
    }
}

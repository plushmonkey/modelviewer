package com.plushnode.modelviewer.fbx;

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

    void setDiffuseColor(Vector3D color) {
        this.diffuseColor = color;
    }

    void setTexture(Texture texture) {
        this.texture = texture;
    }

    @Override
    public Vector3D getDiffuseColor() {
        return diffuseColor;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}

package com.plushnode.modelviewer.fbx;

import com.plushnode.modelviewer.fbx.node.FBXNode;
import com.plushnode.modelviewer.material.Texture;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FBXImageTexture extends FBXObject implements Texture {
    private BufferedImage image;

    public FBXImageTexture(long id, String name, FBXNode node, BufferedImage image) {
        super(id, name, node);

        this.image = image;
    }

    public BufferedImage getImage() {
        return this.image;
    }

    @Override
    public Vector3D sample(int x, int y) {
        x = Math.min(Math.max(0, x), image.getWidth() - 1);
        y = Math.min(Math.max(0, y), image.getHeight() - 1);

        Color color = new Color(image.getRGB(x, y));

        return new Vector3D(color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0);
    }

    @Override
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public int getHeight() {
        return image.getHeight();
    }
}

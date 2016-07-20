package com.plushnode.modelviewer.renderer;

import com.plushnode.modelviewer.scene.Scene;
import org.bukkit.Location;
import org.bukkit.Material;

public interface Renderer {
    void renderBlock(Location location, Material type);
    void renderBlock(Location location, int typeId, byte typeData);

    // Called when the rendering is complete
    void addCallback(RenderCallback callback);
}

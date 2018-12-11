package com.plushnode.modelviewer.renderer;

import org.bukkit.Location;
import org.bukkit.Material;

public interface Renderer {
    void renderBlock(Location location, Material type);

    // Called when the rendering is complete
    void addCallback(RenderCallback callback);
}

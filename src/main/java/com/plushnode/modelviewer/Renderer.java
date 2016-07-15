package com.plushnode.modelviewer;

import org.bukkit.Location;
import org.bukkit.Material;

public interface Renderer {
    void renderBlock(Location location, Material type);
}

package com.plushnode.modelviewer.adapters;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public final class BukkitAdapter {
    public static Vector3D adapt(Vector v) {
        return new Vector3D(v.getX(), v.getY(), v.getZ());
    }

    public static Location adapt(Vector3D v, World world) {
        return new Location(world, v.getX(), v.getY(), v.getZ());
    }
}

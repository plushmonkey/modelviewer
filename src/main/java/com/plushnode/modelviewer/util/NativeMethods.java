package com.plushnode.modelviewer.util;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NativeMethods {
    private static NativeMethods instance;
    private Class<?> CraftWorld, World, BlockPosition, Block, Chunk, IBlockData;
    private Method getHandle, getChunkAt, getByCombinedId, a, notify;
    private boolean blockDataNotify = false;

    private NativeMethods() {
        try {
            setupReflection();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static NativeMethods getInstance() {
        if (instance == null) {
            instance = new NativeMethods();
        }
        return instance;
    }

    public boolean setBlockFast(Block block, int typeId, int data) {
        Location location = block.getLocation();

        try {
            int x = (int)((long)location.getX() >> 4);
            int z = (int)((long)location.getZ() >> 4);

            Object world = getHandle.invoke(CraftWorld.cast(block.getWorld()));
            Object chunk = getChunkAt.invoke(world, x, z);
            Constructor<?> blockPositionConstructor = BlockPosition.getConstructor(int.class, int.class, int.class);
            Object blockPosition = blockPositionConstructor.newInstance(location.getBlockX(), location.getBlockY(), location.getBlockZ());

            int fullData = typeId + (data << 12);
            Object newBlock = getByCombinedId.invoke(null, fullData);

            Object oldBlock = a.invoke(chunk, blockPosition, newBlock);
            if (blockDataNotify) {
                int flag = 0;
                notify.invoke(world, blockPosition, oldBlock, newBlock, flag);
            } else {
                notify.invoke(world, blockPosition);
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private void setupReflection() throws NoSuchMethodException {
        CraftWorld = getNMSClass("org.bukkit.craftbukkit.%s.CraftWorld");
        World = getNMSClass("net.minecraft.server.%s.World");
        BlockPosition = getNMSClass("net.minecraft.server.%s.BlockPosition");
        Block = getNMSClass("net.minecraft.server.%s.Block");
        IBlockData = getNMSClass("net.minecraft.server.%s.IBlockData");
        Chunk = getNMSClass("net.minecraft.server.%s.Chunk");
        getHandle = CraftWorld.getDeclaredMethod("getHandle");
        getChunkAt = World.getDeclaredMethod("getChunkAt", int.class, int.class);
        getByCombinedId = Block.getDeclaredMethod("getByCombinedId", int.class);
        a = Chunk.getDeclaredMethod("a", BlockPosition, IBlockData);

        try {
            notify = World.getDeclaredMethod("notify", BlockPosition);
        } catch (NoSuchMethodException e) {
            notify = null;
        }

        if (notify == null) {
            notify = World.getDeclaredMethod("notify", BlockPosition, IBlockData, IBlockData, int.class);
            blockDataNotify = true;
        }
    }

    private Class<?> getNMSClass(String nmsClass) {
        String version = null;

        Pattern pattern = Pattern.compile("net\\.minecraft\\.(?:server)?\\.(v(?:\\d+_)+R\\d)");
        for (Package p : Package.getPackages()) {
            String name = p.getName();
            Matcher m = pattern.matcher(name);
            if (m.matches()) {
                version = m.group(1);
            }
        }

        if (version == null) return null;

        try {
            return Class.forName(String.format(nmsClass, version));
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}

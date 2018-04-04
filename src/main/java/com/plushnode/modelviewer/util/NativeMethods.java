package com.plushnode.modelviewer.util;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NativeMethods {
    private static Class<?> CraftWorld, World, BlockPosition, NMSBlock, Chunk, ChunkSection, IBlockData;
    private static Constructor<?> blockPositionConstructor, chunkSectionConstructor;
    private static Method getHandle, getChunkAt, getByCombinedId, a, notify, setType, getType, getFlag, initLighting, chunkO;
    private static Field sections, worldProviderField;
    private static boolean blockDataNotify = false, enabled = false;
    private static Set<Object> chunkLightingSet = new HashSet<>();
    private static Queue<Object> chunkLightingQueue = new LinkedBlockingQueue<>();

    static {
        try {
            setupReflection();
            enabled = true;
            System.out.println("NativeMethods setup for ModelViewerPlugin.");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static boolean lightChunk() {
        if (!chunkLightingSet.isEmpty()) {
            chunkLightingQueue.addAll(chunkLightingSet);
            chunkLightingSet.clear();
        }

        if (chunkLightingQueue.isEmpty()) {
            return false;
        }

        Object chunk = chunkLightingQueue.poll();
        try {
            initLighting.invoke(chunk);
            chunkO.invoke(chunk);
        } catch (IllegalAccessException|InvocationTargetException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean setBlockFast(Block block, int typeId, int data, boolean notification) {
        if (!enabled) return false;

        if (block.getLocation().getY() < 0 || block.getLocation().getY() > 255) return true;

        Location location = block.getLocation();

        try {
            int x = (int)((long)location.getX() >> 4);
            int z = (int)((long)location.getZ() >> 4);

            Object world = getHandle.invoke(CraftWorld.cast(block.getWorld()));
            Object chunk = getChunkAt.invoke(world, x, z);
            Object blockPosition = blockPositionConstructor.newInstance(location.getBlockX(), location.getBlockY(), location.getBlockZ());

            int fullData = typeId + (data << 12);
            Object blockData = getByCombinedId.invoke(null, fullData);

            Object[] chunkSections = (Object[])sections.get(chunk);

            if (chunkSections == null) {
                return false;
            }

            Object chunkSection = chunkSections[location.getBlockY() >> 4];

            if (chunkSection == null) {
                Object worldProvider = worldProviderField.get(world);
                boolean flag = (Boolean)getFlag.invoke(worldProvider);

                int yPos = (location.getBlockY() >> 4) << 4;
                chunkSection = chunkSectionConstructor.newInstance(yPos, flag);
                chunkSections[location.getBlockY() >> 4] = chunkSection;
            }

            chunkLightingSet.add(chunk);

            int sectionX = location.getBlockX() & 15;
            int sectionY = location.getBlockY() & 15;
            int sectionZ = location.getBlockZ() & 15;

            Object oldBlockData = getType.invoke(chunkSection, sectionX, sectionY, sectionZ);

            setType.invoke(chunkSection, sectionX, sectionY, sectionZ, blockData);

            if (notification) {
                if (blockDataNotify) {
                    int flag = 0;
                    notify.invoke(world, blockPosition, oldBlockData, blockData, flag);
                } else {
                    notify.invoke(world, blockPosition);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static void setupReflection() throws NoSuchMethodException {
        CraftWorld = getNMSClass("org.bukkit.craftbukkit.%s.CraftWorld");
        World = getNMSClass("net.minecraft.server.%s.World");
        BlockPosition = getNMSClass("net.minecraft.server.%s.BlockPosition");
        NMSBlock = getNMSClass("net.minecraft.server.%s.Block");
        IBlockData = getNMSClass("net.minecraft.server.%s.IBlockData");
        Chunk = getNMSClass("net.minecraft.server.%s.Chunk");
        ChunkSection = getNMSClass("net.minecraft.server.%s.ChunkSection");

        blockPositionConstructor = BlockPosition.getConstructor(int.class, int.class, int.class);
        chunkSectionConstructor = ChunkSection.getConstructor(int.class, boolean.class);

        getHandle = CraftWorld.getDeclaredMethod("getHandle");
        getChunkAt = World.getDeclaredMethod("getChunkAt", int.class, int.class);
        getByCombinedId = NMSBlock.getDeclaredMethod("getByCombinedId", int.class);
        a = Chunk.getDeclaredMethod("a", BlockPosition, IBlockData);
        setType = ChunkSection.getDeclaredMethod("setType", int.class, int.class, int.class, IBlockData);
        getType = ChunkSection.getDeclaredMethod("getType", int.class, int.class, int.class);

        initLighting = Chunk.getDeclaredMethod("initLighting");
        chunkO = Chunk.getDeclaredMethod("o");

        Class<?> WorldProvider = getNMSClass("net.minecraft.server.%s.WorldProvider");
        if (WorldProvider != null) {
            getFlag = WorldProvider.getDeclaredMethod("m");
        }

        try {
            sections = Chunk.getDeclaredField("sections");
            sections.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        try {
            worldProviderField = World.getDeclaredField("worldProvider");
            worldProviderField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

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

    private static Class<?> getNMSClass(String nmsClass) {
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

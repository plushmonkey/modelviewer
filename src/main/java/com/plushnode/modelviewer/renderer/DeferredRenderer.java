package com.plushnode.modelviewer.renderer;

import com.plushnode.modelviewer.ModelViewerPlugin;
import com.plushnode.modelviewer.util.NativeMethods;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class DeferredRenderer implements Renderer {
    private int blocksPerTick;
    private Queue<RenderLocation> renderQueue = new LinkedBlockingQueue<>();
    private Queue<RenderCallback> callbacks = new LinkedBlockingQueue<>();

    public DeferredRenderer(ModelViewerPlugin plugin, int blocksPerTick) {
        this.blocksPerTick = blocksPerTick;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (renderQueue.isEmpty()) {
                    while (!callbacks.isEmpty()) {
                        RenderCallback callback = callbacks.poll();
                        callback.onFinish();
                    }
                }
                process();
            }
        }.runTaskTimer(plugin, 1, 1);
    }

    @Override
    public void renderBlock(Location location, Material type) {
        renderQueue.add(new RenderLocation(location, type));
    }

    @Override
    public void renderBlock(Location location, int typeId, byte typeData) {
        renderQueue.add(new RenderLocation(location, typeId, typeData));
    }

    public void addCallback(RenderCallback callback) {
        this.callbacks.add(callback);
    }

    private void process() {
        for (int i = 0; !renderQueue.isEmpty() && i < blocksPerTick; ++i) {
            RenderLocation current = renderQueue.poll();

            if (current == null)
                return;

            if (current.type != null) {
                //NativeMethods.setBlockFast(current.location.getBlock(), current.type.getId(), 0, false);
                current.location.getBlock().setType(current.type, false);
            } else {
                //NativeMethods.setBlockFast(current.location.getBlock(), current.typeId, current.typeData, false);
                current.location.getBlock().setTypeIdAndData(current.typeId, current.typeData, false);
            }
        }
    }

    private class RenderLocation {
        Location location;
        Material type = null;
        int typeId;
        byte typeData;

        RenderLocation(Location location, Material type) {
            this.location = location;
            this.type = type;
        }

        RenderLocation(Location location, int typeId, byte typeData) {
            this.location = location;
            this.typeId = typeId;
            this.typeData = typeData;
        }
    }
}

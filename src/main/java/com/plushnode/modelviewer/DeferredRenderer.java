package com.plushnode.modelviewer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;
import java.util.Queue;

public class DeferredRenderer implements Renderer {
    private int blocksPerTick;
    private Queue<RenderLocation> renderQueue = new LinkedList<>();
    private Queue<Callback> callbacks = new LinkedList<>();

    public DeferredRenderer(ModelViewerPlugin plugin, int blocksPerTick) {
        this.blocksPerTick = blocksPerTick;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (renderQueue.isEmpty()) {
                    while (!callbacks.isEmpty()) {
                        Callback callback = callbacks.poll();
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

    public void addCallback(Callback callback) {
        this.callbacks.add(callback);
    }

    private void process() {
        for (int i = 0; !renderQueue.isEmpty() && i < blocksPerTick; ++i) {
            RenderLocation current = renderQueue.poll();

            if (current == null)
                return;

            current.location.getBlock().setType(current.type);
        }
    }

    public interface Callback {
        void onFinish();
    }

    private class RenderLocation {
        Location location;
        Material type;
        RenderLocation(Location location, Material type) {
            this.location = location;
            this.type = type;
        }
    }
}

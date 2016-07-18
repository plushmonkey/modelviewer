package com.plushnode.modelviewer;

import com.plushnode.modelviewer.adapters.BukkitAdapter;
import com.plushnode.modelviewer.geometry.Face;
import com.plushnode.modelviewer.geometry.Model;
import com.plushnode.modelviewer.rasterizer.Rasterizer;
import com.plushnode.modelviewer.renderer.RenderCallback;
import com.plushnode.modelviewer.renderer.Renderer;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class ModelView {
    private Vector position;
    private Vector renderPosition;
    private double scale = 1.0;
    //private Material material = Material.STONE;
    private int typeId = 1;
    private int typeData = 0;
    private Set<Location> affectedBlocks = new HashSet<>();
    private Model model;
    private Renderer renderer;
    private Rotation rotation;
    private Rasterizer rasterizer;

    public ModelView(Model model, Renderer renderer, Rasterizer rasterizer) {
        this.model = model;
        this.renderer = renderer;
        this.rasterizer = rasterizer;
    }

    public Vector getPosition() {
        return this.position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void rotate(Rotation rot) {
        if (this.rotation == null) {
            this.rotation = rot;
        } else {
            this.rotation = rot.applyTo(this.rotation);
        }
    }

    public void setType(int type, int data) {
        this.typeId = type;
        this.typeData = data;
    }

    public double getScale() {
        return this.scale;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public Vector getSize() {
        List<Face> faces = model.getFaces();
        List<Vector> vertices = model.getVertices();

        Vector min = new Vector(0, 0, 0);
        Vector max = new Vector(0, 0, 0);

        for (int i = 0; i < faces.size(); ++i) {
            Face face = faces.get(i);

            for (int a = 0; a < 3; ++a) {
                Vector vertex = vertices.get(face.getIndex(a)).clone().multiply(scale);

                if (vertex.getX() < min.getX())
                    min.setX(vertex.getX());
                if (vertex.getY() < min.getY())
                    min.setY(vertex.getY());
                if (vertex.getZ() < min.getZ())
                    min.setZ(vertex.getZ());

                if (vertex.getX() > max.getX())
                    max.setX(vertex.getX());
                if (vertex.getY() > max.getY())
                    max.setY(vertex.getY());
                if (vertex.getZ() > max.getZ())
                    max.setZ(vertex.getZ());
            }
        }

        return new Vector(max.getX() - min.getX(), max.getY() - min.getY(), max.getZ() - min.getZ());
    }

    public void clear() {
        for (Location location : this.affectedBlocks) {
            renderer.renderBlock(location, Material.AIR);
        }
        this.affectedBlocks.clear();
    }

    private Vector3D rotateVector(Vector vector) {
        Vector3D result;

        if (this.rotation == null)
            result = BukkitAdapter.adapt(vector);
        else
            result = this.rotation.applyTo(BukkitAdapter.adapt(vector));

        return result;
    }

    public void render(World world, Plugin plugin, RenderCallback callback) {
        List<Face> faces = model.getFaces();
        List<Vector> vertices = model.getVertices();

        final long msBegin = System.currentTimeMillis();

        new BukkitRunnable() {
            public void run() {
                for (int i = 0; i < faces.size(); ++i) {
                    Face face = faces.get(i);

                    Vector vertexA = vertices.get(face.getIndex(0)).clone().multiply(scale);
                    Vector vertexB = vertices.get(face.getIndex(1)).clone().multiply(scale);
                    Vector vertexC = vertices.get(face.getIndex(2)).clone().multiply(scale);

                    Set<Vector3D> toRender = rasterizer.rasterize(rotateVector(vertexA), rotateVector(vertexB), rotateVector(vertexC));
                    for (Vector3D vector : toRender) {
                        Vector3D pos = BukkitAdapter.adapt(renderPosition);

                        Vector3D roundedVector = new Vector3D(
                                Math.round(pos.getX() + vector.getX()),
                                Math.round(pos.getY() + vector.getY()),
                                Math.round(pos.getZ() + vector.getZ()));

                        Location roundedLocation = BukkitAdapter.adapt(roundedVector, world);

                        if (affectedBlocks.contains(roundedLocation)) continue;

                        affectedBlocks.add(roundedLocation);

                        renderer.renderBlock(roundedLocation, typeId, (byte)typeData);
                    }
                }

                long msEnd = System.currentTimeMillis();
                System.out.println("Rasterization complete (" + (msEnd - msBegin) + ")");

                if (callback != null)
                    renderer.addCallback(callback);
            }
        }.runTaskAsynchronously(plugin);
    }

    public void render(World world, Plugin plugin) {
        render(world, plugin, null);
    }

    public void renderDirection(World world, Plugin plugin, Vector direction, RenderCallback callback) {
        Vector size = getSize();
        calculateRenderPosition(size, direction);

        render(world, plugin, callback);
    }

    public void renderDirection(World world, Plugin plugin, Vector direction) {
        Vector size = getSize();
        calculateRenderPosition(size, direction);

        render(world, plugin, null);
    }

    private void calculateRenderPosition(Vector size, Vector direction) {
        this.renderPosition = position.clone();
        this.renderPosition.add(direction.clone().multiply(size.clone().multiply(0.5)));
    }
}

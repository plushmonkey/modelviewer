package com.plushnode.modelviewer;

import com.plushnode.modelviewer.geometry.Face;
import com.plushnode.modelviewer.geometry.Model;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.*;

// This is really bad and broken, but it works for some models
public class ModelView {
    private static final double JUMP = 0.1;
    private Vector position;
    private Vector renderPosition;
    private double scale = 1.0;
    private Material material = Material.STONE;
    private Set<Location> affectedBlocks = new HashSet<>();
    private Model model;
    private Renderer renderer;

    public ModelView(Model model, Renderer renderer) {
        this.model = model;
        this.renderer = renderer;
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

    public double getScale() {
        return this.scale;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    private double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(val, max));
    }

    private double interpolate(double min, double max, double t) {
        return min + (max - min) * clamp(t, 0, 1);
    }

    private void processScanLine(World world, double y, Vector pa, Vector pb, Vector pc, Vector pd) {
        double gradient1 = pa.getY() != pb.getY() ? (y - pa.getY()) / (pb.getY() - pa.getY()) : 1;
        double gradient2 = pc.getY() != pd.getY() ? (y - pc.getY()) / (pd.getY() - pc.getY()) : 1;

        double sx = interpolate(pa.getX(), pb.getX(), gradient1);
        double ex = interpolate(pc.getX(), pd.getX(), gradient2);

        double z1 = interpolate(pa.getZ(), pb.getZ(), gradient1);
        double z2 = interpolate(pc.getZ(), pd.getZ(), gradient2);

        Location location = this.renderPosition.toLocation(world);

        location.setY(this.renderPosition.getY() + y);

        for (double x = sx; x < ex; x += JUMP) {
            double gradient = (x - sx) / (ex - sx);
            double z = interpolate(z1, z2, gradient);

            location.setX(this.renderPosition.getX() + x);
            location.setZ(this.renderPosition.getZ() + z);

            if (affectedBlocks.contains(location.getBlock().getLocation())) continue;

            affectedBlocks.add(location.getBlock().getLocation());

            renderer.renderBlock(location.getBlock().getLocation(), material);
        }
    }

    double cross2d(double x0, double y0, double x1, double y1) {
        return x0 * y1 - x1 * y0;
    }

    double lineSide2D(Vector p, Vector from, Vector to) {
        return cross2d(p.getX() - from.getX(), p.getY() - from.getY(), to.getX() - from.getX(), to.getY() - from.getY());
    }

    public void drawTriangle(World world, Vector vertexA, Vector vertexB, Vector vertexC) {
        Vector[] points = new Vector[] { vertexA.clone(), vertexB.clone(), vertexC.clone() };

        Arrays.sort(points, (Vector v1, Vector v2) -> {
            double r = v1.getY() - v2.getY();
            if (r < 0) return -1;
            if (r == 0) return 0;
            return 1;
        });

        Vector p1 = points[0];
        Vector p2 = points[1];
        Vector p3 = points[2];

        if (lineSide2D(p2, p1, p3) > 0) {
            for (double y = p1.getY(); y <= p3.getY(); y += JUMP) {
                if (y < p2.getY()) {
                    processScanLine(world, y, p1, p3, p1, p2);
                } else {
                    processScanLine(world, y, p1, p3, p2, p3);
                }
            }
        } else {
            for (double y = p1.getY(); y <= p3.getY(); y += JUMP) {
                if (y < p2.getY()) {
                    processScanLine(world, y, p1, p2, p1, p3);
                } else {
                    processScanLine(world, y, p2, p3, p1, p3);
                }
            }
        }
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

                if (vertex.getX() < max.getX())
                    max.setX(vertex.getX());
                if (vertex.getY() < max.getY())
                    max.setY(vertex.getY());
                if (vertex.getZ() < max.getZ())
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

    public void render(World world) {
        List<Face> faces = model.getFaces();
        List<Vector> vertices = model.getVertices();

        for (int i = 0; i < faces.size(); ++i) {
            Face face = faces.get(i);

            Vector vertexA = vertices.get(face.getIndex(0)).clone().multiply(scale);
            Vector vertexB = vertices.get(face.getIndex(1)).clone().multiply(scale);
            Vector vertexC = vertices.get(face.getIndex(2)).clone().multiply(scale);

            drawTriangle(world, vertexA, vertexB, vertexC);
        }
    }

    public void renderDirection(World world, Vector direction) {
        Vector size = getSize();
        calculateRenderPosition(size, direction);

        render(world);
    }

    private void calculateRenderPosition(Vector size, Vector direction) {
        this.renderPosition = position.clone();
        this.renderPosition.add(direction.clone().multiply(size.clone().multiply(0.5)));
    }
}

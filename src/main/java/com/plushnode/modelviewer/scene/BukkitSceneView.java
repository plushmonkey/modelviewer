package com.plushnode.modelviewer.scene;

import com.plushnode.modelviewer.ModelViewerPlugin;
import com.plushnode.modelviewer.adapters.BukkitAdapter;
import com.plushnode.modelviewer.fill.PolygonFiller;
import com.plushnode.modelviewer.geometry.Face;
import com.plushnode.modelviewer.geometry.Model;
import com.plushnode.modelviewer.math.VectorUtils;
import com.plushnode.modelviewer.renderer.RenderCallback;
import com.plushnode.modelviewer.renderer.Renderer;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BukkitSceneView {
    private ModelViewerPlugin plugin;
    private SceneNode scene;
    private Renderer renderer;
    private PolygonFiller filler;
    private Set<Location> affectedBlocks = new HashSet<>();
    private int typeId = 1;
    private int typeData = 0;

    public BukkitSceneView(ModelViewerPlugin plugin, SceneNode scene, Renderer renderer, PolygonFiller filler) {
        this.plugin = plugin;
        this.scene = scene;
        this.renderer = renderer;
        this.filler = filler;
    }

    public void setType(int typeId, int typeData) {
        this.typeId = typeId;
        this.typeData = typeData;
    }

    public SceneNode getScene() {
        return this.scene;
    }

    public Renderer getRenderer() {
        return this.renderer;
    }

    public void clear() {
        for (Location location : this.affectedBlocks) {
            renderer.renderBlock(location, Material.AIR);
        }
        this.affectedBlocks.clear();
    }

    public void render(World world) {
        render(world, null);
    }

    public void renderNode(World world, SceneNode node, RealMatrix transform) {
        RealMatrix resultTransform = transform.multiply(node.getTransform().getMatrix());

        Model model = node.getModel();

        if (model != null) {
            List<Face> faces = model.getFaces();
            List<Vector3D> vertices = model.getVertices();

            List<Vector3D> transformedVertices = new ArrayList<>(vertices.size());

            for (int i = 0; i < vertices.size(); ++i) {
                Vector3D vertex = vertices.get(i);
                vertex = VectorUtils.multiply(resultTransform, vertex);
                transformedVertices.add(vertex);
            }

            for (int i = 0; i < faces.size(); ++i) {
                Face face = faces.get(i);

                List<Vector3D> faceVertices = new ArrayList<>(face.getSize());

                for (int j = 0; j < face.getSize(); ++j) {
                    Vector3D vertex = transformedVertices.get(face.getIndex(j));
                    faceVertices.add(vertex);
                }

                Set<Vector3D> toRender = filler.fill(faceVertices);

                for (Vector3D vector : toRender) {
                    Vector3D roundedVector = new Vector3D(
                            Math.round(vector.getX()),
                            Math.round(vector.getY()),
                            Math.round(vector.getZ()));

                    Location roundedLocation = BukkitAdapter.adapt(roundedVector, world);

                    if (affectedBlocks.contains(roundedLocation)) continue;

                    affectedBlocks.add(roundedLocation);

                    renderer.renderBlock(roundedLocation, typeId, (byte) typeData);
                }
            }
        }

        for (SceneNode subNode : node.getChildren())
            renderNode(world, subNode, resultTransform);
    }

    public void render(World world, RenderCallback callback) {
        new BukkitRunnable() {
            public void run() {
                long msBegin = System.currentTimeMillis();

                renderNode(world, scene, MatrixUtils.createRealIdentityMatrix(4));

                long msEnd = System.currentTimeMillis();

                System.out.println("Filling calculated (" + (msEnd - msBegin) + "ms)");

                if (callback != null)
                    renderer.addCallback(callback);
            }
        }.runTaskAsynchronously(plugin);
    }
}

package com.plushnode.modelviewer.scene;

import com.plushnode.modelviewer.ModelViewerPlugin;
import com.plushnode.modelviewer.adapters.BukkitAdapter;
import com.plushnode.modelviewer.fill.TriangleFiller;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BukkitSceneView {
    private ModelViewerPlugin plugin;
    private SceneNode scene;
    private Renderer renderer;
    private TriangleFiller filler;
    private Set<Location> affectedBlocks = new HashSet<>();
    private int typeId = 1;
    private int typeData = 0;

    public BukkitSceneView(ModelViewerPlugin plugin, SceneNode scene, Renderer renderer, TriangleFiller filler) {
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

            for (int i = 0; i < faces.size(); ++i) {
                Face face = faces.get(i);

                Vector3D vertexA = vertices.get(face.getIndex(0));
                Vector3D vertexB = vertices.get(face.getIndex(1));
                Vector3D vertexC = vertices.get(face.getIndex(2));

                vertexA = VectorUtils.multiply(resultTransform, vertexA);
                vertexB = VectorUtils.multiply(resultTransform, vertexB);
                vertexC = VectorUtils.multiply(resultTransform, vertexC);

                Set<Vector3D> toRender = filler.fill(vertexA, vertexB, vertexC);

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

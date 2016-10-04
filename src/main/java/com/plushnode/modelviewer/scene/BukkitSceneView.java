package com.plushnode.modelviewer.scene;

import com.plushnode.modelviewer.ModelViewerPlugin;
import com.plushnode.modelviewer.adapters.BukkitAdapter;
import com.plushnode.modelviewer.fill.PolygonFiller;
import com.plushnode.modelviewer.geometry.Face;
import com.plushnode.modelviewer.geometry.Model;
import com.plushnode.modelviewer.geometry.Vertex;
import com.plushnode.modelviewer.material.Texture;
import com.plushnode.modelviewer.math.VectorUtils;
import com.plushnode.modelviewer.renderer.RenderCallback;
import com.plushnode.modelviewer.renderer.Renderer;
import com.plushnode.modelviewer.util.ColorMatcher;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
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

    public BukkitSceneView(ModelViewerPlugin plugin, SceneNode scene, Renderer renderer, PolygonFiller filler) {
        this.plugin = plugin;
        this.scene = scene;
        this.renderer = renderer;
        this.filler = filler;
    }

    public void setType(int typeId, int typeData) {
        scene.setType(typeId, (byte)typeData);
    }

    public SceneNode getScene() {
        return this.scene;
    }

    public Renderer getRenderer() {
        return this.renderer;
    }

    public int getBlockCount() {
        return this.affectedBlocks.size();
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

        System.out.println("Rendering " + node.getName());

        if (model != null) {
            List<Face> faces = model.getFaces();
            List<Vertex> vertices = model.getVertices();

            List<Vector3D> transformedVertices = new ArrayList<>(vertices.size());

            for (int i = 0; i < vertices.size(); ++i) {
                Vector3D position = vertices.get(i).getPosition();
                position = VectorUtils.multiply(resultTransform, position);
                transformedVertices.add(position);
            }

            for (int i = 0; i < faces.size(); ++i) {
                Face face = faces.get(i);

                ColorMatcher.Type type = ColorMatcher.getInstance().getDefaultType();

                //int materialIndex = face.getMaterialIndex();
                int materialIndex = 0;
                int blockId = 1;
                byte blockData = 0;

                if (materialIndex >= 0) {
                    com.plushnode.modelviewer.material.Material material = node.getMaterial(materialIndex);
                    /*FBXPropertyStore store = FBXPropertiesLoader.loadProperties(node.getMaterial(materialIndex).getNode("Properties70"));

                    FBXProperty diffuseColor = store.getProperty("DiffuseColor");*/

                    type = ColorMatcher.getInstance().getTypeFromColor(material.getDiffuseColor());
                    blockId = type.id;
                    blockData = type.data;
                }

                List<Vector3D> faceVertices = new ArrayList<>(face.getSize());

                for (int j = 0; j < face.getSize(); ++j) {
                    Vector3D vertex = transformedVertices.get(face.getIndex(j));
                    faceVertices.add(vertex);
                }

                List<Integer> uvIndices = face.getUvIndices();
                List<Vector2D> faceUVs = new ArrayList<>();

                if (!model.getUVs().isEmpty() && uvIndices != null && !uvIndices.isEmpty()) {
                    for (int j = 0; j < face.getSize(); ++j) {
                        Vector2D uv = model.getUVs().get(uvIndices.get(j));
                        faceUVs.add(uv);
                    }
                }

                Set<Vector3D> toRender = filler.fill(faceVertices);
                for (Vector3D vector : toRender) {
                    Vector3D roundedVector = new Vector3D(
                            Math.floor(vector.getX()),
                            Math.floor(vector.getY()),
                            Math.floor(vector.getZ()));

                    Location roundedLocation = BukkitAdapter.adapt(roundedVector, world);
                    if (affectedBlocks.contains(roundedLocation)) continue;
                    affectedBlocks.add(roundedLocation);

                    if (!faceUVs.isEmpty()) {
                        List<Double> weights = computeBarycentricWeights(vector, faceVertices);
                        Vector2D result = new Vector2D(0, 0);

                        for (int j = 0; j < faceUVs.size(); ++j) {
                            Vector2D weightedUV = faceUVs.get(j);
                            weightedUV = weightedUV.scalarMultiply(weights.get(j));
                            result = result.add(weightedUV);
                        }

                        com.plushnode.modelviewer.material.Material material = node.getMaterial(face.getMaterialIndex());
                        Texture texture = material.getTexture();

                        //BufferedImage texture = TextureManager.getInstance().getTexture();
                        if (texture != null) {
                            Vector3D sampledColor = texture.sample(result.getX(), result.getY());

                            type = ColorMatcher.getInstance().getTypeFromColor(sampledColor);

                            blockId = type.id;
                            blockData = type.data;
                        }
                    }

                    renderer.renderBlock(roundedLocation, blockId, blockData);
                }
            }
        }

        for (SceneNode subNode : node.getChildren())
            renderNode(world, subNode, resultTransform);
    }

    private List<Double> computeBarycentricWeights(Vector3D p, List<Vector3D> vertices) {
        List<Double> weights = new ArrayList<>();
        double weightSum = 0;
        int n = vertices.size();

        for (int i = 0; i < n; ++i) {
            Vector3D curr = vertices.get(i);
            Vector3D prev = vertices.get((i + n - 1) % n);
            Vector3D next = vertices.get((i + 1) % n);

            double norm = p.subtract(curr).getNorm();

            double weight;
            double epsilon = 0.05;

            double c1 = prev.subtract(curr).crossProduct(p.subtract(curr)).getNorm();
            double c2 = next.subtract(curr).crossProduct(p.subtract(curr)).getNorm();

            if (next.subtract(curr).crossProduct(p.subtract(curr)).getNorm() <= epsilon * next.subtract(curr).getNorm() || c1 == 0.0 || c2 == 0.0) {
                weight = p.distance(prev) / next.distance(prev);
            } else {
                double cot1 = cotangent(p, curr, prev);
                double cot2 = cotangent(p, curr, next);

                weight = (cot1 + cot2) / (norm * norm);
            }

            weights.add(weight);
            weightSum += weight;
        }

        for (int i = 0; i < weights.size(); ++i)
            weights.set(i, weights.get(i) / weightSum);

        return weights;
    }

    private double cotangent(Vector3D a, Vector3D b, Vector3D c) {
        Vector3D ba = a.subtract(b);
        Vector3D bc = c.subtract(b);

        return ba.dotProduct(bc) / bc.crossProduct(ba).getNorm();
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

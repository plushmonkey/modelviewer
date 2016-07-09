package com.plushnode.modelviewer;

import com.darkblade12.particleeffect.ParticleEffect;
import com.plushnode.modelviewer.fbx.FBXDocument;
import com.plushnode.modelviewer.fbx.FBXNode;
import com.plushnode.modelviewer.fbx.io.FBXBinaryReader;
import com.plushnode.modelviewer.util.NativeMethods;
import com.plushnode.modelviewer.util.TempBlock;
import com.plushnode.modelviewer.util.TempBlockManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.logging.Logger;

public class ModelViewerPlugin extends JavaPlugin {
    private static Logger logger = Logger.getLogger("ModelViewer");
    private List<Vector> vertices = new ArrayList<>();
    private List<Integer> indices = new ArrayList<>();
    private List<Integer> edges = new ArrayList<>();
    private TempBlockManager tempBlockManager = new TempBlockManager();
    private Set<BlockVector> affectedBlocks = new HashSet<>();

    private double scale = 5.0;
    private int direction = 0;

    @SuppressWarnings("unchecked")
    @Override
    public void onEnable() {
        NativeMethods.getInstance();
        logger.info("Loading fbx file");

        FBXDocument document = null;
        getDataFolder().mkdirs();

        File testFile = new File(getDataFolder(), "test.fbx");
        try {
            FBXBinaryReader reader = new FBXBinaryReader(new RandomAccessFile(testFile.getAbsolutePath(), "r"));
            document = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (document != null) {
            logger.info("FBX file loaded");
            FBXNode geometryNode = document.getNode("Objects").getNode("Geometry");
            FBXNode vertexNode = geometryNode.getNode("Vertices");
            FBXNode edgeNode = geometryNode.getNode("Edges");
            FBXNode indicesNode = geometryNode.getNode("PolygonVertexIndex");

            ArrayList<Double> verticesList = (ArrayList<Double>)vertexNode.getProperties().get(0);
            for (int i = 0; i < verticesList.size(); i += 3) {
                vertices.add(new Vector(verticesList.get(i), verticesList.get(i + 1), verticesList.get(i + 2)));
            }

            edges = (ArrayList<Integer>)edgeNode.getProperties().get(0);
            indices = (ArrayList<Integer>)indicesNode.getProperties().get(0);

            final World world = this.getServer().getWorld("world");
            Location location = world.getSpawnLocation().clone();
            location.setX(14530);
            location.setY(160);
            location.setZ(60);


            final double maxScale = 20;

            final double animationLength = 1.5;
            double scalePerSecond = maxScale / animationLength;
            double scalePerTick = (scalePerSecond / 1000.0) * 20;
            final int delay = 4;
            final double speed = scalePerTick * delay;
            final float spread = 0.1f;

            new BukkitRunnable() {
                public void run() {
                    if (direction == 0)
                        scale += speed;
                    else
                        scale -= speed;

                    if (scale >= maxScale) {
                        scale = 1;
                        //direction = 1;
                    }
                    if (scale <= 1)
                        direction = 0;

                    //scale = 25.0;

                    for (int i = 0; i < edges.size(); ++i) {
                        int index = edges.get(i);

                        int beginVertexIndex = indices.get(index);
                        int endVertexIndex = beginVertexIndex;
                        if (beginVertexIndex < 0) {
                            beginVertexIndex = ~beginVertexIndex;

                            int backwardsIndex = index;

                            while (backwardsIndex > 0) {
                                if (indices.get(--backwardsIndex) < 0) {
                                    endVertexIndex = indices.get(backwardsIndex + 1);
                                    break;
                                }
                            }

                            if (backwardsIndex <= 0)
                                endVertexIndex = indices.get(0);
                        } else {
                            endVertexIndex = indices.get(index + 1);
                            if (endVertexIndex < 0) {
                                endVertexIndex = ~endVertexIndex;
                            }
                        }

                        Vector begin = vertices.get(beginVertexIndex).clone().multiply(scale).add(location.toVector());
                        Vector end = vertices.get(endVertexIndex).clone().multiply(scale).add(location.toVector());

                        Vector toEnd = end.clone().subtract(begin);
                        Vector direction = toEnd.clone().normalize();

                        Location current = begin.toLocation(world);
                        for (int a = 0; a < (int)Math.ceil(toEnd.length()); ++a) {
                            /*if (current.getBlock().getType() == Material.AIR) {
                                TempBlock tempBlock = tempBlockManager.getTempBlock(current);
                                if (tempBlock == null) {
                                    tempBlockManager.add(new TempBlock(current.getBlock().getState(), Material.STONE));
                                }
                            }

                            affectedBlocks.add(current.toVector().toBlockVector());*/
                            ParticleEffect.SMOKE_LARGE.display(spread, spread, spread, 0, 1, current, 257);
                            current.add(direction);
                        }
                    }


                    Iterator<Map.Entry<Location, TempBlock>> iterator = tempBlockManager.temporaryBlocks.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<Location, TempBlock> entry = iterator.next();
                        TempBlock tempBlock = entry.getValue();

                        BlockVector bv = tempBlock.getPreviousState().getLocation().toVector().toBlockVector();
                        if (!affectedBlocks.contains(bv)) {
                            tempBlock.reset();
                            iterator.remove();
                        }
                    }

                    affectedBlocks.clear();
                }
            }.runTaskTimer(this, delay, delay);
        }
    }

    @Override
    public void onDisable() {
        tempBlockManager.resetAll();
    }
}
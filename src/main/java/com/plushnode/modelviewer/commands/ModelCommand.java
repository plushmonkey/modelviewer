package com.plushnode.modelviewer.commands;

import com.plushnode.modelviewer.*;
import com.plushnode.modelviewer.adapters.BukkitAdapter;
import com.plushnode.modelviewer.fbx.FBXDocument;
import com.plushnode.modelviewer.fbx.FBXSceneCreator;
import com.plushnode.modelviewer.fill.BarycentricConvexPolygonFiller;
import com.plushnode.modelviewer.renderer.DeferredRenderer;
import com.plushnode.modelviewer.renderer.Renderer;
import com.plushnode.modelviewer.scene.*;
import com.plushnode.modelviewer.color.ClayColorMatcher;
import com.plushnode.modelviewer.color.StaticMaterialMatcher;
import com.plushnode.modelviewer.color.SolidColorMatcher;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ModelCommand implements CommandExecutor {
    private ModelViewerPlugin plugin;
    private Map<String, BukkitSceneView> history = new ConcurrentHashMap<>();

    public ModelCommand(ModelViewerPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 0) {
            return false;
        }

        if (args[0].equalsIgnoreCase("d") || args[0].equalsIgnoreCase("display")) {
            handleDisplay(commandSender, args);
        } else if (args[0].equalsIgnoreCase("reset")) {
            handleReset(commandSender, args);
        } else if (args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("scale")) {
            handleScale(commandSender, args);
        } else if (args[0].equalsIgnoreCase("r") || args[0].equalsIgnoreCase("rotate")) {
            handleRotate(commandSender, args);
        } else if (args[0].equalsIgnoreCase("type")) {
            handleType(commandSender, args);
        }

        return true;
    }

    private void handleType(CommandSender commandSender, String[] args) {
        if (args.length < 2) {
            commandSender.sendMessage("/model type [materialName]");
            return;
        }

        BukkitSceneView view = history.get(commandSender.getName());
        if (view == null) {
            commandSender.sendMessage("No models to change.");
            return;
        }

        if (!(commandSender instanceof Player))
            return;

        Material type;

        try {
            type = Material.valueOf(args[1]);
        } catch (IllegalArgumentException e) {
            commandSender.sendMessage("Failed to parse type.");
            return;
        }

        view.setColorMatcher(new StaticMaterialMatcher(type));
        view.clear();
        Player player = (Player)commandSender;
        final String setString = "Model type set to " + type + ".";
        view.render(player.getWorld(), () -> {
            commandSender.sendMessage(setString);
        });
    }
    private void handleRotate(CommandSender commandSender, String[] args) {
        if (args.length != 3) {
            commandSender.sendMessage("/model rotate [axis-XYZ] [degrees] (bad arg count)");
            return;
        }

        BukkitSceneView view = history.get(commandSender.getName());
        if (view == null) {
            commandSender.sendMessage("No models to rotate");
            return;
        }

        if (!(commandSender instanceof Player))
            return;

        String axisStr = args[1];
        Vector3D axis = null;

        if (axisStr.equalsIgnoreCase("x")) {
            axis = Vector3D.PLUS_I;
        } else if (axisStr.equalsIgnoreCase("y")) {
            axis = Vector3D.PLUS_J;
        } else if (axisStr.equalsIgnoreCase("z")) {
            axis = Vector3D.PLUS_K;
        }

        if (axis == null) {
            commandSender.sendMessage("/model rotate [axis-XYZ] [degrees] (axis null)");
            return;
        }

        int degrees;

        try {
            degrees = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            commandSender.sendMessage("/model rotate [axis-XYZ] [degrees] (bad number format)");
            return;
        }

        double rot = Math.toRadians(degrees);

        view.getScene().getTransform().rotate(new Rotation(axis, rot));

        view.clear();
        Player player = (Player)commandSender;
        view.render(player.getWorld(), () -> {
            commandSender.sendMessage("Model rotated. (" + view.getBlockCount() + " blocks)");
        });
    }

    private void handleScale(CommandSender commandSender, String[] args) {
        BukkitSceneView view = history.get(commandSender.getName());
        if (view == null) {
            commandSender.sendMessage("No models to scale");
            return;
        }

        if (!(commandSender instanceof Player))
            return;

        try {
            double scale = Double.parseDouble(args[1]);

            view.getScene().getTransform().setScale(scale);
        } catch (NumberFormatException e) {
            commandSender.sendMessage("Error parsing scale.");
            return;
        }

        view.clear();
        Player player = (Player)commandSender;

        final long begin = System.currentTimeMillis();

        view.render(player.getWorld(), () -> {
            String message = "Model scale set to " + view.getScene().getTransform().getScale() + " in " + (System.currentTimeMillis() - begin) + "ms. (" + view.getBlockCount() + " blocks)";
            commandSender.sendMessage(message);
            System.out.println(message);
        });
    }

    private void handleReset(CommandSender commandSender, String[] args) {
        BukkitSceneView view = history.get(commandSender.getName());
        if (view == null) {
            commandSender.sendMessage("Nothing to reset.");
            return;
        }

        view.clear();

        view.getRenderer().addCallback(() -> {
            commandSender.sendMessage("Model reset.");
        });
    }

    private double parseDouble(String str, double def) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    private void handleDisplay(CommandSender commandSender, String[] args) {
        if (!(commandSender instanceof Player))
            return;
        if (args.length == 0) {
            commandSender.sendMessage("Usage: /model d [name] <size>");
            return;
        }

        final double scale = (args.length > 2) ? parseDouble(args[2], 1.0) : 1.0;
        final Player player = (Player)commandSender;
        final Vector3D position = BukkitAdapter.adapt(player.getLocation().toVector());
        final Vector3D direction = BukkitAdapter.adapt(player.getLocation().getDirection());
        final World world = player.getWorld();

        player.sendMessage("Loading and rendering scene...");

        new BukkitRunnable() {
            @Override
            public void run() {
                FBXDocument document = plugin.loadFBX(args[1]);

                if (document == null) {
                    player.sendMessage("Could not find scene file.");
                    return;
                }

                SceneCreator sceneCreator = new FBXSceneCreator(document);

                Scene scene = sceneCreator.createScene();

                translateScene(scene.getRootNode(), position, direction);
                scene.getRootNode().getTransform().setScale(scale);

                final long begin = System.currentTimeMillis();

                Renderer renderer = new DeferredRenderer(plugin, 1000);
                BukkitSceneView view = new BukkitSceneView(plugin, scene.getRootNode(), renderer, new BarycentricConvexPolygonFiller());

                if (args.length > 3) {
                    if (args[3].startsWith("-c")) {
                        view.setColorMatcher(new ClayColorMatcher());
                    } else if (args[3].startsWith("-f")) {
                        view.setColorMatcher(new SolidColorMatcher());
                    } else if (args[3].startsWith("-s")) {
                        Material type = Material.STONE;

                        if (args.length > 4) {
                            try {
                                type = Material.valueOf(args[4]);
                            } catch (IllegalArgumentException e) {
                                //
                            }
                        }
                        view.setColorMatcher(new StaticMaterialMatcher(type));
                    }
                }

                view.render(world, () -> {
                    player.sendMessage("Rendered in " + (System.currentTimeMillis() - begin) + "ms. (" + view.getBlockCount() + " blocks)");
                });

                history.put(player.getName(), view);
            }
        }.runTaskAsynchronously(this.plugin);
    }

    private void translateScene(SceneNode scene, Vector3D position, Vector3D direction) {
        Vector3D translation = position.add(direction.scalarMultiply(5)).add(scene.getSize());

        scene.getTransform().setTranslation(translation);
    }
}

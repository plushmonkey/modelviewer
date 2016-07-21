package com.plushnode.modelviewer.commands;

import com.plushnode.modelviewer.*;
import com.plushnode.modelviewer.adapters.BukkitAdapter;
import com.plushnode.modelviewer.fbx.FBXDocument;
import com.plushnode.modelviewer.fbx.node.FBXNode;
import com.plushnode.modelviewer.fbx.property.FBXPropertiesLoader;
import com.plushnode.modelviewer.fbx.property.FBXProperty;
import com.plushnode.modelviewer.fbx.property.FBXPropertyStore;
import com.plushnode.modelviewer.geometry.Model;
import com.plushnode.modelviewer.fill.LineTriangleFiller;
import com.plushnode.modelviewer.renderer.DeferredRenderer;
import com.plushnode.modelviewer.renderer.Renderer;
import com.plushnode.modelviewer.scene.*;
import com.plushnode.modelviewer.util.ModelLoader;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.RealVector;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelCommand implements CommandExecutor {
    private Map<String, Model> models = new HashMap<>();
    private ModelViewerPlugin plugin;
    private Map<String, BukkitSceneView> history = new HashMap<>();

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
            commandSender.sendMessage("/model type [id] <data>");
            return;
        }

        BukkitSceneView view = history.get(commandSender.getName());
        if (view == null) {
            commandSender.sendMessage("No models to change.");
            return;
        }

        if (!(commandSender instanceof Player))
            return;

        int type;

        try {
            type = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            commandSender.sendMessage("Failed to parse type id. It must be an integer.");
            return;
        }

        int data = 0;
        if (args.length > 2) {
            try {
                data = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
            }
        }

        view.setType(type, data);
        view.clear();
        Player player = (Player)commandSender;
        final String setString = "Model type set to " + type + ":" + data + ".";
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
            commandSender.sendMessage("Model rotated.");
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
            commandSender.sendMessage("Model scale set to " + view.getScene().getTransform().getScale() + " in " + (System.currentTimeMillis() - begin) + "ms.");
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

    private void handleDisplay(CommandSender commandSender, String[] args) {
        if (!(commandSender instanceof Player))
            return;
        if (args.length == 0) {
            commandSender.sendMessage("Usage: /model d [name] <size>");
            return;
        }

        double scale = 1.0;

        if (args.length > 2) {
            try {
                scale = Double.parseDouble(args[2]);
            } catch (NumberFormatException e) {
                commandSender.sendMessage("Error parsing size.");
                return;
            }
        }

        SceneNode scene = new SceneNode(null, new Transform());

        FBXDocument document = this.plugin.loadFBX(args[1]);
        List<Model> modelList = ModelLoader.load(document);

        for (int i = 0; i < modelList.size(); ++i) {
            Model model = modelList.get(i);
            SceneNode node = new SceneNode(model, new Transform());

            transformNode(node);

            scene.addChild(node);
        }

        Player player = (Player)commandSender;

        Renderer renderer = new DeferredRenderer(plugin, 100);

        translateScene(scene, BukkitAdapter.adapt(player.getLocation().toVector()), BukkitAdapter.adapt(player.getLocation().getDirection()));
        scene.getTransform().setScale(scale);

        final long begin = System.currentTimeMillis();

        BukkitSceneView view = new BukkitSceneView(plugin, scene, renderer, new LineTriangleFiller());
        view.render(player.getWorld(), () -> {
            player.sendMessage("Rendered in " + (System.currentTimeMillis() - begin) + "ms.");
        });

        history.put(player.getName(), view);
    }

    private void transformNode(SceneNode sceneNode) {
        Rotation xRot = new Rotation(Vector3D.PLUS_I, 0);
        Rotation yRot = new Rotation(Vector3D.PLUS_J, 0);
        Rotation zRot = new Rotation(Vector3D.PLUS_K, 0);

        Vector3D modelTranslation = Vector3D.ZERO;
        Vector3D modelScaling = new Vector3D(1, 1, 1);

        for (FBXNode node : sceneNode.getModel().getNode().getNodes()) {
            if (node.getName().equalsIgnoreCase("Properties70")) {
                FBXPropertyStore properties = FBXPropertiesLoader.loadProperties(node);
                FBXProperty translation = properties.getProperty("Lcl Translation");
                FBXProperty scaling = properties.getProperty("Lcl Scaling");
                FBXProperty rotation = properties.getProperty("Lcl Rotation");

                if (scaling != null) {
                    modelScaling = scaling.getValue().asVector();
                }

                if (translation != null) {
                    modelTranslation = translation.getValue().asVector();
                }

                if (rotation != null) {
                    Vector3D value = rotation.getValue().asVector();

                    /*xRot = new Rotation(Vector3D.PLUS_I, value.getX());
                    yRot = new Rotation(Vector3D.PLUS_J, value.getY());
                    zRot = new Rotation(Vector3D.PLUS_K, value.getZ());*/
                }
            }
        }

        modelTranslation = modelTranslation.scalarMultiply(1/100.0);

        sceneNode.getTransform().setTranslation(modelTranslation);

        sceneNode.getTransform().rotate(xRot);
        sceneNode.getTransform().rotate(yRot);
        sceneNode.getTransform().rotate(zRot);
    }

    private void translateScene(SceneNode scene, Vector3D position, Vector3D direction) {
        Vector3D translation = position.add(direction.scalarMultiply(5)).add(scene.getSize());

        scene.getTransform().setTranslation(translation);
    }
}

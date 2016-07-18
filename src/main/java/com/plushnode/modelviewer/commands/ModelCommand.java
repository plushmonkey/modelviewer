package com.plushnode.modelviewer.commands;

import com.plushnode.modelviewer.*;
import com.plushnode.modelviewer.fbx.FBXDocument;
import com.plushnode.modelviewer.geometry.Model;
import com.plushnode.modelviewer.rasterizer.LineRasterizer;
import com.plushnode.modelviewer.renderer.DeferredRenderer;
import com.plushnode.modelviewer.renderer.Renderer;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class ModelCommand implements CommandExecutor {
    private Map<String, Model> models = new HashMap<>();
    private ModelViewerPlugin plugin;
    private Map<String, ModelView> history = new HashMap<>();

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

        ModelView view = history.get(commandSender.getName());
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
        view.render(player.getWorld(), plugin, () -> {
            commandSender.sendMessage(setString);
        });
    }
    private void handleRotate(CommandSender commandSender, String[] args) {
        if (args.length != 3) {
            commandSender.sendMessage("/model rotate [axis-XYZ] [degrees] (bad arg count)");
            return;
        }

        ModelView view = history.get(commandSender.getName());
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
        view.rotate(new Rotation(axis, rot));

        view.clear();
        Player player = (Player)commandSender;
        view.render(player.getWorld(), plugin, () -> {
            commandSender.sendMessage("Model rotated.");
        });
    }

    private void handleScale(CommandSender commandSender, String[] args) {
        ModelView view = history.get(commandSender.getName());
        if (view == null) {
            commandSender.sendMessage("No models to scale");
            return;
        }

        if (!(commandSender instanceof Player))
            return;

        try {
            double scale = Double.parseDouble(args[1]);

            view.setScale(scale);
        } catch (NumberFormatException e) {
            commandSender.sendMessage("Error parsing scale.");
            return;
        }

        view.clear();
        Player player = (Player)commandSender;
        view.render(player.getWorld(), plugin, () -> {
            commandSender.sendMessage("Model scale set to " + view.getScale() + ".");
        });
    }

    private void handleReset(CommandSender commandSender, String[] args) {
        ModelView view = history.get(commandSender.getName());
        if (view == null) {
            commandSender.sendMessage("Nothing to reset.");
            return;
        }

        view.clear();

        Renderer renderer = view.getRenderer();

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

        Model model = models.get(args[1]);

        if (model == null) {
            FBXDocument document = this.plugin.loadFBX(args[1]);
            model = ModelLoader.load(document);

            if (model != null) {
                this.models.put(args[1], model);
            } else {
                commandSender.sendMessage("Error loading model.");
                return;
            }
        }

        Player player = (Player)commandSender;
        Location location = player.getLocation().clone();
        Vector direction = location.getDirection().clone();

        location.add(direction.clone().multiply(5));

        double scale = 1.0;

        if (args.length > 2) {
            try {
                scale = Double.parseDouble(args[2]);
            } catch (NumberFormatException e) {
                commandSender.sendMessage("Error parsing size.");
                return;
            }
        }

        Renderer renderer = new DeferredRenderer(plugin, 100);
        ModelView view = new ModelView(model, renderer, new LineRasterizer());

        view.setScale(scale);
        view.setPosition(location.toVector());

        player.sendMessage("Rendering...");

        view.renderDirection(player.getWorld(), plugin, direction, () -> {
            player.sendMessage("Rendered.");
        });

        history.put(player.getName(), view);
    }
}

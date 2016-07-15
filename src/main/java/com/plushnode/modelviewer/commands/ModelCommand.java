package com.plushnode.modelviewer.commands;

import com.plushnode.modelviewer.*;
import com.plushnode.modelviewer.fbx.FBXDocument;
import com.plushnode.modelviewer.geometry.Model;
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
        } else if (args[0].equalsIgnoreCase("r") || args[0].equalsIgnoreCase("reset")) {
            handleReset(commandSender, args);
        } else if (args[0].equalsIgnoreCase("scale")) {
            handleScale(commandSender, args);
        }

        return true;
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
        view.render(player.getWorld());

        Renderer renderer = view.getRenderer();
        if (renderer instanceof DeferredRenderer) {
            ((DeferredRenderer)renderer).addCallback(() -> {
                commandSender.sendMessage("Model scale set to " + view.getScale() + ".");
            });
        } else {
            commandSender.sendMessage("Model scale set to " + view.getScale() + ".");
        }
    }

    private void handleReset(CommandSender commandSender, String[] args) {
        ModelView view = history.get(commandSender.getName());
        if (view == null) {
            commandSender.sendMessage("Nothing to reset.");
            return;
        }

        view.clear();

        Renderer renderer = view.getRenderer();
        if (renderer instanceof DeferredRenderer) {
            ((DeferredRenderer)renderer).addCallback(() -> {
                commandSender.sendMessage("Model reset.");
            });
        } else {
            commandSender.sendMessage("Model reset.");
        }
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

        DeferredRenderer renderer = new DeferredRenderer(plugin, 100);

        ModelView view = new ModelView(model, renderer);

        view.setScale(scale);
        view.setPosition(location.toVector());

        player.sendMessage("Rendering...");

        view.renderDirection(player.getWorld(), direction);

        renderer.addCallback(() -> {
            player.sendMessage("Rendered.");
        });

        history.put(player.getName(), view);
    }
}

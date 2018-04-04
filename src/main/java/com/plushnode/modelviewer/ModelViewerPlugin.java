package com.plushnode.modelviewer;

import com.plushnode.modelviewer.commands.ModelCommand;
import com.plushnode.modelviewer.fbx.FBXDocument;
import com.plushnode.modelviewer.fbx.io.FBXBinaryReader;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Logger;

public class ModelViewerPlugin extends JavaPlugin {
    private static Logger logger = Logger.getLogger("ModelViewer");

    public FBXDocument loadFBX(String name) {
        File file = new File(getDataFolder(), name + ".fbx");

        try {
            FBXBinaryReader reader = new FBXBinaryReader(new RandomAccessFile(file.getAbsolutePath(), "r"));
            return reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onEnable() {
        this.getCommand("model").setExecutor(new ModelCommand(this));

        getDataFolder().mkdirs();
    }

    @Override
    public void onDisable() {

    }
}
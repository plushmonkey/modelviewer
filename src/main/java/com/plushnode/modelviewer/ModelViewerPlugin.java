package com.plushnode.modelviewer;

import com.plushnode.modelviewer.commands.ModelCommand;
import com.plushnode.modelviewer.fbx.FBXDocument;
import com.plushnode.modelviewer.fbx.io.FBXBinaryReader;
import com.plushnode.modelviewer.fbx.node.FBXNode;
import com.plushnode.modelviewer.fbx.property.FBXPropertiesLoader;
import com.plushnode.modelviewer.fbx.property.FBXProperty;
import com.plushnode.modelviewer.fbx.property.FBXPropertyStore;
import com.plushnode.modelviewer.geometry.Model;
import com.plushnode.modelviewer.util.ModelLoader;
import com.plushnode.modelviewer.util.NativeMethods;
import com.plushnode.modelviewer.util.TempBlockManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.logging.Logger;

public class ModelViewerPlugin extends JavaPlugin {
    private static Logger logger = Logger.getLogger("ModelViewer");
    private TempBlockManager tempBlockManager = new TempBlockManager();

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
        NativeMethods.getInstance();

        this.getCommand("model").setExecutor(new ModelCommand(this));

        getDataFolder().mkdirs();
    }

    @Override
    public void onDisable() {
        tempBlockManager.resetAll();
    }
}
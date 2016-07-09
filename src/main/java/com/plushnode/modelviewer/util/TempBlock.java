package com.plushnode.modelviewer.util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

public class TempBlock {
    private BlockState previousState;
    private Material tempType;
    private boolean applyPhysics;

    public TempBlock(Block block, Material tempType) {
        this.previousState = block.getState();
        this.tempType = tempType;
        this.applyPhysics = false;

        NativeMethods.getInstance().setBlockFast(block, tempType.getId(), 0);
    }

    public TempBlock(BlockState blockState, Material tempType) {
        this.previousState = blockState;
        this.tempType = tempType;
        this.applyPhysics = false;

        NativeMethods.getInstance().setBlockFast(previousState.getBlock(), tempType.getId(), 0);
    }

    public TempBlock(Block block, Material tempType, boolean applyPhysics) {
        this.previousState = block.getState();
        this.tempType = tempType;
        this.applyPhysics = applyPhysics;

        if (this.applyPhysics) {
            block.setType(tempType);
        } else {
            NativeMethods.getInstance().setBlockFast(block, tempType.getId(), 0);
        }
    }

    // Refresh the block to the temporary state
    public void update() {
        Block block = previousState.getBlock();
        if (this.applyPhysics) {
            block.setType(tempType);
        } else {
            NativeMethods.getInstance().setBlockFast(block, tempType.getId(), 0);
        }
    }

    public void reset() {
        if (this.applyPhysics) {
            this.previousState.update(true);
        } else {
            Material previousType = this.previousState.getType();
            NativeMethods.getInstance().setBlockFast(this.previousState.getBlock(), previousType.getId(), 0);
        }
    }

    public Material getTempType() {
        return tempType;
    }

    public void setTempType(Material type) {
        if (type != this.tempType) {
            this.tempType = type;

            this.update();
        }
    }

    public BlockState getPreviousState() {
        return previousState;
    }
}

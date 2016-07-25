package com.plushnode.modelviewer.geometry;

import java.util.ArrayList;
import java.util.List;

// Stores indices
public class Face {
    private List<Integer> indices = new ArrayList<>();
    private int materialIndex = -1;

    public void setMaterialIndex(int index) {
        this.materialIndex = index;
    }

    public int getMaterialIndex() {
        return materialIndex;
    }

    public void addIndex(int index) {
        this.indices.add(index);
    }

    public int getSize() {
        return indices.size();
    }

    public int getIndex(int num) {
        return indices.get(num);
    }
}

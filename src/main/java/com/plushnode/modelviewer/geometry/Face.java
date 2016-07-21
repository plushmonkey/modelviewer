package com.plushnode.modelviewer.geometry;

import java.util.ArrayList;
import java.util.List;

// Stores indices
public class Face {
    private List<Integer> indices = new ArrayList<>();

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

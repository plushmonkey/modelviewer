package com.plushnode.modelviewer.geometry;

// Stores indices
public class Face {
    private int[] indices = new int[3];

    public Face(int index1, int index2, int index3) {
        this.indices[0] = index1;
        this.indices[1] = index2;
        this.indices[2] = index3;
    }

    public int getIndex(int num) {
        return indices[num];
    }
}

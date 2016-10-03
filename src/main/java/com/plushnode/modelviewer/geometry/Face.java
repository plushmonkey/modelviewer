package com.plushnode.modelviewer.geometry;

import com.plushnode.modelviewer.material.Material;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.ArrayList;
import java.util.List;

// Stores indices
public class Face {
    private List<Integer> uvIndices = new ArrayList<>();
    private List<Integer> indices = new ArrayList<>();
    private int materialIndex;

    public int getMaterialIndex() {
        return materialIndex;
    }

    public void setMaterialIndex(int index) {
        this.materialIndex = index;
    }

    public List<Integer> getUvIndices() {
        return uvIndices;
    }

    public void addUvIndex(Integer uvIndex) {
        this.uvIndices.add(uvIndex);
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

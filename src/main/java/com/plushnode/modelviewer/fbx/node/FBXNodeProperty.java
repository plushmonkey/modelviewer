package com.plushnode.modelviewer.fbx.node;

import java.util.List;

// A single property stored on the node
// A list of these will need to be processed to get the actual key-value properties for Properties70
// Seems to be stored on a Properties70 node as string (name), string (type), string (A,A+,AU,""), n values
public class FBXNodeProperty {
    private Object value;
    private FBXNodePropertyType type;

    public FBXNodeProperty(Object value, FBXNodePropertyType type) {
        this.value = value;
        this.type = type;
    }

    public FBXNodePropertyType getType() {
        return type;
    }

    public Short getShort() {
        return (Short)value;
    }

    public Boolean getBoolean() {
        return (Boolean)value;
    }

    public Integer getInt() {
        return (Integer)value;
    }

    public Float getFloat() {
        return (Float)value;
    }

    public Double getDouble() {
        return (Double)value;
    }

    public Long getLong() {
        return (Long)value;
    }

    @SuppressWarnings("unchecked")
    public List<Float> getFloatList() {
        return (List<Float>)value;
    }

    @SuppressWarnings("unchecked")
    public List<Double> getDoubleList() {
        return (List<Double>)value;
    }

    @SuppressWarnings("unchecked")
    public List<Long> getLongList() {
        return (List<Long>)value;
    }

    @SuppressWarnings("unchecked")
    public List<Integer> getIntList() {
        return (List<Integer>)value;
    }

    @SuppressWarnings("unchecked")
    public List<Byte> getByteList() {
        return (List<Byte>)value;
    }

    public String getString() {
        return (String)value;
    }
}

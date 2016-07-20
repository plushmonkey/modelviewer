package com.plushnode.modelviewer.fbx;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class AnyType {
    private Object value;

    public AnyType(Object value) {
        this.value = value;
    }

    public Integer asInt() { return (Integer)value; }
    public Float asFloat() { return (Float)value; }
    public Double asDouble() { return (Double)value; }
    public Boolean asBoolean() { return (Boolean)value; }
    public Vector3D asVector() { return (Vector3D)value; }

    public String asString() { return (String)value; }
}

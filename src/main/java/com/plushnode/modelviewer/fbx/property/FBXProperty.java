package com.plushnode.modelviewer.fbx.property;

import com.plushnode.modelviewer.fbx.AnyType;

// Key-value property from a Properties70 node
// A list of FBXNodeProperty needs to be processed to create this.
// Values in the list are stored as string, string, string, n values
public class FBXProperty {
    private String name;
    private String type;
    private AnyType value;

    public FBXProperty(String name, String type, AnyType value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public AnyType getValue() {
        return value;
    }
}

package com.plushnode.modelviewer.fbx.property;

import com.plushnode.modelviewer.fbx.AnyType;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(name).append(": ");

        switch (this.type) {
            case "bool":
            {
                sb.append(this.value.asBoolean());
            }
            break;
            case "ColorRGB":
            case "Color":
            case "Lcl Scaling":
            case "Lcl Rotation":
            case "Lcl Translation":
            case "Vector3D":
            {
                sb.append(this.value.asVector());
            }
            break;
            case "Roll":
            case "FieldOfView":
            case "Number":
            case "Real":
            case "Visibility":
            case "double":
            {
                sb.append(this.value.asDouble());
            }
            break;
            case "KString":
            case "object":
            {
                sb.append(this.value.asString());
            }
            break;
            case "enum":
            case "int":
            case "Integer":
            {
                sb.append(this.value.asInt());
            }
        }

        return sb.toString();
    }
}

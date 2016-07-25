package com.plushnode.modelviewer.fbx.property;

import com.plushnode.modelviewer.fbx.AnyType;
import com.plushnode.modelviewer.fbx.node.FBXNode;
import com.plushnode.modelviewer.fbx.node.FBXNodeProperty;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.ArrayList;
import java.util.List;

public class FBXPropertiesLoader {
    public static FBXPropertyStore loadProperties(FBXNode properties70) {
        List<FBXNodeProperty> properties = getAllNodeProperties(properties70);

        FBXPropertyStore store = new FBXPropertyStore();

        for (int i = 0; i < properties.size(); ) {
            String name = properties.get(i++).getString();
            String type = properties.get(i++).getString();
            String unknown = properties.get(i++).getString();
            String unknown2 = properties.get(i++).getString();
            AnyType value = null;

            switch (type) {
                case "bool":
                {
                    value = new AnyType(properties.get(i++).getInt() == 1);
                }
                break;
                case "ColorRGB":
                case "Color":
                case "Lcl Scaling":
                case "Lcl Rotation":
                case "Lcl Translation":
                case "Vector":
                case "Vector3D":
                {
                    double value1 = properties.get(i++).getDouble();
                    double value2 = properties.get(i++).getDouble();
                    double value3 = properties.get(i++).getDouble();

                    value = new AnyType(new Vector3D(value1, value2, value3));
                }
                break;
                case "Roll":
                case "FieldOfView":
                case "FieldOfViewX":
                case "FieldOfViewY":
                case "Number":
                case "Real":
                case "Visibility":
                case "OpticalCenterX":
                case "OpticalCenterY":
                case "double":
                {
                    value = new AnyType(properties.get(i++).getDouble());
                }
                break;
                case "DateTime":
                case "KString": // Unicode, I guess
                {
                    value = new AnyType(properties.get(i++).getString());
                }
                break;
                case "Visibility Inheritance":
                case "enum":
                case "int":
                case "Integer":
                {
                    value = new AnyType(properties.get(i++).getInt());
                }
                break;
                case "ULongLong":
                case "KTime":
                {
                    value = new AnyType(properties.get(i++).getLong());
                }
                break;
                case "object":
                case "Compound":
                {
                    //return store;
                }
                break;
                default:
                    throw new RuntimeException("Unknown property type " + type + " encountered.");
            }

            if (value != null)
                store.addProperty(new FBXProperty(name, type, value));
        }

        return store;
    }

    private static List<FBXNodeProperty> getAllNodeProperties(FBXNode properties70) {
        List<FBXNodeProperty> properties = new ArrayList<>();
        List<FBXNode> propertyNodes = properties70.getNodes();

        for (FBXNode node : propertyNodes) {
            List<FBXNodeProperty> nodeProperties = node.getProperties();

            if (nodeProperties != null)
                properties.addAll(nodeProperties);
        }

        return properties;
    }
}

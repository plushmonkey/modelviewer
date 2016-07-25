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

    @Override
    public String toString() {
        switch (this.type) {
            case SHORT:
                return getShort().toString();
            case BOOLEAN:
                return getBoolean().toString();
            case INTEGER:
                return getInt().toString();
            case FLOAT:
                return getFloat().toString();
            case DOUBLE:
                return getDouble().toString();
            case LONG:
                return getLong().toString();
            case STRING:
                return getString();
        }

        StringBuilder sb = new StringBuilder();

        sb.append("[ ");

        switch (this.type) {
            case FLOAT_LIST: {
                List<Float> vals = getFloatList();
                for (int i = 0; i < vals.size(); ++i) {
                    if (i != 0)
                        sb.append(", ");
                    sb.append(vals.get(i));
                }
            }
            break;
            case DOUBLE_LIST: {
                List<Double> vals = getDoubleList();
                for (int i = 0; i < vals.size(); ++i) {
                    if (i != 0)
                        sb.append(", ");
                    sb.append(vals.get(i));
                }
            }
            break;
            case LONG_LIST: {
                List<Long> vals = getLongList();
                for (int i = 0; i < vals.size(); ++i) {
                    if (i != 0)
                        sb.append(", ");
                    sb.append(vals.get(i));
                }
            }
            break;
            case INTEGER_LIST: {
                List<Integer> vals = getIntList();
                for (int i = 0; i < vals.size(); ++i) {
                    if (i != 0)
                        sb.append(", ");
                    sb.append(vals.get(i));
                }
            }
            break;
            case BYTE_LIST: {
                List<Byte> vals = getByteList();
                for (int i = 0; i < vals.size(); ++i) {
                    if (i != 0)
                        sb.append(", ");
                    sb.append((int)vals.get(i));
                }
            }
            break;
        }

        sb.append(" ]");

        return sb.toString();
    }
}

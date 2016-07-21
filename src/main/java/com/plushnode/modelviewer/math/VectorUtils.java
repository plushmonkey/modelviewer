package com.plushnode.modelviewer.math;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class VectorUtils {
    public static Vector3D multiply(RealMatrix matrix, Vector3D vector) {
        RealVector v = new ArrayRealVector();

        v = v.append(vector.getX())
                .append(vector.getY())
                .append(vector.getZ())
                .append(1);

        v = matrix.operate(v);

        return new Vector3D(v.getEntry(0), v.getEntry(1), v.getEntry(2));
    }
}

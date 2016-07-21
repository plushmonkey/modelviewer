package com.plushnode.modelviewer.scene;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class Transform {
    // Uniform scale
    private double scale;
    private Rotation rotation;
    private Vector3D translation;

    public Transform() {
        this.scale = 1.0;
        this.rotation = Rotation.IDENTITY;
        this.translation = Vector3D.ZERO;
    }

    public double getScale() {
        return scale;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public Vector3D getTranslation() {
        return translation;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void rotate(Rotation rotation) {
        if (this.rotation == null) {
            this.rotation = rotation;
        } else {
            this.rotation = rotation.applyTo(this.rotation);
        }
    }

    // Sets the absolute rotation
    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }

    public void translate(Vector3D amount) {
        this.translation = this.translation.add(amount);
    }

    public void setTranslation(Vector3D translation) {
        this.translation = translation;
    }

    public RealMatrix getScaleMatrix() {
        RealMatrix matrix = MatrixUtils.createRealIdentityMatrix(4);

        matrix = matrix.scalarMultiply(scale);
        matrix.setEntry(3, 3, 1);

        return matrix;
    }

    public RealMatrix getRotationMatrix() {
        RealMatrix A = MatrixUtils.createRealMatrix(this.rotation.getMatrix());

        RealMatrix result = MatrixUtils.createRealIdentityMatrix(4);
        for (int i = 0; i < 3; ++i)
            result.setColumnVector(i, A.getColumnVector(i).append(0));
        return result;
    }

    public RealMatrix getTranslationMatrix() {
        RealMatrix matrix = MatrixUtils.createRealIdentityMatrix(4);

        matrix.setColumn(3, new double[]{ translation.getX(), translation.getY(), translation.getZ(), 1 });

        return matrix;
    }

    public RealMatrix getMatrix() {
        return getTranslationMatrix().multiply(getRotationMatrix().multiply(getScaleMatrix()));
    }

    public Transform applyTo(Transform transform) {
        Transform result = new Transform();

        result.setTranslation(getTranslation().add(transform.getTranslation()));
        result.setRotation(getRotation().applyTo(transform.getRotation()));
        result.setScale(getScale() * transform.getScale());

        return result;
    }
}

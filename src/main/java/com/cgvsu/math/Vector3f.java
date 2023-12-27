package com.cgvsu.math;

public class Vector3f {
    protected float x;
    protected float y;
    protected float z;


    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
    public Vector3f(Vector3f vector3f){
        this.x = vector3f.x;
        this.y = vector3f.y;
        this.z = vector3f.z;
    }
    public void add(Vector3f vector) {
        this.x += vector.x;
        this.y += vector.y;
        this.z += vector.z;
    }

    public void subtract(Vector3f vector) {
        this.x -= vector.x;
        this.y -= vector.y;
        this.z -= vector.z;
    }

    public boolean equals(Vector3f other) {
        // todo: желательно, чтобы это была глобальная константа
        final float eps = 1e-7f;
        return Math.abs(x - other.x) < eps && Math.abs(y - other.y) < eps && Math.abs(z - other.z) < eps;
    }


}

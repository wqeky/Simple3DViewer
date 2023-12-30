package com.cgvsu.math;

public class Vector2f { // вектор в 2-мерном пространстве
    private float x, y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setX(final float x) {
        this.x = x;
    }

    public void setY(final float y) {
        this.y = y;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public boolean equals(final Vector2f other) {
        final float eps = 1e-7f;
        return (Math.abs(x - other.x) < eps) && (Math.abs(y - other.y) < eps);
    }
}


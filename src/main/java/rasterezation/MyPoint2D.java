package com.cgvsu.rasterization;

import java.util.Objects;

public class MyPoint2D {
    //todo: сделать х и у float
    private double x;
    private double y;

    public MyPoint2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyPoint2D myPoint2D = (MyPoint2D) o;
        return Double.compare(myPoint2D.x, x) == 0 && Double.compare(myPoint2D.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


    @Override
    public String toString() {
        return "MyPoint2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

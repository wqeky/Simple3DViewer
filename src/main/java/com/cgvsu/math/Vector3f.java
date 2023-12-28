package com.cgvsu.math;

public class Vector3f {

    private double x;
    private double y;
    private double z;
    private static double eps = 1e-4;

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

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Vector3f(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public double get(int index) {
        switch (index){
            case 0: return x;
            case 1: return y;
            case 2: return z;
        }
        throw new IllegalArgumentException("Индекс выходит за границы");
    }

    // Сложение векторов
    public Vector3f sumVec(Vector3f other) {
        return new Vector3f(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    // Вычитание векторов
    public Vector3f subtractVec(Vector3f other) {
        return new Vector3f(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    // Умножение на скаляр
    public Vector3f multiplyVec(double scalar) {
        return new Vector3f(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    // Деление на скаляр
    public Vector3f divide(double scalar) {
        if (Math.abs(scalar) < eps) {
            throw new ArithmeticException("Деление на ноль");
        }
        return new Vector3f(this.x / scalar, this.y / scalar, this.z / scalar);
    }

    // Вычисление длины вектора
    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    // Нормализация вектора
    public Vector3f normalize() {
        double len = length();
        if (Math.abs(len) < eps) {
            return new Vector3f(0, 0, 0);
        }
        return new Vector3f(x / len, y / len, z / len);
    }

    // Скалярное произведение векторов
    public double dotProduct(Vector3f other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    // Векторное произведение векторов
    public Vector3f crossProduct(Vector3f other) {
        return new Vector3f(
                this.y * other.z - this.z * other.y,
                this.z * other.x - this.x * other.z,
                this.x * other.y - this.y * other.x
        );
    }
    /*public float x;
    public float y;
    public float z;
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector3f divideScalar(float num) {
        final float eps = 1e-7f;
        if (num - 0 < eps)
            throw new ArithmeticException("Division by zero");
        return new Vector3f(x / num, y / num, z / num);
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
    //длина вектора

    public boolean equals(Vector3f other) {
        final float eps = 1e-7f;
        return Math.abs(x - other.x) < eps && Math.abs(y - other.y) < eps && Math.abs(z - other.z) < eps;
    }
*/

}

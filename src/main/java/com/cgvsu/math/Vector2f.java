package com.cgvsu.math;

public class Vector2f {
    public Vector2f(double x, double y) {
        this.x = x;
        this.y = y;

    }

    private double x;
    private double y;
    public double get(int index) {
        switch (index){
            case 0: return x;
            case 1: return y;
        }
        throw new IllegalArgumentException("Индекс выходит за границы");
    }

    // Сложение векторов
    public Vector2f addVec(Vector2f other) {
        return new Vector2f(this.x + other.x, this.y + other.y);
    }

    // Вычитание векторов
    public Vector2f subtractionVec(Vector2f other) {
        return new Vector2f(this.x - other.x, this.y - other.y);
    }

    // Умножение на скаляр
    public Vector2f multiply(double scalar) {
        return new Vector2f(this.x * scalar, this.y * scalar);
    }

    // Деление на скаляр
    public Vector2f divide(double scalar) {
        if (Math.abs(scalar) < 1e-7f) {
            throw new ArithmeticException("Деление на ноль");
        }
        return new Vector2f(this.x / scalar, this.y / scalar);
    }

    // Вычисление длины вектора
    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    // Нормализация вектора
    public Vector2f normalize() {
        double len = length();
        if (Math.abs(len) < 1e-7f) {
            return new Vector2f(0, 0);
        }
        return new Vector2f(x / len, y / len);
    }

    // Скалярное произведение векторов
    public double dotProduct(Vector2f other) {
        return this.x * other.x + this.y * other.y;
    }
    public void setX(final double x){this.x = x;}

    public void setY (final double y){this.y = y;}

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public boolean equals(final Vector2f other) {
        final float eps = 1e-7f;
        return (Math.abs(x - other.x) < eps) && (Math.abs(y - other.y) < eps);
    }
}
package com.cgvsu.math;


public class Matrix3x3{

    private double[][] matrix = new double[3][3];

    public Matrix3x3(double[][] data) {
        if (data.length != 3 || data[0].length != 3) {
            throw new IllegalArgumentException("Матрица должна быть 3x3");
        }
        this.matrix = data;
    }

    public double[][] getMatrix() {
        return matrix;
    }
    // Сложение матриц
    public Matrix3x3 sumMatrix(Matrix3x3 other) {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = this.matrix[i][j] + other.matrix[i][j];
            }
        }
        return new Matrix3x3(result);
    }

    // Вычитание матриц
    public Matrix3x3 subtractMatrix(Matrix3x3 other) {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = this.matrix[i][j] - other.matrix[i][j];
            }
        }
        return new Matrix3x3(result);
    }

    //Умножение на вектор3Д
    public Vector3f multiplyMatrix(Vector3f vector) {
        if (vector == null) {
            throw new NullPointerException("Вектор не может быть нулевым");
        }
        double[] result = new double[3];
        for (int i = 0; i < 3; i++) {
            result[i] = 0;
            for (int j = 0; j < 3; j++) {
                result[i] += this.matrix[i][j] * vector.get(j);
            }
        }
        return new Vector3f(result[0],result[1], result[2]);
    }

    // Умножение на матрицу
    public Matrix3x3 multiplyMatrix(Matrix3x3 other) {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = 0;
                for (int k = 0; k < 3; k++) {
                    result[i][j] += this.matrix[i][k] * other.matrix[k][j];
                }
            }
        }
        return new Matrix3x3(result);
    }

    // Транспонирование
    public Matrix3x3 transpose() {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = this.matrix[j][i];
            }
        }
        return new Matrix3x3(result);
    }

    // Задание единичной матрицы
    public static Matrix3x3 identity() {
        double[][] identityMatrix = new double[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        };
        return new Matrix3x3(identityMatrix);
    }

    // Задание нулевой матрицы
    public static Matrix3x3 zero() {
        double[][] zeroMatrix = new double[][]{
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
        return new Matrix3x3(zeroMatrix);
    }
    public double determinate(){
        return (matrix[0][0]*matrix[1][1]*matrix[2][2]-(matrix[0][2]*matrix[1][1]*matrix[2][0]) +matrix[0][1]*matrix[1][2]*matrix[2][0]-(matrix[0][1]*matrix[1][0]*matrix[2][2]) +matrix[0][2]*matrix[1][0]*matrix[2][1]-(matrix[0][0]*matrix[1][2]*matrix[2][1]));
    }
}

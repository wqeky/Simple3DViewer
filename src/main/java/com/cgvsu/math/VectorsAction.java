package com.cgvsu.math;

import java.util.ArrayList;


public class VectorsAction  {

    public static Vector3f sumVectors(Vector3f... vectors) {
        float x = vectors[0].getX();
        float y = vectors[0].getY();
        float z = vectors[0].getZ();
        for (int i = 1; i < vectors.length; i++) {
            x += vectors[i].getX();
            y += vectors[i].getY();
            z += vectors[i].getZ();
        }
        return new Vector3f(x, y, z);
    }
//умножение вектора на на число

    public static Vector3f sumVectors(ArrayList<Vector3f> vectors) {
        float x = vectors.get(0).getX();
        float y = vectors.get(0).getY();
        float z = vectors.get(0).getZ();
        for (int i = 1; i < vectors.size(); i++) {
            x += vectors.get(i).getX();
            y += vectors.get(i).getY();
            z += vectors.get(i).getZ();
        }
        return new Vector3f(x, y, z);
    }


    public static Vector3f calculateCrossProduct(Vector3f vector1, Vector3f vector2) {
        float x = vector1.getY() * vector2.getZ() - vector1.getZ() * vector2.getY();
        float y = vector1.getZ() * vector2.getX() - vector1.getX() * vector2.getZ();
        float z = vector1.getX() * vector2.getY() - vector1.getY() * vector2.getX();
        return new Vector3f(x, y, z);
    }

    public static Vector3f createFromTwoPoints(float x1, float y1, float z1, float x2, float y2, float z2) {
        return new Vector3f(x2 - x1, y2 - y1, z2 - z1);
    }

    public static Vector3f createFromTwoPoints(Vector3f vertex1, Vector3f vertex2) {
        return new Vector3f(vertex2.getX() - vertex1.getX(), vertex2.getY() - vertex1.getY(), vertex2.getZ() - vertex1.getZ());
    }
}

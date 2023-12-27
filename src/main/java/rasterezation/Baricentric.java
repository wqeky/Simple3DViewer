package com.cgvsu.rasterization;

import com.cgvsu.math.Vector2f;

public class Baricentric { // класс одного из коллег проекта (по заданию)
    double u;
    double v;
    double w;

    public Baricentric(com.cgvsu.rasterization.MyPoint2D a1, com.cgvsu.rasterization.MyPoint2D a2, com.cgvsu.rasterization.MyPoint2D a3, com.cgvsu.rasterization.MyPoint2D p){
        double s = (a2.getX() - a1.getX())*(a3.getY()-a1.getY())-(a3.getX()-a1.getX())*(a2.getY()-a1.getY());
        double s1 = (a2.getX() - p.getX())*(a3.getY()-p.getY())-(a3.getX()-p.getX())*(a2.getY()-p.getY());
        double s2 = (a3.getX() - p.getX())*(a1.getY()-p.getY())-(a1.getX()-p.getX())*(a3.getY()-p.getY());
        this.u = s1/s;
        this.v = s2/s;
        this.w = 1 - u - v;
    }
}
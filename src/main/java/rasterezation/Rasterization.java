package com.cgvsu.rasterization;

import com.cgvsu.math.Vector2f;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Rasterization { // класс одного из коллег проекта (по заданию)

    public static void fillTriangle(
            final com.cgvsu.rasterization.GraphicsUtils gr,
            com.cgvsu.rasterization.MyPoint2D p1, com.cgvsu.rasterization.MyPoint2D p2, com.cgvsu.rasterization.MyPoint2D p3,
            com.cgvsu.rasterization.MyColor myColor1, com.cgvsu.rasterization.MyColor myColor2, com.cgvsu.rasterization.MyColor myColor3) {


        List<com.cgvsu.rasterization.MyPoint2D> points = new ArrayList<>(Arrays.asList(p1, p2, p3));

        points.sort(Comparator.comparingDouble(com.cgvsu.rasterization.MyPoint2D::getY));

        final double x1 = points.get(0).getX();
        double x2 = points.get(1).getX();
        double x3 = points.get(2).getX();
        double y1 = points.get(0).getY();
        double y2 = points.get(1).getY();
        double y3 = points.get(2).getY();

        for (int y = (int) Math.round(y1 + 0.5); y <= y2; y++) {
            double startX = getX(y, x1, x2, y1, y2);
            double endX = getX(y, x1, x3, y1, y3);

            fillLine(gr, y, startX, endX, myColor1, myColor2, myColor3, x1, x2, x3, y1, y2, y3);
        }

        for (int y = (int) Math.round(y2 + 0.5); y < y3; y++) {
            double startX = getX(y, x1, x3, y1, y3);
            double endX = getX(y, x2, x3, y2, y3);
            fillLine(gr, y, startX, endX, myColor1, myColor2, myColor3, x1, x2, x3, y1, y2, y3);
        }
    }

    public static void fillTriangle(
            com.cgvsu.rasterization.GraphicsUtils gr,
            double x1, double y1,
            double x2, double y2,
            double x3, double y3,
            com.cgvsu.rasterization.MyColor myColor1, com.cgvsu.rasterization.MyColor myColor2, com.cgvsu.rasterization.MyColor myColor3) {
        fillTriangle(gr, new com.cgvsu.rasterization.MyPoint2D(x1, y1), new com.cgvsu.rasterization.MyPoint2D(x2, y2), new com.cgvsu.rasterization.MyPoint2D(x3, y3), myColor1, myColor2, myColor3);
    }

    public static void fillTexture(final com.cgvsu.rasterization.GraphicsUtils gr,
                                   com.cgvsu.rasterization.MyPoint2D p1, com.cgvsu.rasterization.MyPoint2D p2, com.cgvsu.rasterization.MyPoint2D p3,
                                   com.cgvsu.rasterization.MyPoint2D tc1, com.cgvsu.rasterization.MyPoint2D tc2, com.cgvsu.rasterization.MyPoint2D tc3, Image texture) {

        List<com.cgvsu.rasterization.MyPoint2D> points = new ArrayList<>(Arrays.asList(p1, p2, p3));
        List<com.cgvsu.rasterization.MyPoint2D> coordsT = new ArrayList<>(Arrays.asList(tc1, tc2, tc3));
        points.sort(Comparator.comparingDouble(com.cgvsu.rasterization.MyPoint2D::getY));
        coordsT.sort(Comparator.comparingDouble(com.cgvsu.rasterization.MyPoint2D::getY));


        final double x1 = points.get(0).getX();
        double x2 = points.get(1).getX();
        double x3 = points.get(2).getX();
        double y1 = points.get(0).getY();
        double y2 = points.get(1).getY();
        double y3 = points.get(2).getY();

        for (int y = (int) Math.round(y1 + 0.5); y <= y2; y++) {
            double startX = getX(y, x1, x2, y1, y2);
            double endX = getX(y, x1, x3, y1, y3);
            fillTextureLine(gr, y, p1, p2, p3, tc1, tc2, tc3, startX, endX, texture);
        }

        for (int y = (int) Math.round(y2 + 0.5); y < y3; y++) {
            double startX = getX(y, x1, x3, y1, y3);
            double endX = getX(y, x2, x3, y2, y3);
            fillTextureLine(gr, y, p1, p2, p3, tc1, tc2, tc3, startX, endX, texture);
        }


    }

    private static void fillTextureLine(
            final com.cgvsu.rasterization.GraphicsUtils gr, int ys, com.cgvsu.rasterization.MyPoint2D p1, com.cgvsu.rasterization.MyPoint2D p2, com.cgvsu.rasterization.MyPoint2D p3, com.cgvsu.rasterization.MyPoint2D p1t, com.cgvsu.rasterization.MyPoint2D p2t, com.cgvsu.rasterization.MyPoint2D p3t,
            double startX, double endX, Image texture) {

        if (Double.compare(startX, endX) > 0) {
            double temp = startX;
            startX = endX;
            endX = temp;
        }


        Color clr;
        for (int xs = (int) Math.round(startX + 0.5); xs < endX; xs++){
            com.cgvsu.rasterization.Baricentric bc = new com.cgvsu.rasterization.Baricentric(p1, p2, p3, new com.cgvsu.rasterization.MyPoint2D(xs, ys));
            double xtexture =  bc.u* p1t.getX() + bc.v* p2t.getX() + bc.w* p3t.getX();
            double ytexture =  bc.u* p1t.getY() + bc.v* p2t.getY() + bc.w* p3t.getY();
            xtexture *= texture.getWidth();
            ytexture = texture.getHeight() - ytexture*texture.getHeight();

            clr = texture.getPixelReader().getColor((int) xtexture, (int) ytexture);
            com.cgvsu.rasterization.MyColor color = new com.cgvsu.rasterization.MyColor(clr.getRed(), clr.getGreen(), clr.getBlue());
            gr.setPixel(xs, ys, color);
        }
    }

    public static com.cgvsu.rasterization.MyColor getShade(com.cgvsu.rasterization.MyColor color, double shade) {
        double red = Math.abs(color.getRed() * shade/2);
        double green = Math.abs(color.getGreen() * shade/2);
        double blue = Math.abs(color.getBlue() * shade/2);

        return new com.cgvsu.rasterization.MyColor(red, green, blue);
    }


    private static double getX(double y, double x1, double x2, double y1, double y2) {
        return (x2 - x1) * (y - y1) / (y2 - y1) + x1;
    }

    private static void fillLine(
            final com.cgvsu.rasterization.GraphicsUtils gr, int y, double startX, double endX,
            com.cgvsu.rasterization.MyColor myColor1, com.cgvsu.rasterization.MyColor myColor2, com.cgvsu.rasterization.MyColor myColor3,
            double x1, double x2, double x3,
            double y1, double y2, double y3) {

        if (Double.compare(startX, endX) > 0) {
            double temp = startX;
            startX = endX;
            endX = temp;
        }

        for (int x = (int) Math.round(startX + 0.5); x < endX; x++) {
            gr.setPixel(x, y, getColor(myColor1, myColor2, myColor3, x, y, x1, x2, x3, y1, y2, y3));
        }
    }


    private static com.cgvsu.rasterization.MyColor getColor(
            com.cgvsu.rasterization.MyColor myColor1, com.cgvsu.rasterization.MyColor myColor2, com.cgvsu.rasterization.MyColor myColor3,
            double x, double y,
            double x1, double x2, double x3,
            double y1, double y2, double y3) {

        double detT = (y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3);

        double alpha = ((y2 - y3) * (x - x3) + (x3 - x2) * (y - y3)) / detT;

        double betta = ((y3 - y1) * (x - x3) + (x1 - x3) * (y - y3)) / detT;

        double gamma = 1 - alpha - betta;

        double r = (alpha * myColor1.getRed() + betta * myColor2.getRed() + gamma * myColor3.getRed());
        double g = (alpha * myColor1.getGreen() + betta * myColor2.getGreen() + gamma * myColor3.getGreen());
        double b = (alpha * myColor1.getBlue() + betta * myColor2.getBlue() + gamma * myColor3.getBlue());

        return new com.cgvsu.rasterization.MyColor(r, g, b);
    }

}

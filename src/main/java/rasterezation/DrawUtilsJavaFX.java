package com.cgvsu.rasterization;


import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class DrawUtilsJavaFX extends com.cgvsu.rasterization.GraphicsUtils<Canvas> {
    public DrawUtilsJavaFX(Canvas graphics) {
        super(graphics);
    }

    public DrawUtilsJavaFX(java.awt.Canvas canvas) {
        super(canvas);
    }

    @Override
    public void setPixel(int x, int y, com.cgvsu.rasterization.MyColor myColor) {
        graphics.getGraphicsContext2D().getPixelWriter().setColor(x, y, toColor(myColor));
    }

    private Color toColor(com.cgvsu.rasterization.MyColor myColor) {
        return Color.color(myColor.getRed(), myColor.getGreen(), myColor.getBlue());
    }
}
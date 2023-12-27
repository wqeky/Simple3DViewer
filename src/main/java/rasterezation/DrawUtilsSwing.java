package com.cgvsu.rasterization;

import java.awt.*;

public class DrawUtilsSwing extends com.cgvsu.rasterization.GraphicsUtils<Graphics> { // класс одного из коллег проекта (по заданию)


    public DrawUtilsSwing(Graphics graphics) {
        super(graphics);
    }

    @Override
    public void setPixel(int x, int y, com.cgvsu.rasterization.MyColor myColor) {
        graphics.setColor(toColor(myColor));
        graphics.drawLine(x, y, x, y);
    }

    private Color toColor(com.cgvsu.rasterization.MyColor myColor) {
        return new Color((int) (255 * myColor.getRed()), (int) (255 * myColor.getGreen()), (int) (255 * myColor.getBlue()));
    }
}
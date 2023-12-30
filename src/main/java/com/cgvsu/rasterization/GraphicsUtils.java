package com.cgvsu.rasterization;

import java.awt.*;

public abstract class GraphicsUtils<T> {
    T graphics;

    public GraphicsUtils(T graphics) {
        this.graphics = graphics;
    }

    public GraphicsUtils(Canvas canvas) {

    }

    public abstract void setPixel(int x, int y, com.cgvsu.rasterization.MyColor myColor);

}
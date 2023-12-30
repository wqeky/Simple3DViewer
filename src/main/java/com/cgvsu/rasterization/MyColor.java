package com.cgvsu.rasterization;

public class MyColor {


    public static final MyColor RED = new MyColor(1, 0, 0);
    public static final MyColor GREEN = new MyColor(0, 1, 0);
    public static final MyColor BLUE = new MyColor(0, 0, 1);


    private final double R;
    private final double G;
    private final double B;

    public MyColor(double r, double g, double b) {

        if (!(r < 0.0D) && !(r > 1.0D)) {
            if (!(g < 0.0D) && !(g > 1.0D)) {
                if (!(b < 0.0D) && !(b > 1.0D)) {
                    R = r;
                    G = g;
                    B = b;
                } else {
                    throw new IllegalArgumentException("Color's blue value (" + b + ") must be in the range 0.0-1.0");
                }
            } else {
                throw new IllegalArgumentException("Color's green value (" + g + ") must be in the range 0.0-1.0");
            }
        } else {
            throw new IllegalArgumentException("Color's red value (" + r + ") must be in the range 0.0-1.0");
        }
    }

    public double getRed() {
        return R;
    }

    public double getGreen() {
        return G;
    }

    public double getBlue() {
        return B;
    }

    @Override
    public String toString() {
        return "MyColor{" +
                "R=" + R +
                ", G=" + G +
                ", B=" + B +
                '}';
    }
}

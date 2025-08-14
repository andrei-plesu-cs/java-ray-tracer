package org.andreip.utils;

public class Pixel extends Vec3 {
    public Pixel(float e0, float e1, float e2) {
        super(e0, e1, e2);
    }

    public String getStringLine() {
        var r = x();
        var g = y();
        var b = z();

        // Apply a linear to gamma transform for gamma 2
        r = linearToGamma(r);
        g = linearToGamma(g);
        b = linearToGamma(b);

        // Translate the [0,1] component values to the byte range [0,255].
        var intensity = new Interval(0.000f, 0.999f);
        int rbyte = (int) (256 * intensity.clamp(r));
        int gbyte = (int) (256 * intensity.clamp(g));
        int bbyte = (int) (256 * intensity.clamp(b));

        return rbyte + " " + gbyte + " " + bbyte;
    }

    private float linearToGamma(float linearComponent) {
        if (linearComponent > 0)
            return (float) Math.sqrt(linearComponent);

        return 0;
    }
}
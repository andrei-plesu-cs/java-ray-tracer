package org.andreip.utils;

public record Interval(double min, double max) {
    public static final Interval EMPTY = new Interval();
    public static final Interval UNIVERSE = new Interval(Double.MIN_VALUE, Double.MAX_VALUE);

    public Interval() {
        this(Double.MAX_VALUE, Double.MIN_VALUE);
    }

    public double size() {
        return max - min;
    }

    public boolean contains(double x) {
        return min <= x && x <= max;
    }

    public boolean surrounds(double x) {
        return min < x && x < max;
    }

    public double clamp(double x) {
        if (x < min) return min;
        if (x > max) return max;
        return x;
    }
}
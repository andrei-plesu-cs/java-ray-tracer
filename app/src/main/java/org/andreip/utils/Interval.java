package org.andreip.utils;

public record Interval(float min, float max) {
    public static final Interval EMPTY = new Interval();
    public static final Interval UNIVERSE = new Interval(Float.MIN_VALUE, Float.MAX_VALUE);

    public Interval() {
        this(Float.MAX_VALUE, Float.MIN_VALUE);
    }

    public float size() {
        return max - min;
    }

    public boolean contains(float x) {
        return min <= x && x <= max;
    }

    public boolean surrounds(float x) {
        return min < x && x < max;
    }

    public float clamp(float x) {
        if (x < min) return min;
        if (x > max) return max;
        return x;
    }
}
package org.andreip.utils;

public final class Utilities {
    private Utilities() {
        throw new AssertionError();
    }

    public static float degreesToRadians(float degrees) {
        return degrees * (float) Math.PI / 180;
    }

    public static float randomInRange(float min, float max) {
        return ((float) Math.random() * (max - min)) + min;
    }
}
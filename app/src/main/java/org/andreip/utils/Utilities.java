package org.andreip.utils;

public final class Utilities {
    private Utilities() {
        throw new AssertionError();
    }

    public static double degreesToRadians(double degrees) {
        return degrees * Math.PI / 180.0;
    }

    public static double randomInRange(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }
}
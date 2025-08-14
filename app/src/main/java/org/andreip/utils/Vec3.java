package org.andreip.utils;

import static org.andreip.utils.Utilities.*;

public class Vec3 {
    private final double[] e;

    public Vec3() {
        this(0.0, 0.0, 0.0);
    }

    public Vec3(double e0, double e1, double e2) {
        e = new double[] { e0, e1, e2 };
    }

    public final double x() { return e[0]; }

    public final double y() { return e[1]; }

    public final double z() { return e[2]; }

    public static final Vec3 random() {
        return new Vec3(Math.random(), Math.random(), Math.random());
    }

    public static final Vec3 random(double min, double max) {
        return new Vec3(randomInRange(min, max), randomInRange(min, max), randomInRange(min, max));
    }

    public static final Vec3 randomUnitVector() {
        while (true) {
            var p = random(-1, 1);
            var lensq = p.lengthSquared();
            if (1e-160 < lensq && lensq <= 1) {
                return p.scale(1.0 / Math.sqrt(lensq));
            }
        }
    }

    public static final Vec3 randomOnHemisphere(Vec3 normal) {
        Vec3 onUnitSphere = randomUnitVector();
        if (onUnitSphere.dot(normal) > 0.0) { // In the same hemisphere as the normal
            return onUnitSphere;
        } else {
            return onUnitSphere.scale(-1);
        }
    }

    public static final Vec3 randomInUnitDisc() {
        while (true) {
            var p = new Vec3(randomInRange(-1, 1), randomInRange(-1, 1), 0.0);
            if (p.lengthSquared() < 1.0) {
                return p;
            }
        }
    }

    public final Vec3 add(Vec3 v) {
        return new Vec3(x() + v.x(), y() + v.y(), z() + v.z());
    }

    public final Vec3 add(double t) {
        return new Vec3(x() + t, y() + t, z() + t);
    }

    public final Vec3 subtract(Vec3 v) {
        return new Vec3(x() - v.x(), y() - v.y(), z() - v.z());
    }

    public final Vec3 subtract(double t) {
        return add(-t);
    }

    public final Vec3 multiply(Vec3 v) {
        return new Vec3(x() * v.x(), y() * v.y(), z() * v.z());
    }

    public final Vec3 scale(double t) {
        return new Vec3(x() * t, y() * t, z() * t);
    }

    public final double length() {
        return Math.sqrt(lengthSquared());
    }

    public final double lengthSquared() {
        return dot(this);
    }

    public final double dot(Vec3 v) {
        return x() * v.x()
            + y() * v.y()
            + z() * v.z();
    }

    public final Vec3 cross(Vec3 v) {
        return new Vec3(y() * v.z() - z() * v.y(),
                z() * v.x() - x() * v.z(),
                x() * v.y() - y() * v.x());
    }

    public final Vec3 toUnit() {
        return scale(1.0 / length());
    }

    public final Vec3 negate() {
        return scale(-1.0);
    }

    public final Pixel toPixel() {
        return new Pixel(x(), y(), z());
    }

    public final boolean nearZero() {
        // Return true if the vector is close to zero in all dimensions.
        var s = 1e-8;
        return (Math.abs(x()) < s) && (Math.abs(y()) < s) && (Math.abs(z()) < s);
    }

    public final Vec3 reflect(Vec3 n) {
        return subtract(n.scale(2 * dot(n)));
    }

    public final Vec3 refract(Vec3 n, double etaiOverEtat) {
        var cosTheta = Math.min(negate().dot(n), 1.0);
        Vec3 rOutPerp = add(n.scale(cosTheta))
            .scale(etaiOverEtat);
        Vec3 rOutParallel = n.scale(-Math.sqrt(Math.abs(1.0 - rOutPerp.lengthSquared())));
        return rOutPerp.add(rOutParallel);
    }

    public final Vec3 set(Vec3 v) {
        e[0] = v.x();
        e[1] = v.y();
        e[2] = v.z();
        return this;
    }

    @Override
    public String toString() {
        return "Vec3[x = %f, y = %f, z = %f".formatted(x(), y(), z());
    }
}
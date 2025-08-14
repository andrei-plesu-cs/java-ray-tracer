package org.andreip.utils;

import static org.andreip.utils.Utilities.*;

public class Vec3 {
    private final float[] e;

    public Vec3() {
        this(0, 0, 0);
    }

    public Vec3(float e0, float e1, float e2) {
        e = new float[] { e0, e1, e2 };
    }

    public final float x() { return e[0]; }

    public final float y() { return e[1]; }

    public final float z() { return e[2]; }

    public static final Vec3 random() {
        return new Vec3((float) Math.random(), (float) Math.random(), (float) Math.random());
    }

    public static final Vec3 random(float min, float max) {
        return new Vec3(randomInRange(min, max), randomInRange(min, max), randomInRange(min, max));
    }

    public static final Vec3 randomUnitVector() {
        while (true) {
            var p = random(-1, 1);
            var lensq = p.lengthSquared();
            if (1e-160 < lensq && lensq <= 1) {
                return p.scale(1 / (float) Math.sqrt(lensq));
            }
        }
    }

    public static final Vec3 randomOnHemisphere(Vec3 normal) {
        Vec3 onUnitSphere = randomUnitVector();
        if (onUnitSphere.dot(normal) > 0) { // In the same hemisphere as the normal
            return onUnitSphere;
        } else {
            return onUnitSphere.scale(-1);
        }
    }

    public static final Vec3 randomInUnitDisc() {
        while (true) {
            var p = new Vec3(randomInRange(-1, 1), randomInRange(-1, 1), 0);
            if (p.lengthSquared() < 1) {
                return p;
            }
        }
    }

    public final Vec3 add(Vec3 v) {
        return new Vec3(x() + v.x(), y() + v.y(), z() + v.z());
    }

    public final Vec3 add(float t) {
        return new Vec3(x() + t, y() + t, z() + t);
    }

    public final Vec3 subtract(Vec3 v) {
        return new Vec3(x() - v.x(), y() - v.y(), z() - v.z());
    }

    public final Vec3 subtract(float t) {
        return add(-t);
    }

    public final Vec3 multiply(Vec3 v) {
        return new Vec3(x() * v.x(), y() * v.y(), z() * v.z());
    }

    public final Vec3 scale(float t) {
        return new Vec3(x() * t, y() * t, z() * t);
    }

    public final float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    public final float lengthSquared() {
        return dot(this);
    }

    public final float dot(Vec3 v) {
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
        return scale(1 / length());
    }

    public final Vec3 negate() {
        return scale(-1);
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

    public final Vec3 refract(Vec3 n, float etaiOverEtat) {
        var cosTheta = Math.min(negate().dot(n), 1.0f);
        Vec3 rOutPerp = add(n.scale(cosTheta))
            .scale(etaiOverEtat);
        Vec3 rOutParallel = n.scale((float) -Math.sqrt(Math.abs(1 - rOutPerp.lengthSquared())));
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
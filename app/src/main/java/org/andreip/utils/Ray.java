package org.andreip.utils;

public class Ray {
    private Vec3 origin;
    private Vec3 direction;
    private double time;

    public Ray(Vec3 origin, Vec3 direction) {
        this(origin, direction, 0.0);
    }

    public Ray(Vec3 origin, Vec3 direction, double time) {
        this.origin = origin;
        this.direction = direction;
        this.time = time;
    }

    public Vec3 at(double t) {
        return origin.add(direction.scale(t));
    }

    public Vec3 origin() {
        return origin;
    }

    public Vec3 direction() {
        return direction;
    }

    public double time() {
        return time;
    }

    public Ray set(Ray r) {
        origin = r.origin();
        direction = r.direction();
        time = r.time();
        return this;
    }
}

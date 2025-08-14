package org.andreip.utils;

public class Ray {
    private Vec3 origin;
    private Vec3 direction;

    public Ray(Vec3 origin, Vec3 direction) {
        this.origin = origin;
        this.direction = direction;
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

    public Ray set(Ray r) {
        origin = r.origin();
        direction = r.direction();
        return this;
    }
}

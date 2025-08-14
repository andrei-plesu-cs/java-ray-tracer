package org.andreip.utils;

public class Ray {
    private Vec3 origin;
    private Vec3 direction;
    private float time;

    public Ray(Vec3 origin, Vec3 direction) {
        this(origin, direction, 0);
    }

    public Ray(Vec3 origin, Vec3 direction, float time) {
        this.origin = origin;
        this.direction = direction;
        this.time = time;
    }

    public Vec3 at(float t) {
        return origin.add(direction.scale(t));
    }

    public Vec3 origin() {
        return origin;
    }

    public Vec3 direction() {
        return direction;
    }

    public float time() {
        return time;
    }

    public Ray set(Ray r) {
        origin = r.origin();
        direction = r.direction();
        time = r.time();
        return this;
    }
}

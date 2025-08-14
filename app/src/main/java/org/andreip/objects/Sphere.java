package org.andreip.objects;

import org.andreip.utils.*;
import org.andreip.materials.*;

public class Sphere implements Hittable {
    private final Vec3 center;
    private final double radius;
    private final Material mat;

    public Sphere(Vec3 center, double radius, Material mat) {
        this.center = center;
        this.radius = radius;
        this.mat = mat;
    }

    @Override 
    public boolean hit(Ray r, Interval rayT, HitRecord rec) {
        Vec3 oc = center.subtract(r.origin());
        var a = r.direction().lengthSquared();
        var h = r.direction().dot(oc);
        var c = oc.lengthSquared() - radius * radius;

        var discriminant = h * h - a * c;
        if (discriminant < 0) {
            return false;
        }

        var sqrtd = Math.sqrt(discriminant);

        // Find the nearest root that lies in the acceptable range.
        var root = (h - sqrtd) / a;
        if (!rayT.surrounds(root)) {
            root = (h + sqrtd) / a;
            if (!rayT.surrounds(root))
                return false;
        }

        rec.setT(root);
        rec.setP(r.at(rec.getT()));
        Vec3 outwardNormal = rec.getP().subtract(center).scale(1.0 / radius);
        rec.setFaceNormal(r, outwardNormal);
        rec.setMat(mat);

        return true;
    }
}
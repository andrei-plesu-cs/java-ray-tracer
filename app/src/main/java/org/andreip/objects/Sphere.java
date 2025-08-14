package org.andreip.objects;

import org.andreip.utils.*;
import org.andreip.materials.*;

public class Sphere implements Hittable {
    private final Ray center;
    private final float radius;
    private final Material mat;

    public Sphere(Vec3 staticCenter, float radius, Material mat) {
        this(new Ray(staticCenter, new Vec3(0, 0, 0)), radius, mat);
    }

    public Sphere(Vec3 center1, Vec3 center2, float radius, Material mat) {
        this(new Ray(center1, center2.subtract(center1)), radius, mat);
    }

    private Sphere(Ray center, float radius, Material mat) {
        this.center = center;
        this.radius = radius;
        this.mat = mat;
    }

    @Override 
    public boolean hit(Ray r, Interval rayT, HitRecord rec) {
        Vec3 currentCenter = center.at(r.time());
        Vec3 oc = currentCenter.subtract(r.origin());
        var a = r.direction().lengthSquared();
        var h = r.direction().dot(oc);
        var c = oc.lengthSquared() - radius * radius;

        var discriminant = h * h - a * c;
        if (discriminant < 0) {
            return false;
        }

        var sqrtd = (float) Math.sqrt(discriminant);

        // Find the nearest root that lies in the acceptable range.
        var root = (h - sqrtd) / a;
        if (!rayT.surrounds(root)) {
            root = (h + sqrtd) / a;
            if (!rayT.surrounds(root))
                return false;
        }

        rec.setT(root);
        rec.setP(r.at(rec.getT()));
        Vec3 outwardNormal = rec.getP().subtract(currentCenter).scale(1 / radius);
        rec.setFaceNormal(r, outwardNormal);
        rec.setMat(mat);

        return true;
    }
}
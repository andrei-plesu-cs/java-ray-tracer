package org.andreip.materials;

import org.andreip.utils.*;
import org.andreip.objects.*;

public class Dielectric implements Material {
    // Refractive index in vacuum or air, or the ratio of the material's refractive index over
    // the refractive index of the enclosing media
    private final double refractionIndex;

    public Dielectric(double refractionIndex) {
        this.refractionIndex = refractionIndex;
    }

    @Override
    public boolean scatter(Ray rIn, HitRecord rec, Vec3 attenuation, Ray scattered) {
        attenuation.set(new Vec3(1.0, 1.0, 1.0));
        double ri = rec.getFrontFace() ? (1.0 / refractionIndex) : refractionIndex;

        Vec3 unitDirection = rIn.direction().toUnit();
        double cosTheta = Math.min(unitDirection.negate().dot(rec.getNormal()), 1.0);
        double sinTheta = Math.sqrt(1.0 - cosTheta * cosTheta);

        boolean cannotRefract = (ri * sinTheta) > 1.0;
        Vec3 direction;

        if (cannotRefract || reflectance(cosTheta, ri) > Math.random()) {
            direction = unitDirection.reflect(rec.getNormal());
        } else {
            direction = unitDirection.refract(rec.getNormal(), ri);
        }

        scattered.set(new Ray(rec.getP(), direction));
        return true;
    }

    private static double reflectance(double cosine, double refractionIndex) {
        // Use Schlick's approximation for reflectance.
        var r0 = (1 - refractionIndex) / (1 + refractionIndex);
        r0 = r0 * r0;
        return r0 + (1 - r0) * Math.pow((1 - cosine), 5);
    }
}
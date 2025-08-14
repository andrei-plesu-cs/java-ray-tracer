package org.andreip.materials;

import org.andreip.utils.*;
import org.andreip.objects.*;

public final class Lambertian extends AlbedoMaterial {
    public Lambertian(Vec3 albedo) {
        super(albedo);
    }

    @Override
    public boolean scatter(Ray rIn, HitRecord rec, Vec3 attenuation, Ray scattered) {
        var scatterDirection = rec.getNormal().add(Vec3.randomUnitVector());

        // Catch degenerate scatter direction
        if (scatterDirection.nearZero()) {
            scatterDirection = rec.getNormal();
        }

        scattered.set(new Ray(rec.getP(), scatterDirection));
        attenuation.set(albedo);

        return true;
    }
}
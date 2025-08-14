package org.andreip.materials;

import org.andreip.utils.*;
import org.andreip.objects.*;

public final class Metal extends AlbedoMaterial {
    private final double fuzz;

    public Metal(Vec3 albedo, double fuzz) {
        super(albedo);
        this.fuzz = fuzz;
    }

    @Override
    public boolean scatter(Ray rIn, HitRecord rec, Vec3 attenuation, Ray scattered) {
        Vec3 reflected = rIn.direction().reflect(rec.getNormal());
        reflected = reflected.toUnit().add(Vec3.randomUnitVector().scale(fuzz));
        scattered.set(new Ray(rec.getP(), reflected, rIn.time()));
        attenuation.set(albedo);
        return scattered.direction().dot(rec.getNormal()) > 0;
    }
}
package org.andreip.materials;

import org.andreip.utils.*;

public abstract class AlbedoMaterial implements Material {
    protected final Vec3 albedo;

    public AlbedoMaterial(Vec3 albedo) {
        this.albedo = albedo;
    }
}

package org.andreip.materials;

import org.andreip.utils.*;
import org.andreip.objects.*;

public interface Material {
    public abstract boolean scatter(Ray rIn, HitRecord rec, Vec3 attenuation, Ray scattered);
}
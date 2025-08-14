package org.andreip.objects;

import org.andreip.utils.*;

public interface Hittable {
    boolean hit(Ray r, Interval rayT, HitRecord rec);
}
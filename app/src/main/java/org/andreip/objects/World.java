package org.andreip.objects;

import org.andreip.utils.*;
import java.util.*;

public class World implements Hittable {
    private List<Hittable> objects;

    public World() {
        objects = new ArrayList<>();
    }

    public void clear() {
        objects.clear();
    }

    public boolean add(Hittable object) {
        return objects.add(object);
    }

    @Override
    public boolean hit(Ray r, Interval rayT, HitRecord rec) {
        HitRecord tempRec = new HitRecord();
        boolean hitAnything = false;
        var closestSoFar = rayT.max();

        for (var object : objects) {
            if (object.hit(r, new Interval(rayT.min(), closestSoFar), tempRec)) {
                hitAnything = true;
                closestSoFar = tempRec.getT();
                rec.set(tempRec);
            }
        }

        return hitAnything;
    }
}
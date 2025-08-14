package org.andreip.objects;

import org.andreip.utils.*;
import org.andreip.materials.*;

public class HitRecord {
    private Vec3 p;
    private Vec3 normal;
    private double t;
    private boolean frontFace;
    private Material mat;

    public HitRecord() {
        this(new Vec3(0, 0, 0), new Vec3(0, 0, 0), 0.0, null);
    }

    public HitRecord(Vec3 p, Vec3 normal, double t, Material mat) {
        this.p = p;
        this.normal = normal;
        this.t = t;
        this.mat = mat;
    }

    public Vec3 getP() { 
        return p; 
    }

    public Vec3 getNormal() { 
        return normal; 
    }

    public double getT() { 
        return t; 
    }

    public Material getMat() { 
        return mat; 
    }

    public boolean getFrontFace() {
        return frontFace;
    }

    public void setP(Vec3 p) {
        this.p = p;
    }

    public void setNormal(Vec3 normal) {
        this.normal = normal;
    }

    public void setT(double t) {
        this.t = t;
    }

    public void setMat(Material mat) {
        this.mat = mat;
    }

    public void setFaceNormal(Ray r, Vec3 outwardNormal) {
        // Sets the hit record normal vector.
        // NOTE: the parameter `outward_normal` is assumed to have unit length.

        frontFace = r.direction().dot(outwardNormal) < 0;
        normal = frontFace ? outwardNormal : outwardNormal.negate();
    }

    public void set(HitRecord rec) {
        p = rec.getP();
        normal = rec.getNormal();
        t = rec.getT();
        mat = rec.getMat();
        frontFace = rec.getFrontFace();
    }
}
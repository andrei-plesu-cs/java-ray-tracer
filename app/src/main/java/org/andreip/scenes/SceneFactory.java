package org.andreip.scenes;

import org.andreip.camera.*;
import org.andreip.utils.*;
import org.andreip.materials.*;
import org.andreip.objects.*;
import org.andreip.engines.config.*;

public class SceneFactory {
    private static final TraceConfiguration.Builder DEFAULT_CONFIG = new TraceConfiguration.Builder()
        .aspectRatio(16.0f / 9.0f)
        .imageWidth(600)
        .samplesPerPixel(50)
        .maxDepth(20)
        .vFov(20)
        .lookFrom(new Vec3(13, 2, 3))
        .lookAt(new Vec3(0, 0, 0))
        .vUp(new Vec3(0, 1, 0))
        .defocusAngle(0.6f)
        .focusDist(10);

    private SceneFactory() {
        throw new AssertionError();
    }

    public static Camera getChapter1Scene() {
        var world = new World();

        var groundMaterial = new Lambertian(new Vec3(0.5f, 0.5f, 0.5f));

        world.add(new Sphere(
            new Vec3(0, -1000, 0),
            1000,
            groundMaterial
        ));

        for (int a = -11; a < 11; a++) {
            for (int b = -11; b < 11; b++) {
                var chooseMat = Math.random();
                Vec3 center = new Vec3(a + 0.9f * (float) Math.random(), 0.2f, b + 0.9f * (float) Math.random());

                if (center.subtract(new Vec3(4, 0.2f, 0)).length() > 0.9) {
                    Material sphereMaterial = null;

                    if (chooseMat < 0.8) {
                        // diffuse
                        var albedo = Vec3.random().multiply(Vec3.random());
                        sphereMaterial = new Lambertian(albedo);
                        var center2 = center.add(new Vec3(0, Utilities.randomInRange(0, 0.5f), 0));
                        world.add(new Sphere(center, center2, 0.2f, sphereMaterial));
                    } else if (chooseMat < 0.95f) {
                        // metal
                        var albedo = Vec3.random(0.5f, 1.0f);
                        var fuzz = Utilities.randomInRange(0.0f, 0.5f);
                        sphereMaterial = new Metal(albedo, fuzz);
                        world.add(new Sphere(center, 0.2f, sphereMaterial));
                    } else {
                        // glass
                        sphereMaterial = new Dielectric(1.5f);
                        world.add(new Sphere(center, 0.2f, sphereMaterial));
                    }
                }
            }
        }

        var material1 = new Dielectric(1.5f);
        world.add(new Sphere(
            new Vec3(0, 1, 0),
            1,
            material1
        ));

        var material2 = new Lambertian(new Vec3(0.4f, 0.2f, 0.1f));
        world.add(new Sphere(
            new Vec3(-4, 1, 0),
            1,
            material2
        ));

        var material3 = new Metal(new Vec3(0.7f, 0.6f, 0.5f), 0);
        world.add(new Sphere(
            new Vec3(4, 1, 0),
            1,
            material3
        ));

        var camera = new Camera(
            DEFAULT_CONFIG.world(world).build(),
            Camera.EngineType.CPU_PARALLEL
        );

        return camera;
    }
}

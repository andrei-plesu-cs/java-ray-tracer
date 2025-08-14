package org.andreip.scenes;

import org.andreip.camera.*;
import org.andreip.utils.*;
import org.andreip.materials.*;
import org.andreip.objects.*;
import org.andreip.engines.config.*;

public class SceneFactory {
    private static final TraceConfiguration.Builder DEFAULT_CONFIG = new TraceConfiguration.Builder()
        .aspectRatio(16.0 / 9.0)
        .imageWidth(800)
        .samplesPerPixel(50)
        .maxDepth(20)
        .vFov(20)
        .lookFrom(new Vec3(13, 2, 3))
        .lookAt(new Vec3(0, 0, 0))
        .vUp(new Vec3(0, 1, 0))
        .defocusAngle(0.6)
        .focusDist(10);

    private SceneFactory() {
        throw new AssertionError();
    }

    public static Camera getChapter1Scene() {
        var world = new World();

        var groundMaterial = new Lambertian(new Vec3(0.5, 0.5, 0.5));

        world.add(new Sphere(
            new Vec3(0, -1000, 0),
            1000,
            groundMaterial
        ));

        for (int a = -11; a < 11; a++) {
            for (int b = -11; b < 11; b++) {
                var chooseMat = Math.random();
                Vec3 center = new Vec3(a + 0.9 * Math.random(), 0.2, b + 0.9 * Math.random());

                if (center.subtract(new Vec3(4, 0.2, 0)).length() > 0.9) {
                    Material sphereMaterial = null;

                    if (chooseMat < 0.8) {
                        // diffuse
                        var albedo = Vec3.random().multiply(Vec3.random());
                        sphereMaterial = new Lambertian(albedo);
                    } else if (chooseMat < 0.95) {
                        // metal
                        var albedo = Vec3.random(0.5, 1.0);
                        var fuzz = Utilities.randomInRange(0.0, 0.5);
                        sphereMaterial = new Metal(albedo, fuzz);
                    } else {
                        // glass
                        sphereMaterial = new Dielectric(1.5);
                    }

                    world.add(new Sphere(center, 0.2, sphereMaterial));
                }
            }
        }

        var material1 = new Dielectric(1.5);
        world.add(new Sphere(
            new Vec3(0, 1, 0),
            1.0,
            material1
        ));

        var material2 = new Lambertian(new Vec3(0.4, 0.2, 0.1));
        world.add(new Sphere(
            new Vec3(-4, 1, 0),
            1.0,
            material2
        ));

        var material3 = new Metal(new Vec3(0.7, 0.6, 0.5), 0.0);
        world.add(new Sphere(
            new Vec3(4, 1, 0),
            1.0,
            material3
        ));

        var camera = new Camera(
            DEFAULT_CONFIG.world(world).build(),
            Camera.EngineType.CPU_PARALLEL
        );

        return camera;
    }
}

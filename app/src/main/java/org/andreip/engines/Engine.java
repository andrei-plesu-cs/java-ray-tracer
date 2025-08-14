package org.andreip.engines;

import java.util.*;
import org.andreip.utils.*;
import org.andreip.objects.*;
import org.andreip.camera.*;
import org.andreip.engines.config.*;
import static org.andreip.utils.Utilities.*;

public abstract class Engine {
    protected final double viewportHeight;
    protected final double viewportWidth;
    protected final Vec3 viewportU;
    protected final Vec3 viewportV;
    protected final Vec3 pixelDeltaU;
    protected final Vec3 pixelDeltaV;
    protected final Vec3 viewportUpperLeft;
    protected final Vec3 pixel00Loc;
    protected final double pixelSamplesScale;
    protected final Vec3 u, v, w;
    protected final Vec3 center;
    protected final Vec3 defocusDiskU;
    protected final Vec3 defocusDiskV;
    protected final TraceConfiguration traceConfig;
    protected final ProgressIndicator progressIndicator;

    public Engine(TraceConfiguration traceConfig) {
        this.traceConfig = traceConfig;

        if (traceConfig.getWorld() == null) {
            throw new IllegalArgumentException("Scene objects need to be set, parameter world.");
        }

        progressIndicator = new ProgressIndicator(traceConfig.getImageHeight());
        progressIndicator.setDaemon(true); // Mark as daemon so the thread quits when rendering is done

        // Compute image height
        pixelSamplesScale = 1.0 / traceConfig.getSamplesPerPixel();

        center = traceConfig.getLookFrom();

        // Determine viewport dimensions.
        var theta = degreesToRadians(traceConfig.getVFov());
        var h = Math.tan(theta / 2);
        viewportHeight = 2 * h * traceConfig.getFocusDist();
        viewportWidth = viewportHeight * ( (double) traceConfig.getImageWidth() / traceConfig.getImageHeight() );

        // Calculate the u,v,w unit basis vectors for the camera coordinate frame.
        w = traceConfig.getLookFrom().subtract(traceConfig.getLookAt()).toUnit();
        u = traceConfig.getVUp().cross(w).toUnit();
        v = w.cross(u);

        // Calculate the vectors across the horizontal and down the vertical viewport edges.
        viewportU = u.scale(viewportWidth);
        viewportV = v.scale(-viewportHeight);

        // Calculate the horizontal and vertical delta vectors from pixel to pixel.
        pixelDeltaU = viewportU.scale(1.0 / traceConfig.getImageWidth());
        pixelDeltaV = viewportV.scale(1.0 / traceConfig.getImageHeight());

        // Calculate the location of the upper left pixel.
        viewportUpperLeft = center
            .subtract(w.scale(traceConfig.getFocusDist()))
            .subtract(viewportU.scale(1.0 / 2.0))
            .subtract(viewportV.scale(1.0 / 2.0));
        
        pixel00Loc = viewportUpperLeft.add(pixelDeltaU
            .add(pixelDeltaV)
            .scale(0.5));

        // Calculate the camera defocus disk basis vectors.
        var defocusRadius = traceConfig.getFocusDist() * Math.tan(degreesToRadians(traceConfig.getDefocusAngle() / 2));
        defocusDiskU = u.scale(defocusRadius);
        defocusDiskV = v.scale(defocusRadius);
    }

    public abstract List<Pixel> trace();

    protected Vec3 rayColor(Ray r, int depth, World world) {
        // If we've exceeded the ray bounce limit, no more light is gathered.
        if (depth <= 0) {
            return new Vec3(0, 0, 0);
        }

        HitRecord rec = new HitRecord();
        if (world.hit(r, new Interval(0.001, Double.MAX_VALUE), rec)) {
            Ray scattered = new Ray(new Vec3(0, 0, 0), new Vec3(0, 0, 0));
            Vec3 attenuation = new Vec3(0, 0, 0);

            if (rec.getMat().scatter(r, rec, attenuation, scattered)) {
                return attenuation
                    .multiply(rayColor(scattered, depth - 1, world));
            }

            return new Vec3(0, 0, 0);
        }

        Vec3 unitDirection = r.direction().toUnit();
        var a = 0.5 * (unitDirection.y() + 1.0);

        return new Vec3(1, 1, 1)
            .scale(1.0 - a)
            .add(new Vec3(0.5, 0.7, 1.0)
                .scale(a));
    }

    protected Ray getRay(int i, int j) {
        // Construct a camera ray originating from the defocus disk and directed at a randomly
        // sampled point around the pixel location i, j.

        var offset = sampleSquare();
        var pixelSample = pixel00Loc
            .add(pixelDeltaU.scale(i + offset.x()))
            .add(pixelDeltaV.scale(j + offset.y()));

        var rayOrigin = (traceConfig.getDefocusAngle() <= 0) ? center : defocusDiskSample();
        var rayDirection = pixelSample.subtract(rayOrigin);
        var rayTime = Math.random();

        return new Ray(rayOrigin, rayDirection, rayTime);
    }

    protected Vec3 sampleSquare() {
        // Returns the vector to a random point in the [-.5,-.5]-[+.5,+.5] unit square.
        return new Vec3(Math.random() - 0.5, Math.random() - 0.5, 0);
    }

    protected Vec3 defocusDiskSample() {
        // Returns a random point in the camera defocus disk.
        var p = Vec3.randomInUnitDisc();
        return center
            .add(defocusDiskU.scale(p.x()))
            .add(defocusDiskV.scale(p.y()));
    }
}

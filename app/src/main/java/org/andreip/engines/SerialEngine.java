package org.andreip.engines;

import java.util.*;
import org.andreip.utils.*;
import org.andreip.camera.*;
import org.andreip.engines.config.*;

public class SerialEngine extends Engine {
    public SerialEngine(TraceConfiguration traceConfig) {
        super(traceConfig);
    }

    public List<Pixel> trace() {
        progressIndicator.start();

        var pixels = new ArrayList<Pixel>();
        for (int j = 0; j < traceConfig.getImageHeight(); j++) {
            progressIndicator.update(j);
            for (int i = 0; i < traceConfig.getImageWidth(); i++) {
                Vec3 pixelColor = new Vec3(0, 0, 0);
                for (int sample = 0; sample < traceConfig.getSamplesPerPixel(); sample++) {
                    Ray r = getRay(i, j);
                    pixelColor = pixelColor.add(rayColor(r, traceConfig.getMaxDepth(), traceConfig.getWorld()));
                }
                pixels.add(pixelColor.scale(pixelSamplesScale).toPixel());
            }
        }
        return pixels;
    }
}

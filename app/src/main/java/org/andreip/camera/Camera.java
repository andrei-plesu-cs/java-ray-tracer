package org.andreip.camera;

import org.andreip.utils.*;
import java.util.function.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
import org.andreip.objects.*;
import org.andreip.materials.*;
import org.andreip.engines.config.*;
import org.andreip.engines.*;

public final class Camera {
    private final TraceConfiguration traceConfig;
    private final Engine engine;

    public static enum EngineType {
        SERIAL, CPU_PARALLEL
    }

    public Camera(TraceConfiguration traceConfig, EngineType engineType) {
        this.traceConfig = traceConfig;
        this.engine = switch(engineType) {
            case SERIAL -> new SerialEngine(traceConfig);
            case CPU_PARALLEL -> new CPUParallelEngine(traceConfig);
        };
    }

    public void render() throws IOException {
        var pixels = engine.trace();

        writeImage(pixels, traceConfig.getImageWidth(), traceConfig.getImageHeight());
    }

    private void writeImage(List<Pixel> pixels, int width, int height) throws IOException {
        Path path = Paths.get("output");
        Files.createDirectories(path);
        path = path.resolve("image.ppm");
        Files.deleteIfExists(path);
        Files.writeString(path, String.format("P3\n%d %d\n255\n", width, height));
        List<String> stringPixels = pixels.stream()
            .map(Pixel::getStringLine)
            .collect(Collectors.toList());
        Files.write(path, stringPixels, StandardOpenOption.APPEND);
    }
}

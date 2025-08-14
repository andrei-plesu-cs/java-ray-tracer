package org.andreip.engines;

import java.util.*;
import org.andreip.utils.*;
import org.andreip.camera.*;
import org.andreip.engines.config.*;
import java.util.concurrent.*;

public class CPUParallelEngine extends Engine {
    private final int noOfThreads = 8;
    
    public CPUParallelEngine(TraceConfiguration traceConfig) {
        super(traceConfig);
    }

    private class Worker implements Callable<List<Pixel>> {
        private final int threadIdx;

        public Worker(int threadIdx) {
            this.threadIdx = threadIdx;
        }

        @Override
        public List<Pixel> call() {
            var pixels = new ArrayList<Pixel>();

            int chunkSize = (int) Math.ceil((double) traceConfig.getImageHeight() / noOfThreads);
            int start = threadIdx * chunkSize;
            int end = Math.min(traceConfig.getImageHeight(), start + chunkSize);

            for (int j = start; j < end; j++) {
                progressIndicator.increment();
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

    public List<Pixel> trace() {
        progressIndicator.start();

        var pixels = new ArrayList<Pixel>();
    
        var service = Executors.newFixedThreadPool(noOfThreads);
        
        try {
            var threadList = new ArrayList<Worker>();
            for (int i=0; i<noOfThreads; i++) {
                threadList.add(new Worker(i));
            }
            var result = service.invokeAll(threadList);
            for (var item: result) {
                pixels.addAll(item.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            service.shutdown();
        }

        try {
            service.awaitTermination(20, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!service.isTerminated()) {
            throw new IllegalStateException("Job not done in 20 minutes");
        }

        return pixels;
    }
}

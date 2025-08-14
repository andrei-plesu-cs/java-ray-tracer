package org.andreip.engines.config;

import org.andreip.utils.*;
import org.andreip.objects.*;

public final class TraceConfiguration {
    private final int imageWidth;
    private final int imageHeight;
    private final double aspectRatio;
    private final int samplesPerPixel;
    private final int maxDepth;
    private final double vFov;
    private final Vec3 lookFrom;
    private final Vec3 lookAt;
    private final Vec3 vUp;
    private final double defocusAngle;
    private final double focusDist;
    private final World world;

    private TraceConfiguration(Builder builder) {
        imageWidth = builder.imageWidth;
        aspectRatio = builder.aspectRatio;
        samplesPerPixel = builder.samplesPerPixel;
        maxDepth = builder.maxDepth;
        vFov = builder.vFov;
        world = builder.world;
        lookFrom = builder.lookFrom;
        lookAt = builder.lookAt;
        vUp = builder.vUp;
        defocusAngle = builder.defocusAngle;
        focusDist = builder.focusDist;
        imageHeight = builder.imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public double getAspectRatio() {
        return aspectRatio;
    }

    public int getSamplesPerPixel() {
        return samplesPerPixel;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public double getVFov() {
        return vFov;
    }

    public World getWorld() {
        return world;
    }

    public Vec3 getLookFrom() {
        return lookFrom;
    }

    public Vec3 getLookAt() {
        return lookAt;
    }

    public Vec3 getVUp() {
        return vUp;
    }

    public double getDefocusAngle() {
        return defocusAngle;
    }

    public double getFocusDist() {
        return focusDist;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public static class Builder {
        private int imageWidth = 400;
        private int imageHeight;
        private double aspectRatio = 16.0 / 9.0;
        private int samplesPerPixel = 10;
        private int maxDepth = 10;
        private double vFov = 90.0;
        private Vec3 lookFrom = new Vec3(0, 0, 0);
        private Vec3 lookAt = new Vec3(0, 0, -1);
        private Vec3 vUp = new Vec3(0, 1, 0);
        private double defocusAngle = 0;
        private double focusDist = 10;
        private World world;

        public Builder imageWidth(int imageWidth) {
            this.imageWidth = imageWidth; return this;
        }

        public Builder aspectRatio(double aspectRatio) {
            this.aspectRatio = aspectRatio; return this;
        }

        public Builder samplesPerPixel(int samplesPerPixel) {
            this.samplesPerPixel = samplesPerPixel; return this;
        }

        public Builder maxDepth(int maxDepth) {
            this.maxDepth = maxDepth; return this;
        }

        public Builder world(World world) {
            this.world = world; return this;
        }

        public Builder vFov(int vFov) {
            this.vFov = vFov; return this;
        }

        public Builder lookFrom(Vec3 lookFrom) {
            this.lookFrom = lookFrom; return this;
        }

        public Builder lookAt(Vec3 lookAt) {
            this.lookAt = lookAt; return this;
        }

        public Builder vUp(Vec3 vUp) {
            this.vUp = vUp; return this;
        }

        public Builder defocusAngle(double defocusAngle) {
            this.defocusAngle = defocusAngle; return this;
        }

        public Builder focusDist(double focusDist) {
            this.focusDist = focusDist; return this;
        }

        public TraceConfiguration build() {
            imageHeight = Math.max(1, (int) (imageWidth / aspectRatio));

            return new TraceConfiguration(this);
        }
    }
}
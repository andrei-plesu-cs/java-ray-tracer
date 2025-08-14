package org.andreip.engines.config;

import org.andreip.utils.*;
import org.andreip.objects.*;

public final class TraceConfiguration {
    private final int imageWidth;
    private final int imageHeight;
    private final float aspectRatio;
    private final int samplesPerPixel;
    private final int maxDepth;
    private final float vFov;
    private final Vec3 lookFrom;
    private final Vec3 lookAt;
    private final Vec3 vUp;
    private final float defocusAngle;
    private final float focusDist;
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

    public float getAspectRatio() {
        return aspectRatio;
    }

    public int getSamplesPerPixel() {
        return samplesPerPixel;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public float getVFov() {
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

    public float getDefocusAngle() {
        return defocusAngle;
    }

    public float getFocusDist() {
        return focusDist;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public static class Builder {
        private int imageWidth = 400;
        private int imageHeight;
        private float aspectRatio = 16.0f / 9.0f;
        private int samplesPerPixel = 10;
        private int maxDepth = 10;
        private float vFov = 90.0f;
        private Vec3 lookFrom = new Vec3(0, 0, 0);
        private Vec3 lookAt = new Vec3(0, 0, -1);
        private Vec3 vUp = new Vec3(0, 1, 0);
        private float defocusAngle = 0;
        private float focusDist = 10;
        private World world;

        public Builder imageWidth(int imageWidth) {
            this.imageWidth = imageWidth; return this;
        }

        public Builder aspectRatio(float aspectRatio) {
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

        public Builder defocusAngle(float defocusAngle) {
            this.defocusAngle = defocusAngle; return this;
        }

        public Builder focusDist(float focusDist) {
            this.focusDist = focusDist; return this;
        }

        public TraceConfiguration build() {
            imageHeight = Math.max(1, (int) (imageWidth / aspectRatio));

            return new TraceConfiguration(this);
        }
    }
}
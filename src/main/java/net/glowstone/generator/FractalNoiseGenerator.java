package net.glowstone.generator;

import java.util.Random;

import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.OctaveGenerator;

public class FractalNoiseGenerator extends OctaveGenerator {
    private final int xSize;
    private final int ySize;
    private final int zSize;
    private double[] noise;

    public FractalNoiseGenerator(Random rand, int octaves, int xSize, int zSize) {
        this(rand, octaves, xSize, 1, zSize);
    }

    public FractalNoiseGenerator(Random rand, int octaves, int xSize, int ySize, int zSize) {
        super(createOctaves(rand, octaves));
        this.xSize = xSize;
        this.ySize = ySize;
        this.zSize = zSize;
        noise = new double[xSize * ySize * zSize];
    }

    public double[] fBm(double x, double z, double lacunarity, double persistence) {
        return fBm(x, 0, z, lacunarity, persistence);
    }

    public double[] fBm(double x, double y, double z, double lacunarity, double persistence) {
        for (int i = 0; i < noise.length; i++) {
            noise[i] = 0;
        }

        double freq = 1;
        double amp = 1;

        x = x * xScale;
        y = y * yScale;
        z = z * zScale;

        // fBm
        // the noise is periodic over x and z axis
        for (NoiseGenerator octave : octaves) {
            double dX = x * freq;
            double dY = y * freq;
            double dZ = z * freq;
            // compute integer part
            long lX = floor(dX);
            long lZ = floor(dZ);
            // compute fractional part
            dX -= lX;
            dZ -= lZ;
            // wrap integer part to 0..16777216
            lX %= 16777216;
            lZ %= 16777216;
            // add to fractional part
            dX += lX;
            dZ += lZ;

            noise = ((PerlinNoiseGenerator) octave).getNoise(noise, dX, dY, dZ, xSize, ySize, zSize, xScale * freq, yScale * freq, zScale * freq, amp);
            freq *= lacunarity;
            amp *= persistence;
        }

        return noise;
    }

    private static NoiseGenerator[] createOctaves(Random rand, int octaves) {
        NoiseGenerator[] result = new NoiseGenerator[octaves];

        for (int i = 0; i < octaves; i++) {
            result[i] = new PerlinNoiseGenerator(rand);
        }

        return result;
    }

    private static long floor(double x) {
        return x >= 0 ? (long) x : (long) x - 1;
    }
}

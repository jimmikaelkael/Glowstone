package net.glowstone.generator;

import java.util.Random;

public class PerlinNoiseGenerator extends org.bukkit.util.noise.PerlinNoiseGenerator {

    public PerlinNoiseGenerator(Random rand) {
        super(rand);
    }

    public double[] getNoise(double[] noise, double x, double y, double z, int sizeX, int sizeY, int sizeZ, double scaleX, double scaleY, double scaleZ, double amplitude) {
        if (sizeY == 1) {
            return get2dNoise(noise, x, z, sizeX, sizeZ, scaleX, scaleZ, amplitude);
        } else {
            return get3dNoise(noise, x, y, z, sizeX, sizeY, sizeZ, scaleX, scaleY, scaleZ, amplitude);
        }
    }

    public double[] get2dNoise(double[] noise, double x, double z, int sizeX, int sizeZ, double scaleX, double scaleZ, double amplitude) {
        int index = 0;
        for (int i = 0; i < sizeX; i++) {
            double dX = x + offsetX + i * scaleX;
            int floorX = floor(dX);
            int iX = floorX & 255;
            dX -= floorX;
            double fX = fade(dX);
            for (int j = 0; j < sizeZ; j++) {
                double dZ = z + offsetZ + j * scaleZ;
                int floorZ = floor(dZ);
                int iZ = floorZ & 255;
                dZ -= floorZ;
                double fZ = fade(dZ);
                // Hash coordinates of the square corners
                int a = perm[iX] + 0;
                int aa = perm[a] + iZ;
                int b = perm[iX + 1] + 0;
                int ba = perm[b] + iZ;
                double x1 = lerp(fX, grad(perm[aa], dX, 0, dZ), grad(perm[ba], dX - 1, 0, dZ));
                double x2 = lerp(fX, grad(perm[aa + 1], dX, 0, dZ - 1), grad(perm[ba + 1], dX - 1, 0, dZ - 1));
                noise[index++] += lerp(fZ, x1, x2) * amplitude;
            }
        }
        return noise;
    }

    public double[] get3dNoise(double[] noise, double x, double y, double z, int sizeX, int sizeY, int sizeZ, double scaleX, double scaleY, double scaleZ, double amplitude) {
        int n = -1;
        double x1 = 0;
        double x2 = 0;
        double x3 = 0;
        double x4 = 0;
        int index = 0;
        for (int i = 0; i < sizeX; i++) {
            double dX = x + offsetX + i * scaleX;
            int floorX = floor(dX);
            int iX = floorX & 255;
            dX -= floorX;
            double fX = fade(dX);
            for (int j = 0; j < sizeZ; j++) {
                double dZ = z + offsetZ + j * scaleZ;
                int floorZ = floor(dZ);
                int iZ = floorZ & 255;
                dZ -= floorZ;
                double fZ = fade(dZ);
                for (int k = 0; k < sizeY; k++) {
                    double dY = y + offsetY + k * scaleY;
                    int floorY = floor(dY);
                    int iY = floorY & 255;
                    dY -= floorY;
                    double fY = fade(dY);
                    if (k == 0 || iY != n) {
                        n = iY;
                        // Hash coordinates of the cube corners
                        int a = perm[iX] + iY;
                        int aa = perm[a] + iZ;
                        int ab = perm[a + 1] + iZ;
                        int b = perm[iX + 1] + iY;
                        int ba = perm[b] + iZ;
                        int bb = perm[b + 1] + iZ;
                        x1 = lerp(fX, grad(perm[aa], dX, dY, dZ), grad(perm[ba], dX - 1, dY, dZ));
                        x2 = lerp(fX, grad(perm[ab], dX, dY - 1, dZ), grad(perm[bb], dX - 1, dY - 1, dZ));
                        x3 = lerp(fX, grad(perm[aa + 1], dX, dY, dZ - 1), grad(perm[ba + 1], dX - 1, dY, dZ - 1));
                        x4 = lerp(fX, grad(perm[ab + 1], dX, dY - 1, dZ - 1), grad(perm[bb + 1], dX - 1, dY - 1, dZ - 1));
                    }
                    double y1 = lerp(fY, x1, x2);
                    double y2 = lerp(fY, x3, x4);

                    noise[index++] += lerp(fZ, y1, y2) * amplitude;
                }
            }
        }
        return noise;
    }
}

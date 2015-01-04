package net.glowstone.generator.ground;

import java.util.Random;

import org.bukkit.Material;

public class GroundGenerator {

    private Material topMaterial;
    private Material groundMaterial;

    public GroundGenerator() {
        setTopMaterial(Material.GRASS);
        setGroundMaterial(Material.DIRT);
    }

    public void generateTerrainColumn(byte[][] buf, Random random, int x, int z, int seaLevel, double surfaceNoise) {

        Material topMat = topMaterial;
        Material groundMat = groundMaterial;

        int surfaceHeight = (int) (surfaceNoise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        int deep = -1;
        for (int y = 255; y >= 0; y--) {
            if (y <= random.nextInt(5)) {
                set(buf, x, y, z, Material.BEDROCK);
            } else {
                Material mat = get(buf, x, y, z);
                if (mat == Material.AIR) {
                    deep = -1;
                } else if (mat == Material.STONE) {
                    if (deep == -1) {
                        if (surfaceHeight <= 0) {
                            topMat = Material.AIR;
                            groundMat = Material.STONE;
                        } else if (y >= seaLevel - 5 && y <= seaLevel) {
                            topMat = topMaterial;
                            groundMat = groundMaterial;
                        }
                        if (y < seaLevel - 1 && topMat == Material.AIR) {
                            topMat = Material.STATIONARY_WATER;
                        }

                        deep = surfaceHeight;
                        if (y >= seaLevel - 2) {
                            set(buf, x, y, z, topMat);
                        } else if (y < seaLevel - 8 - surfaceHeight) {
                            topMat = Material.AIR;
                            groundMat = Material.STONE;
                            set(buf, x, y, z, Material.GRAVEL);
                        } else {
                            set(buf, x, y, z, groundMat);
                        }
                    } else if (deep > 0) {
                        deep--;
                        set(buf, x, y, z, groundMat);

                        if (deep == 0 && groundMat == Material.SAND) {
                            deep = random.nextInt(4) + Math.max(0, y - seaLevel - 1);
                            groundMat = Material.SANDSTONE;
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    protected final Material get(byte[][] buf, int x, int y, int z) {
        if (buf[y >> 4] == null) {
            return Material.AIR;
        }
        return Material.getMaterial(buf[y >> 4][((y & 0xF) << 8) | (z << 4) | x]);
    }

    @SuppressWarnings("deprecation")
    protected final void set(byte[][] buf, int x, int y, int z, Material id) {
        if (id.getId() == 0) {
            return;
        }
        if (buf[y >> 4] == null) {
            buf[y >> 4] = new byte[4096];
        }
        buf[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = (byte) id.getId();
    }

    protected final void setTopMaterial(Material topMaterial) {
        this.topMaterial = topMaterial;
    }

    protected final void setGroundMaterial(Material groundMaterial) {
        this.groundMaterial = groundMaterial;
    }
}

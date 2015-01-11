package net.glowstone.generator;

import net.glowstone.GlowWorld;
import net.glowstone.constants.GlowBiome;
import net.glowstone.generator.ground.*;
import net.glowstone.generator.populators.*;
import net.glowstone.generator.populators.overworld.*;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.util.noise.OctaveGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.bukkit.block.Biome.*;

/**
 * Basic generator with lots of hills.
 */
public class SurfaceGenerator extends GlowChunkGenerator {

    private static final double COORDINATE_SCALE = 684.412D;   // coordinateScale
    private static final double HEIGHT_SCALE = 684.412D;       // heightScale
    private static final double HEIGHT_NOISE_SCALE_X = 200.0D; // depthNoiseScaleX
    private static final double HEIGHT_NOISE_SCALE_Z = 200.0D; // depthNoiseScaleZ
    private static final double DETAIL_NOISE_SCALE_X = 80.0D;  // mainNoiseScaleX
    private static final double DETAIL_NOISE_SCALE_Y = 160.0D; // mainNoiseScaleY
    private static final double DETAIL_NOISE_SCALE_Z = 80.0D;  // mainNoiseScaleZ
    private static final double SURFACE_SCALE = 0.033666667D;
    private static final double BASE_SIZE = 8.5D;              // baseSize
    private static final double STRETCH_Y = 12.0D;             // stretchY
    private static final double BIOME_HEIGHT_OFFSET = 0.0D;    // biomeDepthOffset
    private static final double BIOME_HEIGHT_WEIGHT = 1.0D;    // biomeDepthWeight
    private static final double BIOME_SCALE_OFFSET = 0.0D;     // biomeScaleOffset
    private static final double BIOME_SCALE_WEIGHT = 1.0D;     // biomeScaleWeight
    private static final int SEA_LEVEL = 64;
    private static final double[] ELEVATION_WEIGHT = new double[5 * 5];
    private static final Map<Biome, GroundGenerator> GROUND_MAP = new HashMap<>();
    private static final Map<Biome, BiomeHeight> HEIGHT_MAP = new HashMap<>();

    private final double[] density = new double[5 * 33 * 5];
    private final GroundGenerator groundGen = new GroundGenerator();
    private final BiomeHeight defaultHeight = BiomeHeight.DEFAULT;

    public SurfaceGenerator() {
        super(new OverworldPopulator(),
              new SnowPopulator());
    }

    @Override
    public short[][] generateExtBlockSectionsWithData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomes) {
        final short[][] buf = generateRawTerrain(world, chunkX, chunkZ);

        final OctaveGenerator noiseSurface = getWorldOctaves(world).get("surface");
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                double surfaceNoise = noiseSurface.noise((chunkX << 4) + x, (chunkZ << 4) + z, 0.85D, 1.49998D);
                if (GROUND_MAP.containsKey(biomes.getBiome(x, z))) {
                    GROUND_MAP.get(biomes.getBiome(x, z)).generateTerrainColumn(buf, random, x, z, SEA_LEVEL, surfaceNoise);
                } else {
                    groundGen.generateTerrainColumn(buf, random, x, z, SEA_LEVEL, surfaceNoise);
                }
            }
        }
        return buf;
    }

    @Override
    public boolean canSpawn(World world, int x, int z) {
        final Block block = world.getHighestBlockAt(x, z).getRelative(BlockFace.DOWN);
        return block.getType() == Material.GRASS;
    }

    @Override
    protected void createWorldOctaves(World world, Map<String, OctaveGenerator> octaves) {
        final Random seed = new Random(world.getSeed());

        OctaveGenerator gen = new FractalNoiseGenerator(seed, 16, 5, 5);
        gen.setXScale(HEIGHT_NOISE_SCALE_X);
        gen.setZScale(HEIGHT_NOISE_SCALE_Z);
        octaves.put("height", gen);

        gen = new FractalNoiseGenerator(seed, 16, 5, 33, 5);
        gen.setXScale(COORDINATE_SCALE);
        gen.setYScale(HEIGHT_SCALE);
        gen.setZScale(COORDINATE_SCALE);
        octaves.put("roughness", gen);

        gen = new FractalNoiseGenerator(seed, 16, 5, 33, 5);
        gen.setXScale(COORDINATE_SCALE);
        gen.setYScale(HEIGHT_SCALE);
        gen.setZScale(COORDINATE_SCALE);
        octaves.put("roughness2", gen);

        gen = new FractalNoiseGenerator(seed, 8, 5, 33, 5);
        gen.setXScale(COORDINATE_SCALE / DETAIL_NOISE_SCALE_X);
        gen.setYScale(HEIGHT_SCALE / DETAIL_NOISE_SCALE_Y);
        gen.setZScale(COORDINATE_SCALE / DETAIL_NOISE_SCALE_Z);
        octaves.put("detail", gen);

        gen = new SimplexOctaveGenerator(seed, 4);
        gen.setScale(SURFACE_SCALE);
        octaves.put("surface", gen);
    }

    @SuppressWarnings("deprecation")
    private void set(short[][] buf, int x, int y, int z, Material id) {
        if (buf[y >> 4] == null) {
            buf[y >> 4] = new short[4096];
        }
        buf[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = (short) (id.getId() << 4);
    }

    private short[][] generateRawTerrain(World world, int chunkX, int chunkZ) {
        generateTerrainDensity(world, chunkX * 4, chunkZ * 4);

        final short[][] buf = new short[16][];

        for (int i = 0; i < 5 - 1; i++) {
            for (int j = 0; j < 5 - 1; j++) {
                for (int k = 0; k < 33 - 1; k++) {
                    double d1 = density[k + (j + i * 5) * 33];
                    double d2 = density[k + (j + (i + 1) * 5) * 33];
                    double d3 = density[k + (j + 1 + i * 5) * 33];
                    double d4 = density[k + (j + 1 + (i + 1) * 5) * 33];
                    double d5 = (density[k + 1 + (j + i * 5) * 33] - d1) / 8.0D;
                    double d6 = (density[k + 1 + (j + (i + 1) * 5) * 33] - d2) / 8.0D;
                    double d7 = (density[k + 1 + (j + 1 + i * 5) * 33] - d3) / 8.0D;
                    double d8 = (density[k + 1 + (j + 1 + (i + 1) * 5) * 33] - d4) / 8.0D;

                    for (int l = 0; l < 8; l++) {
                        double d9 = d1;
                        double d10 = d3;
                        for (int m = 0; m < 4; m++) {
                            double finalDensity = d9;
                            for (int n = 0; n < 4; n++) {
                                if (finalDensity > 0) {
                                    set(buf, m + i * 4, k * 8 + l, j * 4 + n, Material.STONE);
                                } else if (k * 8 + l < SEA_LEVEL - 1) {
                                    set(buf, m + i * 4, k * 8 + l, j * 4 + n, Material.STATIONARY_WATER);
                                }
                                finalDensity += (d10 - d9) / 4.0D;
                            }
                            d9 += (d2 - d1) / 4.0D;
                            d10 += (d4 - d3) / 4.0D;
                        }
                        d1 += d5;
                        d3 += d7;
                        d2 += d6;
                        d4 += d8;
                    }
                }
            }
        }

        return buf;
    }

    private void generateTerrainDensity(World world, int x, int z) {
        final Map<String, OctaveGenerator> octaves = getWorldOctaves(world);
        final double[] heightNoise = ((FractalNoiseGenerator) octaves.get("height")).fBm(x, z, 0.5D, 2.0D);
        final double[] roughnessNoise = ((FractalNoiseGenerator) octaves.get("roughness")).fBm(x, 0, z, 0.5D, 2.0D);
        final double[] roughnessNoise2 = ((FractalNoiseGenerator) octaves.get("roughness2")).fBm(x, 0, z, 0.5D, 2.0D);
        final double[] detailNoise = ((FractalNoiseGenerator) octaves.get("detail")).fBm(x, 0, z, 0.5D, 2.0D);

        final int[] biomeData = ((GlowWorld) world).getChunkManager().getRoughBiomeMap(x - 2, z - 2, 10, 10);
        final WorldType type = world.getWorldType();

        int index = 0;
        int indexHeight = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                double avgHeightScale = 0;
                double avgHeightBase = 0;
                double totalWeight = 0;
                final Biome biome = GlowBiome.getBiome(biomeData[i + 2 + (j + 2) * 10]);
                final BiomeHeight biomeHeight = HEIGHT_MAP.containsKey(biome) ? HEIGHT_MAP.get(biome) : defaultHeight;
                for (int m = 0; m < 5; m++) {
                    for (int n = 0; n < 5; n++) {
                        final Biome nearBiome = GlowBiome.getBiome(biomeData[i + m + (j + n) * 10]);
                        final BiomeHeight nearBiomeHeight = HEIGHT_MAP.containsKey(nearBiome) ? HEIGHT_MAP.get(nearBiome) : defaultHeight;
                        double heightBase = BIOME_HEIGHT_OFFSET + nearBiomeHeight.getHeight() * BIOME_HEIGHT_WEIGHT;
                        double heightScale = BIOME_SCALE_OFFSET + nearBiomeHeight.getScale() * BIOME_SCALE_WEIGHT;
                        if (type == WorldType.AMPLIFIED && heightBase > 0) {
                            heightBase = 1.0D + heightBase * 2.0D;
                            heightScale = 1.0D + heightScale * 4.0D;
                        }
                        double weight = ELEVATION_WEIGHT[m + n * 5] / (heightBase + 2.0D);
                        if (nearBiomeHeight.getHeight() > biomeHeight.getHeight()) {
                            weight *= 0.5D;
                        }
                        avgHeightScale += heightScale * weight;
                        avgHeightBase += heightBase * weight;
                        totalWeight += weight;
                    }
                }
                avgHeightScale /= totalWeight;
                avgHeightBase /= totalWeight;
                avgHeightScale = avgHeightScale * 0.9D + 0.1D;
                avgHeightBase = (avgHeightBase * 4.0D - 1.0D) / 8.0D;

                double noiseH = heightNoise[indexHeight++] / 8000.0D;
                if (noiseH < 0) {
                    noiseH = Math.abs(noiseH) * 0.3D;
                }
                noiseH = noiseH * 3.0D - 2.0D;
                if (noiseH < 0) {
                    noiseH = Math.max(noiseH * 0.5D, -1) / 1.4D * 0.5D;
                } else {
                    noiseH = Math.min(noiseH, 1) / 8.0D;
                }

                noiseH = ((noiseH * 0.2D + avgHeightBase) * BASE_SIZE / 8.0D) * 4.0D + BASE_SIZE;
                for (int k = 0; k < 33; k++) {
                    double nH = (k - noiseH) * STRETCH_Y * (double) WORLD_DEPTH / 256.0D / avgHeightScale;
                    if (nH < 0.0D) {
                        nH *= 4.0D;
                    }
                    double noiseR = roughnessNoise[index] / 512.0D;
                    double noiseR2 = roughnessNoise2[index] / 512.0D;
                    double noiseD = (detailNoise[index] / 10.0D + 1.0D) / 2.0D;
                    // linear interpolation
                    double dst = noiseD < 0 ? noiseR : noiseD > 1 ? noiseR2 : noiseR + (noiseR2 - noiseR) * noiseD;
                    dst -= nH;
                    index++;
                    if (k > 29) {
                      double lowering = (k - 29) / 3.0D;
                      dst = dst * (1.0D - lowering) + lowering * -10.0D;
                    }
                    density[k + (j + i * 5) * 33] = dst;
                }
            }
        }
    }

    private static void setBiomeSpecificGround(GroundGenerator gen, Biome... biomes) {
        for (Biome biome : biomes) {
            GROUND_MAP.put(biome, gen);
        }
    }

    private static void setBiomeHeight(BiomeHeight height, Biome... biomes) {
        for (Biome biome : biomes) {
            HEIGHT_MAP.put(biome, height);
        }
    }

    private static class BiomeHeight {
        public static final BiomeHeight DEFAULT = new BiomeHeight(0.1D, 0.2D);
        public static final BiomeHeight FLAT_SHORE = new BiomeHeight(0.0D, 0.025D);
        public static final BiomeHeight HIGH_PLATEAU = new BiomeHeight(1.5D, 0.025D);
        public static final BiomeHeight FLATLANDS = new BiomeHeight(0.125D, 0.05D);
        public static final BiomeHeight SWAMPLAND = new BiomeHeight(-0.2D, 0.1D);
        public static final BiomeHeight MID_PLAINS = new BiomeHeight(0.2D, 0.2D);
        public static final BiomeHeight FLAT_HILLS = new BiomeHeight(0.275D, 0.25D); // FLATLANDS -> M
        public static final BiomeHeight SWAMPLAND_HILLS = new BiomeHeight(-0.1D, 0.3D);
        public static final BiomeHeight LOW_HILLS = new BiomeHeight(0.2D, 0.3D);
        public static final BiomeHeight HILLS = new BiomeHeight(0.45D, 0.3D);
        public static final BiomeHeight MID_HILLS3 = new BiomeHeight(0.1D, 0.4D);
        public static final BiomeHeight MID_HILLS = new BiomeHeight(0.2D, 0.4D); // DEFAULT -> M
        public static final BiomeHeight MID_HILLS2 = new BiomeHeight(0.3D, 0.4D); // MID_PLAINS -> M
        public static final BiomeHeight BIG_HILLS = new BiomeHeight(0.525D, 0.55D);
        public static final BiomeHeight BIG_HILLS2 = new BiomeHeight(0.55D, 0.5D);
        public static final BiomeHeight EXTREME_HILLS = new BiomeHeight(1.0D, 0.5D);
        public static final BiomeHeight ROCKY_SHORE = new BiomeHeight(0.1D, 0.8D);
        public static final BiomeHeight LOW_SPIKES = new BiomeHeight(0.4125D, 1.325D);
        public static final BiomeHeight HIGH_SPIKES = new BiomeHeight(1.1D, 1.3125D);
        public static final BiomeHeight RIVER = new BiomeHeight(-0.5D, 0.0D);
        public static final BiomeHeight OCEAN = new BiomeHeight(-1.0D, 0.1D);
        public static final BiomeHeight DEEP_OCEAN = new BiomeHeight(-1.8D, 0.1D);

        private final double height;
        private final double scale;

        public BiomeHeight(double height, double scale) {
            this.height = height;
            this.scale = scale;
        }

        public double getHeight() {
            return height;
        }

        public double getScale() {
            return scale;
        }
    }

    static {
        setBiomeSpecificGround(new SandyGroundGenerator(), BEACH, COLD_BEACH, DESERT, DESERT_HILLS, DESERT_MOUNTAINS);
        setBiomeSpecificGround(new RockyGroundGenerator(), STONE_BEACH);
        setBiomeSpecificGround(new SnowyGroundGenerator(), ICE_PLAINS_SPIKES);
        setBiomeSpecificGround(new MycelGroundGenerator(), MUSHROOM_ISLAND, MUSHROOM_SHORE);
        setBiomeSpecificGround(new StonePatchGroundGenerator(), EXTREME_HILLS);
        setBiomeSpecificGround(new GravelPatchGroundGenerator(), EXTREME_HILLS_MOUNTAINS, EXTREME_HILLS_PLUS_MOUNTAINS);
        setBiomeSpecificGround(new DirtAndStonePatchGroundGenerator(), SAVANNA_MOUNTAINS, SAVANNA_PLATEAU_MOUNTAINS);
        setBiomeSpecificGround(new DirtPatchGroundGenerator(), MEGA_TAIGA, MEGA_TAIGA_HILLS, MEGA_SPRUCE_TAIGA, MEGA_SPRUCE_TAIGA_HILLS);
        setBiomeSpecificGround(new MesaGroundGenerator(), MESA, MESA_BRYCE, MESA_PLATEAU_FOREST, MESA_PLATEAU_FOREST_MOUNTAINS, MESA_PLATEAU, MESA_PLATEAU_MOUNTAINS);

        setBiomeHeight(BiomeHeight.OCEAN, OCEAN, FROZEN_OCEAN);
        setBiomeHeight(BiomeHeight.DEEP_OCEAN, DEEP_OCEAN);
        setBiomeHeight(BiomeHeight.RIVER, RIVER, FROZEN_RIVER);
        setBiomeHeight(BiomeHeight.FLAT_SHORE, BEACH, COLD_BEACH, MUSHROOM_SHORE);
        setBiomeHeight(BiomeHeight.ROCKY_SHORE, STONE_BEACH);
        setBiomeHeight(BiomeHeight.FLATLANDS, DESERT, ICE_PLAINS, SAVANNA);
        setBiomeHeight(BiomeHeight.EXTREME_HILLS, EXTREME_HILLS, EXTREME_HILLS_PLUS, EXTREME_HILLS_MOUNTAINS, EXTREME_HILLS_PLUS_MOUNTAINS);
        setBiomeHeight(BiomeHeight.MID_PLAINS, TAIGA, COLD_TAIGA, MEGA_TAIGA);
        setBiomeHeight(BiomeHeight.SWAMPLAND, SWAMPLAND);
        setBiomeHeight(BiomeHeight.LOW_HILLS, MUSHROOM_ISLAND);
        setBiomeHeight(BiomeHeight.HILLS, ICE_MOUNTAINS, DESERT_HILLS, FOREST_HILLS, TAIGA_HILLS, SMALL_MOUNTAINS, JUNGLE_HILLS, BIRCH_FOREST_HILLS, COLD_TAIGA_HILLS, MEGA_TAIGA_HILLS, MESA_PLATEAU_FOREST_MOUNTAINS, MESA_PLATEAU_MOUNTAINS);
        setBiomeHeight(BiomeHeight.HIGH_PLATEAU, SAVANNA_PLATEAU, MESA_PLATEAU_FOREST, MESA_PLATEAU);
        setBiomeHeight(BiomeHeight.FLAT_HILLS, DESERT_MOUNTAINS);
        setBiomeHeight(BiomeHeight.BIG_HILLS, ICE_PLAINS_SPIKES);
        setBiomeHeight(BiomeHeight.BIG_HILLS2, BIRCH_FOREST_HILLS_MOUNTAINS);
        setBiomeHeight(BiomeHeight.SWAMPLAND_HILLS, SWAMPLAND_MOUNTAINS);
        setBiomeHeight(BiomeHeight.MID_HILLS, JUNGLE_MOUNTAINS, JUNGLE_EDGE_MOUNTAINS, BIRCH_FOREST_MOUNTAINS, ROOFED_FOREST_MOUNTAINS);
        setBiomeHeight(BiomeHeight.MID_HILLS2, TAIGA_MOUNTAINS, COLD_TAIGA_MOUNTAINS, MEGA_SPRUCE_TAIGA, MEGA_SPRUCE_TAIGA_HILLS);
        setBiomeHeight(BiomeHeight.MID_HILLS3, FLOWER_FOREST);
        setBiomeHeight(BiomeHeight.LOW_SPIKES, SAVANNA_MOUNTAINS);
        setBiomeHeight(BiomeHeight.HIGH_SPIKES, SAVANNA_PLATEAU_MOUNTAINS);

        for (int x = 0; x < 5; x++) {
            for (int z = 0; z < 5; z++) {
                int sqX = x - 2;
                sqX *= sqX;
                int sqZ = z - 2;
                sqZ *= sqZ;
                ELEVATION_WEIGHT[z + x * 5] = 10.0F / Math.sqrt(sqX + sqZ + 0.2F);
            }
        }
    }
}

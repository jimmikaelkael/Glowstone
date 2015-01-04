package net.glowstone.constants;

import org.apache.commons.lang.Validate;
import org.bukkit.block.Biome;

import java.util.Arrays;

import static org.bukkit.block.Biome.*;

/**
 * Mappings for Biome id values.
 */
public final class GlowBiome {

    private GlowBiome() {}

    private static final int[] ids = new int[Biome.values().length];
    private static final Biome[] biomes = new Biome[256];
    private static final BiomeHeight[] heights = new BiomeHeight[biomes.length];

    /**
     * Get the biome ID for a specified Biome.
     * @param biome the Biome.
     * @return the biome id, or -1
     */
    public static int getId(Biome biome) {
        Validate.notNull(biome, "Biome cannot be null");
        return ids[biome.ordinal()];
    }

    /**
     * Get the Biome for a specified id.
     * @param id the id.
     * @return the Biome, or null
     */
    public static Biome getBiome(int id) {
        return biomes[id];
    }

    /**
     * Get the BiomeHeight for a specified id.
     * @param id the id.
     * @return the BiomeHeight, or null
     */
    public static BiomeHeight getBiomeHeight(int id) {
        return heights[id];
    }

    private static void set(int id, Biome biome) {
        set(id, biome, BiomeHeight.DEFAULT);
    }

    private static void set(int id, Biome biome, BiomeHeight height) {
        ids[biome.ordinal()] = id;
        biomes[id] = biome;
        heights[id] = height;
    }

    static {
        Arrays.fill(ids, -1);
        set(0, OCEAN, BiomeHeight.OCEAN);
        set(1, PLAINS);
        set(2, DESERT, BiomeHeight.FLATLANDS);
        set(3, EXTREME_HILLS, BiomeHeight.EXTREME_HILLS);
        set(4, FOREST);
        set(5, TAIGA, BiomeHeight.MID_PLAINS);
        set(6, SWAMPLAND, BiomeHeight.SWAMPLAND);
        set(7, RIVER, BiomeHeight.RIVER);
        set(8, HELL);
        set(9, SKY);
        set(10, FROZEN_OCEAN, BiomeHeight.OCEAN);
        set(11, FROZEN_RIVER, BiomeHeight.RIVER);
        set(12, ICE_PLAINS, BiomeHeight.FLATLANDS);
        set(13, ICE_MOUNTAINS, BiomeHeight.HILLS);
        set(14, MUSHROOM_ISLAND, BiomeHeight.LOW_HILLS);
        set(15, MUSHROOM_SHORE, BiomeHeight.FLAT_SHORE);
        set(16, BEACH, BiomeHeight.FLAT_SHORE);
        set(17, DESERT_HILLS, BiomeHeight.HILLS);
        set(18, FOREST_HILLS, BiomeHeight.HILLS);
        set(19, TAIGA_HILLS, BiomeHeight.HILLS);
        set(20, SMALL_MOUNTAINS, BiomeHeight.HILLS); // EXTREME_HILLS_EDGE
        set(21, JUNGLE);
        set(22, JUNGLE_HILLS, BiomeHeight.HILLS);
        set(23, JUNGLE_EDGE);
        set(24, DEEP_OCEAN, BiomeHeight.DEEP_OCEAN);
        set(25, STONE_BEACH, BiomeHeight.ROCKY_SHORE);
        set(26, COLD_BEACH, BiomeHeight.FLAT_SHORE);
        set(27, BIRCH_FOREST);
        set(28, BIRCH_FOREST_HILLS, BiomeHeight.HILLS);
        set(29, ROOFED_FOREST);
        set(30, COLD_TAIGA, BiomeHeight.MID_PLAINS);
        set(31, COLD_TAIGA_HILLS, BiomeHeight.HILLS);
        set(32, MEGA_TAIGA, BiomeHeight.MID_PLAINS);
        set(33, MEGA_TAIGA_HILLS, BiomeHeight.HILLS);
        set(34, EXTREME_HILLS_PLUS, BiomeHeight.EXTREME_HILLS);
        set(35, SAVANNA, BiomeHeight.FLATLANDS);
        set(36, SAVANNA_PLATEAU, BiomeHeight.HIGH_PLATEAU);
        set(37, MESA);
        set(38, MESA_PLATEAU_FOREST, BiomeHeight.HIGH_PLATEAU);
        set(39, MESA_PLATEAU, BiomeHeight.HIGH_PLATEAU);
        set(129, SUNFLOWER_PLAINS, BiomeHeight.DEFAULT);
        set(130, DESERT_MOUNTAINS, BiomeHeight.FLAT_HILLS);
        set(131, EXTREME_HILLS_MOUNTAINS, BiomeHeight.EXTREME_HILLS);
        set(132, FLOWER_FOREST, BiomeHeight.MID_HILLS3);
        set(133, TAIGA_MOUNTAINS, BiomeHeight.MID_HILLS2);
        set(134, SWAMPLAND_MOUNTAINS, BiomeHeight.SWAMPLAND_HILLS);
        set(140, ICE_PLAINS_SPIKES, BiomeHeight.BIG_HILLS);
        set(149, JUNGLE_MOUNTAINS, BiomeHeight.MID_HILLS);
        set(151, JUNGLE_EDGE_MOUNTAINS, BiomeHeight.MID_HILLS);
        set(155, BIRCH_FOREST_MOUNTAINS, BiomeHeight.DEFAULT);
        set(156, BIRCH_FOREST_HILLS_MOUNTAINS, BiomeHeight.MID_HILLS);
        set(157, ROOFED_FOREST_MOUNTAINS, BiomeHeight.MID_HILLS);
        set(158, COLD_TAIGA_MOUNTAINS, BiomeHeight.MID_HILLS2);
        set(160, MEGA_SPRUCE_TAIGA, BiomeHeight.MID_PLAINS);
        set(161, MEGA_SPRUCE_TAIGA_HILLS, BiomeHeight.MID_PLAINS);
        set(162, EXTREME_HILLS_PLUS_MOUNTAINS, BiomeHeight.EXTREME_HILLS);
        set(163, SAVANNA_MOUNTAINS, BiomeHeight.LOW_SPIKES);
        set(164, SAVANNA_PLATEAU_MOUNTAINS, BiomeHeight.HIGH_SPIKES);
        set(165, MESA_BRYCE);
        set(166, MESA_PLATEAU_FOREST_MOUNTAINS, BiomeHeight.HILLS);
        set(167, MESA_PLATEAU_MOUNTAINS, BiomeHeight.HILLS);
    }

    public static class BiomeHeight {
        public static final BiomeHeight DEFAULT = new BiomeHeight(0.1D, 0.2D);
        public static final BiomeHeight FLAT_SHORE = new BiomeHeight(0.0D, 0.025D);
        public static final BiomeHeight HIGH_PLATEAU = new BiomeHeight(1.5D, 0.025D);
        public static final BiomeHeight FLATLANDS = new BiomeHeight(0.125D, 0.05D);
        public static final BiomeHeight SWAMPLAND = new BiomeHeight(-0.2D, 0.1D);
        public static final BiomeHeight MID_PLAINS = new BiomeHeight(0.2D, 0.2D);
        public static final BiomeHeight FLAT_HILLS = new BiomeHeight(0.275D, 0.25D); // FLATLANDS -> M
        public static final BiomeHeight SWAMPLAND_HILLS = new BiomeHeight(-0.1D, 0.3D);
        public static final BiomeHeight LOW_HILLS = new BiomeHeight(0.2D, 0.3D);
        public static final BiomeHeight HILLS = new BiomeHeight(0.425D, 0.325D);
        public static final BiomeHeight MID_HILLS3 = new BiomeHeight(0.1D, 0.4D);
        public static final BiomeHeight MID_HILLS = new BiomeHeight(0.2D, 0.4D); // DEFAULT -> M
        public static final BiomeHeight MID_HILLS2 = new BiomeHeight(0.3D, 0.4D); // MID_PLAINS -> M
        public static final BiomeHeight BIG_HILLS = new BiomeHeight(0.525D, 0.55D);
        public static final BiomeHeight EXTREME_HILLS = new BiomeHeight(1.0D, 0.5D);
        public static final BiomeHeight ROCKY_SHORE = new BiomeHeight(0.1D, 0.8D);
        public static final BiomeHeight LOW_SPIKES = new BiomeHeight(0.3625D, 1.225D);
        public static final BiomeHeight HIGH_SPIKES = new BiomeHeight(1.05D, 1.2125D);
        public static final BiomeHeight RIVER = new BiomeHeight(-0.75D, 0.0D);
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
}

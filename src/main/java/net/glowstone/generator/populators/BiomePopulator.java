package net.glowstone.generator.populators;

import net.glowstone.generator.decorators.overworld.*;
import net.glowstone.generator.decorators.overworld.FlowerDecorator.Flower;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BiomePopulator extends BlockPopulator {

    private final List<BlockPopulator> decorators = new ArrayList<>();

    public BiomePopulator() {

        // the order is important

        addDecorator(new LakeDecorator(Material.STATIONARY_WATER)
                .setDefaultAmount(1)
                .setBiomeAmount(0, Biome.DESERT, Biome.DESERT_HILLS, Biome.DESERT_MOUNTAINS));

        addDecorator(new LakeDecorator(Material.STATIONARY_LAVA)
                .setDefaultAmount(1)
                .setBiomeAmount(0, Biome.DESERT, Biome.DESERT_HILLS, Biome.DESERT_MOUNTAINS));

        addDecorator(new UnderwaterDecorator(Material.SAND)
                .setRadiuses(7, 2)
                .setDefaultAmount(3)
                .setBiomeAmount(0, Biome.SWAMPLAND, Biome.SWAMPLAND_MOUNTAINS));

        addDecorator(new UnderwaterDecorator(Material.CLAY)
                .setRadiuses(4, 1)
                .setPreservesShoreBlocks()
                .setDefaultAmount(1));

        addDecorator(new UnderwaterDecorator(Material.GRAVEL)
                .setRadiuses(6, 2)
                .setDefaultAmount(1)
                .setBiomeAmount(0, Biome.SWAMPLAND, Biome.SWAMPLAND_MOUNTAINS));

        addDecorator(new TreeDecorator()
                .setTreeWeight(3, TreeType.TREE, Biome.OCEAN) // fix for lack of biomes
                .setTreeWeight(2, TreeType.BIRCH, Biome.OCEAN) // fix for lack of biomes

                .setTreeWeight(20, TreeType.REDWOOD, Biome.EXTREME_HILLS, Biome.EXTREME_HILLS_MOUNTAINS)
                .setTreeWeight(1, TreeType.BIG_TREE, Biome.EXTREME_HILLS, Biome.EXTREME_HILLS_MOUNTAINS)
                .setTreeWeight(9, TreeType.TREE, Biome.EXTREME_HILLS, Biome.EXTREME_HILLS_MOUNTAINS)

                .setTreeWeight(4, TreeType.TREE, Biome.FOREST, Biome.FOREST_HILLS, Biome.FLOWER_FOREST,
                        Biome.ROOFED_FOREST, Biome.ROOFED_FOREST_MOUNTAINS)
                .setTreeWeight(1, TreeType.BIRCH, Biome.FOREST, Biome.FOREST_HILLS, Biome.FLOWER_FOREST,
                        Biome.BIRCH_FOREST, Biome.BIRCH_FOREST_MOUNTAINS, Biome.BIRCH_FOREST_HILLS,
                        Biome.BIRCH_FOREST_HILLS_MOUNTAINS, Biome.ROOFED_FOREST, Biome.ROOFED_FOREST_MOUNTAINS)
                .setTreeWeight(2, TreeType.RED_MUSHROOM, Biome.ROOFED_FOREST, Biome.ROOFED_FOREST_MOUNTAINS)
                .setTreeWeight(2, TreeType.BROWN_MUSHROOM, Biome.ROOFED_FOREST, Biome.ROOFED_FOREST_MOUNTAINS)
                .setTreeWeight(76, TreeType.DARK_OAK, Biome.ROOFED_FOREST, Biome.ROOFED_FOREST_MOUNTAINS)
                .setTreeWeight(1, TreeType.TALL_BIRCH, Biome.BIRCH_FOREST, Biome.BIRCH_FOREST_MOUNTAINS,
                        Biome.BIRCH_FOREST_HILLS, Biome.BIRCH_FOREST_HILLS_MOUNTAINS)

                .setTreeWeight(2, TreeType.REDWOOD, Biome.TAIGA, Biome.TAIGA_HILLS, Biome.TAIGA_MOUNTAINS,
                        Biome.COLD_TAIGA, Biome.COLD_TAIGA_HILLS, Biome.COLD_TAIGA_MOUNTAINS)
                .setTreeWeight(1, TreeType.TALL_REDWOOD, Biome.TAIGA, Biome.TAIGA_HILLS, Biome.TAIGA_MOUNTAINS,
                        Biome.COLD_TAIGA, Biome.COLD_TAIGA_HILLS, Biome.COLD_TAIGA_MOUNTAINS)

                .setTreeWeight(52, TreeType.REDWOOD, Biome.MEGA_TAIGA, Biome.MEGA_TAIGA_HILLS)
                .setTreeWeight(36, TreeType.MEGA_REDWOOD, Biome.MEGA_TAIGA, Biome.MEGA_TAIGA_HILLS) // big spruce, those with less leaves height
                .setTreeWeight(26, TreeType.TALL_REDWOOD, Biome.MEGA_TAIGA, Biome.MEGA_TAIGA_HILLS)
                .setTreeWeight(3, TreeType.MEGA_REDWOOD, Biome.MEGA_TAIGA, Biome.MEGA_TAIGA_HILLS)

                .setTreeWeight(44, TreeType.REDWOOD, Biome.MEGA_TAIGA, Biome.MEGA_TAIGA_HILLS)
                .setTreeWeight(33, TreeType.MEGA_REDWOOD, Biome.MEGA_SPRUCE_TAIGA, Biome.MEGA_SPRUCE_TAIGA_HILLS)
                .setTreeWeight(22, TreeType.TALL_REDWOOD, Biome.MEGA_TAIGA, Biome.MEGA_TAIGA_HILLS)

                .setTreeWeight(1, TreeType.SWAMP, Biome.SWAMPLAND, Biome.SWAMPLAND_MOUNTAINS)

                .setTreeWeight(1, TreeType.REDWOOD, Biome.ICE_PLAINS, Biome.ICE_MOUNTAINS, Biome.ICE_PLAINS_SPIKES)

                .setTreeWeight(10, TreeType.BIG_TREE, Biome.JUNGLE, Biome.JUNGLE_HILLS, Biome.JUNGLE_MOUNTAINS,
                        Biome.JUNGLE_EDGE, Biome.JUNGLE_EDGE_MOUNTAINS)
                .setTreeWeight(45, TreeType.JUNGLE_BUSH, Biome.JUNGLE, Biome.JUNGLE_HILLS, Biome.JUNGLE_MOUNTAINS,
                        Biome.JUNGLE_EDGE, Biome.JUNGLE_EDGE_MOUNTAINS)
                .setTreeWeight(15, TreeType.JUNGLE, Biome.JUNGLE, Biome.JUNGLE_HILLS, Biome.JUNGLE_MOUNTAINS)
                .setTreeWeight(30, TreeType.COCOA_TREE, Biome.JUNGLE, Biome.JUNGLE_HILLS, Biome.JUNGLE_MOUNTAINS)
                .setTreeWeight(45, TreeType.COCOA_TREE, Biome.JUNGLE_EDGE, Biome.JUNGLE_EDGE_MOUNTAINS)

                .setTreeWeight(1, TreeType.TREE, Biome.MESA_PLATEAU_FOREST, Biome.MESA_PLATEAU_FOREST_MOUNTAINS)

                .setTreeWeight(1, TreeType.TREE, Biome.SAVANNA, Biome.SAVANNA_PLATEAU, Biome.SAVANNA_MOUNTAINS, Biome.SAVANNA_PLATEAU_MOUNTAINS)
                .setTreeWeight(4, TreeType.ACACIA, Biome.SAVANNA, Biome.SAVANNA_PLATEAU, Biome.SAVANNA_MOUNTAINS, Biome.SAVANNA_PLATEAU_MOUNTAINS)

                .setTreeWeight(1, TreeType.RED_MUSHROOM, Biome.MUSHROOM_ISLAND, Biome.MUSHROOM_SHORE)
                .setTreeWeight(1, TreeType.BROWN_MUSHROOM, Biome.MUSHROOM_ISLAND, Biome.MUSHROOM_SHORE)

                .setDefaultAmount(Integer.MIN_VALUE)
                .setBiomeAmount(5, Biome.OCEAN) // fix for lack of biomes
                .setBiomeAmount(0, Biome.EXTREME_HILLS, Biome.EXTREME_HILLS_MOUNTAINS, Biome.ICE_PLAINS, Biome.ICE_MOUNTAINS,
                        Biome.ICE_PLAINS_SPIKES)
                .setBiomeAmount(1, Biome.SAVANNA, Biome.SAVANNA_PLATEAU, Biome.MUSHROOM_ISLAND, Biome.MUSHROOM_SHORE)
                .setBiomeAmount(2, Biome.SWAMPLAND, Biome.SWAMPLAND_MOUNTAINS, Biome.JUNGLE_EDGE, Biome.JUNGLE_EDGE_MOUNTAINS,
                        Biome.SAVANNA_MOUNTAINS, Biome.SAVANNA_PLATEAU_MOUNTAINS)
                .setBiomeAmount(3, Biome.SMALL_MOUNTAINS, Biome.EXTREME_HILLS_PLUS, Biome.EXTREME_HILLS_PLUS_MOUNTAINS)
                .setBiomeAmount(5, Biome.MESA_PLATEAU_FOREST, Biome.MESA_PLATEAU_FOREST_MOUNTAINS)
                .setBiomeAmount(6, Biome.FLOWER_FOREST)
                .setBiomeAmount(10, Biome.FOREST, Biome.FOREST_HILLS, Biome.BIRCH_FOREST, Biome.BIRCH_FOREST_MOUNTAINS,
                        Biome.BIRCH_FOREST_HILLS, Biome.BIRCH_FOREST_HILLS_MOUNTAINS, Biome.TAIGA, Biome.TAIGA_HILLS,
                        Biome.TAIGA_MOUNTAINS, Biome.COLD_TAIGA, Biome.COLD_TAIGA_HILLS, Biome.COLD_TAIGA_MOUNTAINS,
                        Biome.MEGA_TAIGA, Biome.MEGA_TAIGA_HILLS, Biome.MEGA_SPRUCE_TAIGA, Biome.MEGA_SPRUCE_TAIGA_HILLS)
                .setBiomeAmount(50, Biome.ROOFED_FOREST, Biome.ROOFED_FOREST_MOUNTAINS)
                .setBiomeAmount(50, Biome.JUNGLE, Biome.JUNGLE_HILLS, Biome.JUNGLE_MOUNTAINS));

        addDecorator(new FlowerDecorator()
                .setDefaultFlowerWeight(2, Flower.DANDELION)
                .setDefaultFlowerWeight(1, Flower.POPPY)

                .setFlowerWeight(4, Flower.DANDELION, Biome.OCEAN) // fix for lack of biomes
                .setFlowerWeight(2, Flower.POPPY, Biome.OCEAN) // fix for lack of biomes
                .setFlowerWeight(1, Flower.LILAC, Biome.OCEAN) // fix for lack of biomes
                .setFlowerWeight(1, Flower.ROSE_BUSH, Biome.OCEAN) // fix for lack of biomes
                .setFlowerWeight(1, Flower.PEONIA, Biome.OCEAN) // fix for lack of biomes

                .setFlowerWeight(27, Flower.DANDELION, Biome.PLAINS)
                .setFlowerWeight(17, Flower.POPPY, Biome.PLAINS)
                .setFlowerWeight(17, Flower.HOUSTONIA, Biome.PLAINS)
                .setFlowerWeight(17, Flower.OXEYE_DAISY, Biome.PLAINS)
                .setFlowerWeight(5, Flower.TULIP_RED, Biome.PLAINS)
                .setFlowerWeight(5, Flower.TULIP_ORANGE, Biome.PLAINS)
                .setFlowerWeight(5, Flower.TULIP_WHITE, Biome.PLAINS)
                .setFlowerWeight(5, Flower.TULIP_PINK, Biome.PLAINS)

                .setFlowerWeight(1, Flower.BLUE_ORCHID, Biome.SWAMPLAND, Biome.SWAMPLAND_MOUNTAINS)

                .setFlowerWeight(4, Flower.POPPY, Biome.FLOWER_FOREST)
                .setFlowerWeight(2, Flower.ALLIUM, Biome.FLOWER_FOREST)
                .setFlowerWeight(2, Flower.HOUSTONIA, Biome.FLOWER_FOREST)
                .setFlowerWeight(2, Flower.TULIP_RED, Biome.FLOWER_FOREST)
                .setFlowerWeight(2, Flower.TULIP_ORANGE, Biome.FLOWER_FOREST)
                .setFlowerWeight(2, Flower.TULIP_WHITE, Biome.FLOWER_FOREST)
                .setFlowerWeight(2, Flower.TULIP_PINK, Biome.FLOWER_FOREST)
                .setFlowerWeight(2, Flower.OXEYE_DAISY, Biome.FLOWER_FOREST)
                .setFlowerWeight(1, Flower.LILAC, Biome.FLOWER_FOREST)
                .setFlowerWeight(1, Flower.ROSE_BUSH, Biome.FLOWER_FOREST)
                .setFlowerWeight(1, Flower.PEONIA, Biome.FLOWER_FOREST)

                .setDefaultAmount(2)
                .setBiomeAmount(0, Biome.ICE_PLAINS, Biome.ICE_MOUNTAINS, Biome.ICE_PLAINS_SPIKES, Biome.MESA,
                        Biome.MESA_PLATEAU, Biome.MESA_PLATEAU_MOUNTAINS, Biome.MESA_PLATEAU_FOREST,
                        Biome.MESA_PLATEAU_FOREST_MOUNTAINS, Biome.MESA_BRYCE, Biome.MUSHROOM_ISLAND, Biome.MUSHROOM_SHORE)
                .setBiomeAmount(1, Biome.SWAMPLAND, Biome.SWAMPLAND_MOUNTAINS)
                .setBiomeAmount(4, Biome.PLAINS, Biome.JUNGLE, Biome.JUNGLE_HILLS, Biome.JUNGLE_MOUNTAINS,
                        Biome.JUNGLE_EDGE, Biome.JUNGLE_EDGE_MOUNTAINS, Biome.SAVANNA, Biome.SAVANNA_PLATEAU)
                .setBiomeAmount(80, Biome.FLOWER_FOREST));

        addDecorator(new TallGrassDecorator()
                .setFernDensity(0.8D, Biome.JUNGLE, Biome.JUNGLE_HILLS, Biome.JUNGLE_MOUNTAINS,
                        Biome.JUNGLE_EDGE, Biome.JUNGLE_EDGE_MOUNTAINS)
                .setFernDensity(0.25D, Biome.TAIGA, Biome.TAIGA_HILLS, Biome.TAIGA_MOUNTAINS, Biome.COLD_TAIGA,
                        Biome.COLD_TAIGA_HILLS, Biome.COLD_TAIGA_MOUNTAINS, Biome.MEGA_TAIGA, Biome.MEGA_TAIGA_HILLS,
                        Biome.MEGA_SPRUCE_TAIGA, Biome.MEGA_SPRUCE_TAIGA_HILLS)

                .setDefaultAmount(1)
                .setBiomeAmount(2, Biome.OCEAN) // fix for lack of biomes
                .setBiomeAmount(0, Biome.MUSHROOM_ISLAND, Biome.MUSHROOM_SHORE)
                .setBiomeAmount(2, Biome.FOREST, Biome.FOREST_HILLS, Biome.BIRCH_FOREST, Biome.BIRCH_FOREST_MOUNTAINS, 
                        Biome.BIRCH_FOREST_HILLS, Biome.BIRCH_FOREST_HILLS_MOUNTAINS, Biome.ROOFED_FOREST,
                        Biome.ROOFED_FOREST_MOUNTAINS)
                .setBiomeAmount(5, Biome.SWAMPLAND, Biome.SWAMPLAND_MOUNTAINS, Biome.SAVANNA_MOUNTAINS, Biome.SAVANNA_PLATEAU_MOUNTAINS)
                .setBiomeAmount(7, Biome.MEGA_TAIGA, Biome.MEGA_TAIGA_HILLS, Biome.MEGA_SPRUCE_TAIGA, Biome.MEGA_SPRUCE_TAIGA_HILLS)
                .setBiomeAmount(10, Biome.PLAINS)
                .setBiomeAmount(20, Biome.SAVANNA, Biome.SAVANNA_PLATEAU)
                .setBiomeAmount(25, Biome.JUNGLE, Biome.JUNGLE_HILLS, Biome.JUNGLE_EDGE));

        addDecorator(new DeadBushDecorator()
                .setDefaultAmount(0)
                .setBiomeAmount(1, Biome.OCEAN) // fix for lack of biomes
                .setBiomeAmount(2, Biome.DESERT, Biome.DESERT_MOUNTAINS, Biome.DESERT_HILLS)
                .setBiomeAmount(20, Biome.MESA, Biome.MESA_PLATEAU, Biome.MESA_PLATEAU_MOUNTAINS,
                        Biome.MESA_PLATEAU_FOREST, Biome.MESA_PLATEAU_FOREST_MOUNTAINS, Biome.MESA_BRYCE)
                .setBiomeAmount(1, Biome.SWAMPLAND, Biome.SWAMPLAND_MOUNTAINS)
                .setBiomeAmount(1, Biome.TAIGA, Biome.TAIGA_HILLS, Biome.TAIGA_MOUNTAINS));

        addDecorator(new WaterLilyDecorator()
                .setDefaultAmount(0)
                .setBiomeAmount(4, Biome.SWAMPLAND, Biome.SWAMPLAND_MOUNTAINS));

        addDecorator(new MushroomDecorator(Material.BROWN_MUSHROOM)
                .setFixedHeightRange()
                .setDensity(0.25D)
                .setDefaultAmount(0)
                .setBiomeAmount(1, Biome.MUSHROOM_ISLAND, Biome.MUSHROOM_SHORE, Biome.TAIGA, Biome.TAIGA_HILLS,
                        Biome.TAIGA_MOUNTAINS, Biome.COLD_TAIGA, Biome.COLD_TAIGA_HILLS, Biome.COLD_TAIGA_MOUNTAINS)
                .setBiomeAmount(3, Biome.MEGA_TAIGA, Biome.MEGA_TAIGA_HILLS, Biome.MEGA_SPRUCE_TAIGA, Biome.MEGA_SPRUCE_TAIGA_HILLS)
                .setBiomeAmount(8, Biome.SWAMPLAND, Biome.SWAMPLAND_MOUNTAINS));               

        addDecorator(new MushroomDecorator(Material.RED_MUSHROOM)
                .setDensity(0.125D)
                .setDefaultAmount(0)
                .setBiomeAmount(1, Biome.MUSHROOM_ISLAND, Biome.MUSHROOM_SHORE, Biome.TAIGA, Biome.TAIGA_HILLS,
                        Biome.TAIGA_MOUNTAINS, Biome.COLD_TAIGA, Biome.COLD_TAIGA_HILLS, Biome.COLD_TAIGA_MOUNTAINS)
                .setBiomeAmount(3, Biome.MEGA_TAIGA, Biome.MEGA_TAIGA_HILLS, Biome.MEGA_SPRUCE_TAIGA, Biome.MEGA_SPRUCE_TAIGA_HILLS)
                .setBiomeAmount(8, Biome.SWAMPLAND, Biome.SWAMPLAND_MOUNTAINS));               

        addDecorator(new MushroomDecorator(Material.BROWN_MUSHROOM)
                .setDensity(0.25D)
                .setDefaultAmount(1));

        addDecorator(new MushroomDecorator(Material.RED_MUSHROOM)
                .setDensity(0.125D)
                .setDefaultAmount(1));

        addDecorator(new SugarCaneDecorator()
                .setDefaultAmount(10)
                .setBiomeAmount(60, Biome.DESERT, Biome.DESERT_HILLS, Biome.DESERT_MOUNTAINS)
                .setBiomeAmount(13, Biome.MESA, Biome.MESA_PLATEAU, Biome.MESA_PLATEAU_MOUNTAINS,
                        Biome.MESA_PLATEAU_FOREST, Biome.MESA_PLATEAU_FOREST_MOUNTAINS, Biome.MESA_BRYCE)
                .setBiomeAmount(20, Biome.SWAMPLAND, Biome.SWAMPLAND_MOUNTAINS));

        addDecorator(new PumpkinDecorator());

        addDecorator(new CactusDecorator()
                .setDefaultAmount(0)
                .setBiomeAmount(5, Biome.OCEAN) // fix for lack of biomes
                .setBiomeAmount(10, Biome.DESERT, Biome.DESERT_HILLS, Biome.DESERT_MOUNTAINS)
                .setBiomeAmount(5, Biome.MESA, Biome.MESA_PLATEAU, Biome.MESA_PLATEAU_MOUNTAINS,
                        Biome.MESA_PLATEAU_FOREST, Biome.MESA_PLATEAU_FOREST_MOUNTAINS, Biome.MESA_BRYCE));

        addDecorator(new FlowingLiquidDecorator(Material.WATER)
                .setDefaultAmount(50));

        addDecorator(new FlowingLiquidDecorator(Material.LAVA)
                .setDefaultAmount(20));

        addDecorator(new MelonDecorator()
                .setDefaultAmount(0)
                .setBiomeAmount(1, Biome.OCEAN) // fix for lack of biomes
                .setBiomeAmount(1, Biome.JUNGLE, Biome.JUNGLE_HILLS, Biome.JUNGLE_MOUNTAINS,
                        Biome.JUNGLE_EDGE, Biome.JUNGLE_EDGE_MOUNTAINS));

        addDecorator(new EmeraldOreDecorator()
                .setDefaultAmount(0)
                .setBiomeAmount(1, Biome.OCEAN) // fix for lack of biomes
                .setBiomeAmount(1, Biome.EXTREME_HILLS, Biome.EXTREME_HILLS_MOUNTAINS,
                        Biome.EXTREME_HILLS_PLUS, Biome.EXTREME_HILLS_PLUS_MOUNTAINS));

        addDecorator(new InfestedStoneDecorator()
                .setDefaultAmount(0)
                .setBiomeAmount(1, Biome.OCEAN) // fix for lack of biomes
                .setBiomeAmount(1, Biome.EXTREME_HILLS, Biome.EXTREME_HILLS_MOUNTAINS,
                        Biome.EXTREME_HILLS_PLUS, Biome.EXTREME_HILLS_PLUS_MOUNTAINS));
    }

    @Override
    public void populate(World world, Random random, Chunk source) {
        for (BlockPopulator decorator : decorators) {
            decorator.populate(world, random, source);
        }
    }

    private void addDecorator(BlockPopulator decorator) {
        decorators.add(decorator);
    }
}

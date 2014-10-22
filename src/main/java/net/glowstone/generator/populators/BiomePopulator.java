package net.glowstone.generator.populators;

import net.glowstone.generator.decorators.DeadBushDecorator;
import net.glowstone.generator.decorators.Decorator;
import net.glowstone.generator.decorators.SugarCaneDecorator;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BiomePopulator extends BlockPopulator {

    private List<Decorator> decorators = new ArrayList<Decorator>();

    public BiomePopulator() {

        // the order is important

        addDecorator(new DeadBushDecorator()
                .setDefaultAmount(10)
                .setBiomeAmount(Biome.DESERT, 2)
                .setBiomeAmount(Biome.MESA, 20)
                .setBiomeAmount(Biome.SWAMPLAND, 1)
                .setBiomeAmount(Biome.TAIGA, 1));

        addDecorator(new SugarCaneDecorator()
                .setDefaultAmount(10)
                .setBiomeAmount(Biome.DESERT, 60)
                .setBiomeAmount(Biome.MESA, 13)
                .setBiomeAmount(Biome.SWAMPLAND, 20));
    }

    @Override
    public void populate(World world, Random random, Chunk source) {
        for (Decorator decorator : decorators) {
            decorator.decorate(world, random, source);
        }
    }

    private void addDecorator(Decorator decorator) {
        decorators.add(decorator);
    }
}

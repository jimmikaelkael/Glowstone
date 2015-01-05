package net.glowstone.generator.ground;

import java.util.Random;

import org.bukkit.Material;

public class GravellyMountainGroundGenerator extends GroundGenerator {
    @Override
    public void generateTerrainColumn(short[][] buf, Random random, int x, int z, int seaLevel, double surfaceNoise) {
        if (surfaceNoise < -1.0D || surfaceNoise > 2.0D) {
            setTopMaterial(Material.GRAVEL);
            setGroundMaterial(Material.GRAVEL);
        } else {
            setTopMaterial(Material.GRASS);
            setGroundMaterial(Material.DIRT);
        }
        super.generateTerrainColumn(buf, random, x, z, seaLevel, surfaceNoise);
    }
}

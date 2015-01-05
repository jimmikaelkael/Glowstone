package net.glowstone.generator.ground;

import java.util.Random;

import org.bukkit.Material;

public class DirtAndStonePatchGroundGenerator extends GroundGenerator {
    @Override
    public void generateTerrainColumn(short[][] buf, Random random, int x, int z, int seaLevel, double surfaceNoise) {
        if (surfaceNoise > 1.75D) {
            setTopMaterial(Material.STONE);
            setGroundMaterial(Material.STONE);
        } else if (surfaceNoise > -0.5D) {
            setTopMaterial(Material.DIRT, 1); // coarse dirt
            setGroundMaterial(Material.DIRT);
        } else {
            setTopMaterial(Material.GRASS);
            setGroundMaterial(Material.DIRT);
        }

        super.generateTerrainColumn(buf, random, x, z, seaLevel, surfaceNoise);
    }
}

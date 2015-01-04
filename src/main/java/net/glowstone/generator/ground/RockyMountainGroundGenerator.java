package net.glowstone.generator.ground;

import java.util.Random;

import org.bukkit.Material;

public class RockyMountainGroundGenerator extends GroundGenerator {
    @Override
    public void generateTerrainColumn(byte[][] buf, Random random, int x, int z, int seaLevel, double surfaceNoise) {
        if (surfaceNoise > 1.0D) {
            setTopMaterial(Material.STONE);
            setGroundMaterial(Material.STONE);
        } else {
            setTopMaterial(Material.GRASS);
            setGroundMaterial(Material.DIRT);
        }
        super.generateTerrainColumn(buf, random, x, z, seaLevel, surfaceNoise);
    }
}

package net.glowstone.generator.ground;

import org.bukkit.Material;

public class MesaGroundGenerator extends GroundGenerator {
    public MesaGroundGenerator() {
        super();
        setTopMaterial(Material.SAND, 1);
        setGroundMaterial(Material.STAINED_CLAY, 1);
    }
}

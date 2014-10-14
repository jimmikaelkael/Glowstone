package net.glowstone.block.blocktype;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import net.glowstone.block.GlowBlock;
import net.glowstone.block.GlowBlockState;
import net.glowstone.entity.GlowPlayer;

import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.CocoaPlant;
import org.bukkit.material.CocoaPlant.CocoaPlantSize;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Tree;
import org.bukkit.util.Vector;

public class BlockCocoa extends BlockAttachable {

    @Override
    public void placeBlock(GlowPlayer player, GlowBlockState state, BlockFace face, ItemStack holding, Vector clickedLoc) {
        super.placeBlock(player, state, face, holding, clickedLoc);

        final MaterialData data = state.getData();
        if (data instanceof CocoaPlant) {
            setAttachedFace(state, face.getOppositeFace());
            final CocoaPlant cocoa = (CocoaPlant) data;
            cocoa.setFacingDirection(face);
        } else {
            warnMaterialData(CocoaPlant.class, data);
        }
    }

    @Override
    public boolean canPlaceAt(GlowBlock block, BlockFace against) {
        if (block.getRelative(against.getOppositeFace()).getType().equals(Material.LOG)) {
            final MaterialData data = block.getState().getData();
            if (data instanceof Tree) {
                if (((Tree) data).getSpecies().equals(TreeSpecies.JUNGLE)) {
                    return true;
                }
            } else {
                warnMaterialData(Tree.class, data);
            }
        }
        return false;
    }

    @Override
    public Collection<ItemStack> getDrops(GlowBlock block) {
        final MaterialData data = block.getState().getData();
        if (data instanceof CocoaPlant) {
            int amount = 1;
            if (((CocoaPlant) data).getSize() == CocoaPlantSize.LARGE) {
                amount = 3;
            }
            return Collections.unmodifiableList(Arrays.asList(new ItemStack(Material.INK_SACK, amount, (short) 3)));
        } else {
            warnMaterialData(CocoaPlant.class, data);
        }
        return Collections.unmodifiableList(Arrays.asList(new ItemStack[0]));
    }
}
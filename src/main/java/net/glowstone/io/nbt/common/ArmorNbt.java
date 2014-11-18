package net.glowstone.io.nbt.common;

import net.glowstone.io.nbt.NbtSerializer;
import net.glowstone.util.nbt.CompoundTag;
import net.glowstone.util.nbt.ListTag;
import org.bukkit.inventory.ItemStack;

public class ArmorNbt implements NbtSerializer<ItemStack[], ListTag<CompoundTag>> {
    @Override
    public ListTag<CompoundTag> serialize(ItemStack[] obj) {
        return null;
    }

    @Override
    public ItemStack[] deserialize(ListTag<CompoundTag> tag) {
        return new ItemStack[0];
    }
}

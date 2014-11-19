package net.glowstone.io.nbt.common;

import net.glowstone.constants.ItemIds;
import net.glowstone.inventory.GlowItemFactory;
import net.glowstone.io.nbt.NbtSerializer;
import net.glowstone.util.nbt.CompoundTag;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Represents the serializer to (de)serialize item stacks to NBT.
 */
public class ItemStackNbt implements NbtSerializer<ItemStack, CompoundTag> {

    @Override
    public CompoundTag serialize(ItemStack stack) {
        CompoundTag tag = new CompoundTag();
        if (stack == null || stack.getType() == Material.AIR) {
            return tag;
        }
        tag.putString("id", ItemIds.getName(stack.getType()));
        tag.putShort("Damage", stack.getDurability());
        tag.putByte("Count", stack.getAmount());
        CompoundTag meta = GlowItemFactory.instance().writeNbt(stack.getItemMeta());
        if (meta != null) {
            tag.putCompound("tag", meta);
        }
        return tag;
    }

    @Override
    public void deserialize(CompoundTag tag, ItemStack into) {
    }

    // Copied from NbtSerialization:

    /**
     * Read an item stack in from an NBT tag. Returns null if no item exists.
     *
     * @param tag The tag to read from.
     *
     * @return The resulting ItemStack, or null.
     */
    public static ItemStack readItem(CompoundTag tag) {
        final Material material;
        if (tag.isString("id")) {
            material = ItemIds.getMaterial(tag.getString("id"));
        } else if (tag.isShort("id")) {
            material = Material.getMaterial(tag.getShort("id"));
        } else {
            return null;
        }
        final short damage = tag.isShort("Damage") ? tag.getShort("Damage") : 0;
        final byte count = tag.isByte("Count") ? tag.getByte("Count") : 0;

        if (material == null || material == Material.AIR || count == 0) {
            return null;
        }
        ItemStack stack = new ItemStack(material, count, damage);
        if (tag.isCompound("tag")) {
            stack.setItemMeta(GlowItemFactory.instance().readNbt(material, tag.getCompound("tag")));
        }
        return stack;
    }
}

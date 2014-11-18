package net.glowstone.io.nbt.common;

import net.glowstone.io.nbt.NbtSerializer;
import net.glowstone.util.nbt.CompoundTag;
import org.bukkit.inventory.Inventory;

public class EntityEquipmentNbt implements NbtSerializer<Inventory, CompoundTag> {
    @Override
    public CompoundTag serialize(Inventory obj) {
        return null;
    }

    @Override
    public Inventory deserialize(CompoundTag tag) {
        return null;
    }
}

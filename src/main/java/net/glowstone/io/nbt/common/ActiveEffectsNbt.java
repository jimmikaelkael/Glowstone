package net.glowstone.io.nbt.common;

import net.glowstone.io.nbt.NbtSerializer;
import net.glowstone.util.nbt.CompoundTag;
import net.glowstone.util.nbt.ListTag;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;

public class ActiveEffectsNbt implements NbtSerializer<Collection<PotionEffect>, ListTag<CompoundTag>> {
    @Override
    public ListTag<CompoundTag> serialize(Collection<PotionEffect> obj) {
        return null;
    }

    @Override
    public Collection<PotionEffect> deserialize(ListTag<CompoundTag> tag) {
        return null;
    }
}

package net.glowstone.io.nbt.common;

import net.glowstone.io.nbt.NbtSerializer;
import net.glowstone.util.nbt.CompoundTag;

/**
 * Represents the NBT Serializer for processing {@link net.glowstone.util.nbt.NBT}
 * annotations.
 */
public class TaggedObjectNbt implements NbtSerializer<Object, CompoundTag>{

    @Override
    public CompoundTag serialize(Object obj) {
        return null;
    }

    @Override
    public Object deserialize(CompoundTag tag) {
        return null;
    }
}

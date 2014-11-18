package net.glowstone.io.nbt.common;

import net.glowstone.io.nbt.NbtSerializer;
import net.glowstone.util.nbt.CompoundTag;

import java.util.UUID;

public class UuidNbt implements NbtSerializer<UUID, CompoundTag> {
    @Override
    public CompoundTag serialize(UUID obj) {
        return null;
    }

    @Override
    public UUID deserialize(CompoundTag tag) {
        return null;
    }
}

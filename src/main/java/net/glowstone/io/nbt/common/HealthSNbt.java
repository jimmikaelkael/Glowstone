package net.glowstone.io.nbt.common;

import net.glowstone.io.nbt.NbtSerializer;
import net.glowstone.util.nbt.ShortTag;

public class HealthSNbt implements NbtSerializer<Float, ShortTag> {
    @Override
    public ShortTag serialize(Float obj) {
        return null;
    }

    @Override
    public Float deserialize(ShortTag tag) {
        return null;
    }
}

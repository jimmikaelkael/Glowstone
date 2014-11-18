package net.glowstone.io.nbt.common;

import net.glowstone.io.nbt.NbtSerializer;
import net.glowstone.util.nbt.FloatTag;

public class HealthDNbt implements NbtSerializer<Double, FloatTag> {
    @Override
    public FloatTag serialize(Double obj) {
        return null;
    }

    @Override
    public Double deserialize(FloatTag tag) {
        return null;
    }
}

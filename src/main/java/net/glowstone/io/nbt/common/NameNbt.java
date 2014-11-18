package net.glowstone.io.nbt.common;

import net.glowstone.io.nbt.NbtSerializer;
import net.glowstone.util.nbt.StringTag;

public class NameNbt implements NbtSerializer<String, StringTag> {
    @Override
    public StringTag serialize(String obj) {
        return null;
    }

    @Override
    public String deserialize(StringTag tag) {
        return null;
    }
}

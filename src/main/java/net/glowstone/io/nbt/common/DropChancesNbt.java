package net.glowstone.io.nbt.common;

import net.glowstone.io.nbt.NbtSerializer;
import net.glowstone.util.nbt.FloatTag;
import net.glowstone.util.nbt.ListTag;

public class DropChancesNbt implements NbtSerializer<Integer[], ListTag<FloatTag>> {
    @Override
    public ListTag<FloatTag> serialize(Integer[] obj) {
        return null;
    }

    @Override
    public Integer[] deserialize(ListTag<FloatTag> tag) {
        return new Integer[0];
    }
}

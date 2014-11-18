package net.glowstone.io.nbt.common;

import net.glowstone.io.nbt.NbtSerializer;
import net.glowstone.util.nbt.IntTag;
import net.glowstone.util.nbt.ListTag;
import org.bukkit.util.Vector;

public class VectorNbt implements NbtSerializer<Vector, ListTag<IntTag>> {
    @Override
    public ListTag<IntTag> serialize(Vector obj) {
        return null;
    }

    @Override
    public Vector deserialize(ListTag<IntTag> tag) {
        return null;
    }
}

package net.glowstone.io.nbt;

import net.glowstone.util.nbt.Tag;

public interface NbtSerializer<T, K> {
    K serialize(T obj);

    T deserialize(K tag);
}

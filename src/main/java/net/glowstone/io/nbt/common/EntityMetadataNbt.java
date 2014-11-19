package net.glowstone.io.nbt.common;

import net.glowstone.entity.meta.MetadataIndex;
import net.glowstone.io.nbt.NbtSerializer;
import net.glowstone.util.nbt.Tag;

/**
 * Represents an NBT serializer for converting {@link net.glowstone.entity.meta.MetadataIndex}
 * fields to NBT tags, and back. This will assume that the supplied
 */
public class EntityMetadataNbt implements NbtSerializer<MetadataIndex, Tag>{

    @Override
    public Tag serialize(MetadataIndex obj) {
        return null;
    }

    @Override
    public void deserialize(Tag tag, MetadataIndex into) {

    }
}

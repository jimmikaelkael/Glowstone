package net.glowstone.util.nbt.data;

import net.glowstone.util.nbt.NbtData;
import org.bukkit.entity.Entity;

/**
 * Represents NBT data for getting the vanilla entity type from a specified
 * entity.
 */
public class EntityTypeNbtData implements NbtData<String> {

    private Entity entity;

    public EntityTypeNbtData(Entity entity) {
        if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
        this.entity = entity;
    }

    @Override
    public String getData() {
        // Deprecated, but it represents the NBT name of the entity
        return entity.getType().getName();
    }
}

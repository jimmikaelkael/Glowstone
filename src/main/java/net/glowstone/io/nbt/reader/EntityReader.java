package net.glowstone.io.nbt.reader;

import net.glowstone.GlowWorld;
import net.glowstone.entity.GlowEntity;
import net.glowstone.entity.objects.GlowItem;
import net.glowstone.io.nbt.NbtSerializer;
import net.glowstone.io.nbt.common.AnnotatedObjectNbt;
import net.glowstone.io.nbt.common.LocationNbt;
import net.glowstone.util.nbt.CompoundTag;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Used for deserializing NBT entity information.
 */
public final class EntityReader {

    private static final Map<EntityType, Class<? extends GlowEntity>> BY_TYPE;

    static {
        Map<EntityType, Class<? extends GlowEntity>> register = new HashMap<>();

        register.put(EntityType.DROPPED_ITEM, GlowItem.class);

        BY_TYPE = Collections.unmodifiableMap(register);
    }

    private GlowWorld world;

    public EntityReader(GlowWorld world) {
        if (world == null) throw new IllegalArgumentException("World cannot be null");
        this.world = world;
    }

    /**
     * Loads an entity from the given tag. This will assume that the given tag
     * represents a valid entity. Easily corrected deserialization problems
     * will cause this to return null instead of raising an exception. This will
     * populate the entity as required (as per the compound tag given).
     *
     * @param tag The tag to load from, assumed non-null and valid.
     *
     * @return The loaded entity, or null
     */
    public GlowEntity load(CompoundTag tag) {
        if (!tag.isString("id")) return null; // Unknown tag structure

        // Find type and validate
        EntityType type = EntityType.fromName(tag.getString("id"));
        if (type == null || !BY_TYPE.containsKey(type)) return null; // Unknown entity

        // Construct location
        Location location = LocationNbt.deserialize(tag);
        if (location == null) return null; // Something really bad happened
        location.setWorld(world);

        // Construct entity
        Class<? extends GlowEntity> clazz = BY_TYPE.get(type);
        GlowEntity entity;
        try {
            Constructor<? extends GlowEntity> constructor = clazz.getConstructor(Location.class);
            constructor.setAccessible(true);
            entity = constructor.newInstance(location);
        } catch (Exception e) {
            throw new NbtSerializer.NbtException("Failed to create entity " + clazz.getName(), e);
        }

        // Populate entity properties
        NbtSerializer<Object, CompoundTag> serializer = new AnnotatedObjectNbt();
        serializer.deserialize(tag, entity);

        // Return the final entity
        return entity;
    }

}

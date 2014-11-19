package net.glowstone.io.nbt;

import net.glowstone.entity.meta.MetadataIndex;
import net.glowstone.io.nbt.common.*;
import net.glowstone.util.nbt.NBT;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Handles the central registration for {@link net.glowstone.io.nbt.NbtSerializer}s
 * for use in (de)serialization of objects.
 */
public final class NbtRegistration {

    private static final Map<Class<?>, NbtSerializer> BY_TYPE;
    private static final NbtSerializer TAGGED_SERIALIZER = new AnnotatedObjectNbt();

    static {
        Map<Class<?>, NbtSerializer> registrations = new HashMap<>();

        // Note: Do not register AnnotatedObjectNbt as it will (likely) never be used through
        // the collection.

        registrations.put(GameMode.class, new GameModeNbt());
        registrations.put(Location.class, new LocationNbt());
        registrations.put(PotionEffect.class, new PotionEffectNbt());
        registrations.put(UUID.class, new UuidNbt());
        registrations.put(Vector.class, new VectorNbt());
        registrations.put(ItemStack.class, new ItemStackNbt());
        registrations.put(MetadataIndex.class, new EntityMetadataNbt());

        BY_TYPE = Collections.unmodifiableMap(registrations);
    }

    private NbtRegistration() {
    }

    /**
     * Gets the applicable serializer for the specified object. If the supplied
     * object cannot be matched to a registered serializer, null is returned.
     *
     * @param obj The object to find a serializer for, cannot be null
     *
     * @return The found serializer, or null if none found
     */
    public static NbtSerializer getSerializer(Object obj) {
        if (obj == null) throw new IllegalArgumentException("Object cannot be null");
        return getSerializer(obj.getClass());
    }

    /**
     * Gets the applicable serializer for the specified class. If the supplied
     * class cannot be matched to a registered serializer, null is returned.
     * @param clazz The class to find a serializer for, cannot be null
     * @return The found serializer, or null if none found
     */
    public static NbtSerializer getSerializer(Class<?> clazz){
        if (clazz == null) throw new IllegalArgumentException("Class cannot be null");

        NbtSerializer serializer = null;
        if (BY_TYPE.containsKey(clazz)) {
            serializer = BY_TYPE.get(clazz);
        } else if (isTagged(clazz)) {
            serializer = TAGGED_SERIALIZER;
        }

        return serializer;
    }

    /**
     * Determines if the supplied class is able to be (de)serialized as a
     * tagged object (an object annotated with {@link net.glowstone.util.nbt.NBT})
     *
     * @param clazz The class to lookup, cannot be null
     *
     * @return True if the supplied class is "tagged", false otherwise
     */
    public static boolean isTagged(Class<?> clazz) {
        if (clazz == null) throw new IllegalArgumentException("Class cannot be null");

        Field[] fields = clazz.getFields();
        if (fields != null) {
            for (Field field : fields) {
                if (field.isAnnotationPresent(NBT.class)) {
                    return true;
                }
            }
        }
        return false;
    }

}
package net.glowstone.io.nbt;

import net.glowstone.io.nbt.common.*;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Handles the central registration for {@link net.glowstone.io.nbt.NbtSerializer}s
 * for use in (de)serialization of objects.
 */
public final class NbtRegistration {

    private static final Map<Class<?>, Class<? extends NbtSerializer>> BY_TYPE;

    static {
        Map<Class<?>, Class<? extends NbtSerializer>> registrations = new HashMap<>();

        // Note: Do not register TaggedObjectNbt as it will (likely) never be used through
        // the collection.

        registrations.put(GameMode.class, GameModeNbt.class);
        registrations.put(Location.class, LocationNbt.class);
        registrations.put(PotionEffect.class, PotionEffectNbt.class);
        registrations.put(UUID.class, UuidNbt.class);
        registrations.put(Vector.class, VectorNbt.class);

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
    public NbtSerializer getSerializer(Object obj) {
        if (obj == null) throw new IllegalArgumentException("Object cannot be null");

        // TODO: Implementation

        return null;
    }

}

package net.glowstone.io.nbt;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles the central registration for {@link net.glowstone.io.nbt.NbtSerializer}s
 * for use in (de)serialization of objects.
 */
public final class NbtRegistration {

    private static final Map<Class<?>, Class<? extends NbtSerializer>> BY_TYPE;

    static {
        Map<Class<?>, Class<? extends NbtSerializer>> registrations = new HashMap<>();

        // TODO: Registrations

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

package net.glowstone.io.nbt.common;

import net.glowstone.io.nbt.NbtSerializer;
import net.glowstone.util.nbt.IntTag;
import org.bukkit.GameMode;

public class GameModeNbt implements NbtSerializer<GameMode, IntTag> {

    @Override
    public IntTag serialize(GameMode obj) {
        if (obj == null) throw new IllegalArgumentException("GameMode cannot be null");
        return new IntTag(obj.getValue());
    }

    @Override
    public void deserialize(IntTag tag, GameMode into) {
        // Impossible
        throw new UnsupportedOperationException("Cannot deserialize into an enum");
    }

    /**
     * Deserializes an integer tag into a gamemode.
     *
     * @param tag The tag to deserialize, cannot be null
     *
     * @return The gamemode deserialized, or null if no match
     */
    public static GameMode deserialize(IntTag tag) {
        if (tag == null) throw new IllegalArgumentException("Tag cannot be null");

        for (GameMode mode : GameMode.values()) {
            if (mode.getValue() == tag.getValue())
                return mode;
        }

        return null;
    }

}

package net.glowstone.io.nbt.common;

import net.glowstone.io.nbt.NbtSerializer;
import net.glowstone.util.nbt.IntTag;
import org.bukkit.GameMode;

public class GameModeNbt implements NbtSerializer<GameMode, IntTag> {
    @Override
    public IntTag serialize(GameMode obj) {
        return null;
    }

    @Override
    public GameMode deserialize(IntTag tag) {
        return null;
    }
}

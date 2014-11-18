package net.glowstone.io.nbt.common;

import net.glowstone.util.nbt.CompoundTag;
import net.glowstone.io.nbt.NbtSerializer;
import org.bukkit.Location;

public class LocationNbt implements NbtSerializer<Location, CompoundTag> {
    @Override
    public CompoundTag serialize(Location obj) {
        return null;
    }

    @Override
    public Location deserialize(CompoundTag tag) {
        return null;
    }
}

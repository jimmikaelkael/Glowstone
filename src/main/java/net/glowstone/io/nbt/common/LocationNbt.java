package net.glowstone.io.nbt.common;

import net.glowstone.io.nbt.NbtSerializer;
import net.glowstone.util.nbt.CompoundTag;
import net.glowstone.util.nbt.TagType;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationNbt implements NbtSerializer<Location, CompoundTag> {

    @Override
    public CompoundTag serialize(Location obj) {
        if (obj == null) throw new IllegalArgumentException("Location cannot be null");

        CompoundTag values = new CompoundTag();
        List<Double> pos = new ArrayList<>();
        List<Float> rot = new ArrayList<>();

        pos.add(obj.getX());
        pos.add(obj.getY());
        pos.add(obj.getZ());

        rot.add(obj.getYaw());
        rot.add(obj.getPitch());

        values.putList("Pos", TagType.DOUBLE, pos);
        values.putList("Rot", TagType.FLOAT, rot);

        return values;
    }

    @Override
    public void deserialize(CompoundTag tag, Location into) {
        Location values = deserialize(tag);

        into.setX(values.getX());
        into.setY(values.getY());
        into.setZ(values.getZ());
        into.setYaw(values.getYaw());
        into.setPitch(values.getPitch());
    }

    /**
     * Deserializes a location from the supplied compound tag. If the tag is
     * invalid or is null, this returns null. This will never read world
     * information and therefore will return a location with position and
     * rotation information only.
     *
     * @param tag The tag to deserialize
     *
     * @return The location, or null if the tag is invalid
     */
    public static Location deserialize(CompoundTag tag) {
        if (tag == null || !tag.isList("Pos", TagType.DOUBLE) || !tag.isList("Rot", TagType.FLOAT))
            return null;

        List<Double> pos = tag.getList("Pos", TagType.DOUBLE);
        List<Float> rot = tag.getList("Rot", TagType.FLOAT);

        if (pos.size() != 3 || rot.size() != 2)
            return null;

        return new Location(null, pos.get(0), pos.get(1), pos.get(2), rot.get(0), rot.get(1));
    }
}

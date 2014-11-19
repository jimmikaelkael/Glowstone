package net.glowstone.io.nbt.common;

import net.glowstone.io.nbt.NbtRegistration;
import net.glowstone.io.nbt.NbtSerializer;
import net.glowstone.util.nbt.*;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Represents the NBT Serializer for processing {@link net.glowstone.util.nbt.NBT}
 * annotations. This will not overwrite values that have been previously set if the
 * supplied annotation is not annotated with <code>root = true</code>. An attempt
 * to overwrite will result in an {@link net.glowstone.io.nbt.NbtSerializer.NbtException}.
 * If the field is annotated with <code>root = true</code>, then this will permit
 * the overwriting of values without warning.
 */
public class AnnotatedObjectNbt implements NbtSerializer<Object, CompoundTag> {

    @Override
    public CompoundTag serialize(Object obj) {
        if (obj == null || !NbtRegistration.isTagged(obj.getClass()))
            throw new NbtException("Cannot serialize a non-tagged object, or null");

        CompoundTag tag = new CompoundTag();

        Field[] fields = obj.getClass().getFields();
        if (fields != null) {
            for (Field field : fields) {
                if (field.isAnnotationPresent(NBT.class)) {
                    field.setAccessible(true); // Just in case...

                    NBT info = field.getAnnotation(NBT.class);
                    Tag result;

                    // If a custom type is requested, we'll try to use that
                    try {
                        if (info.type() != Tag.class) {
                            result = toTag(field.get(obj), info.type());
                        } else { // else we'll default to trying to find the type and creating it that way
                            result = toTag(field.get(obj));
                        }
                    } catch (Exception e) {
                        throw new NbtException("Could not find value for field " + field.getName() + " in " + obj.getClass().getName() + " (requested tag type = " + info.type().getName() + ")");
                    }

                    // Check if serialization worked or not
                    if (result == null)
                        throw new NbtException("Could not convert field " + field.getName() + " in " + obj.getClass().getName() + " to tag");

                    // Check if we need to overwrite or not
                    if (result instanceof CompoundTag && info.root()) {
                        // We're allowed to "merge" using an overwrite strategy
                        CompoundTag comp = (CompoundTag) result;
                        Map<String, Tag> values = comp.getValue();

                        for (Map.Entry<String, Tag> value : values.entrySet()) {
                            tag.put(value.getKey(), value.getValue());
                        }
                    } else {
                        if (tag.containsKey(info.value())) {
                            throw new NbtException("Cannot overwrite tag " + info.value());
                        } else {
                            tag.put(info.value(), result);
                        }
                    }
                }
            }
        }

        if (tag.isEmpty()) throw new NbtException("No fields serialized");
        return tag;
    }

    @Override
    public void deserialize(CompoundTag tag, Object into) {
        // TODO
    }

    private Tag toTag(Object value) {
        // TODO: Lists and other collections
        Tag result = null;

        // Primitive check (we can be hopeful)
        for (TagType type : TagType.values()) {
            if (type.getValueClass() != null && type.getValueClass() == value.getClass()) {
                if (type == TagType.COMPOUND || type == TagType.LIST) {
                    throw new NbtException("Cannot construct a " + type.getName());
                } else {
                    try {
                        result = type.getConstructor().newInstance(value);
                    } catch (Exception e) {
                        throw new NbtException("Cannot convert " + value.getClass().getName() + " (" + value + ") to tag " + type.getName(), e);
                    }
                }
            }
        }

        // Now check if we need to go looking up the value
        if (result == null) {
            NbtSerializer serializer = NbtRegistration.getSerializer(value);
            if (serializer == null)
                throw new NbtException("Could not find serializer for " + value.getClass().getName() + " (" + value + ")");

            try {
                // This is somewhat dangerous, so we wrap it in a try/catch to catch any weird problems
                // with casting or other NbtExceptions that occur during use.
                result = (Tag) serializer.serialize(value);
            } catch (Exception e) {
                throw new NbtException("Exception parsing tag for " + value.getClass().getName() + " (" + value + ")", e);
            }
        }

        return result; // Null indicates "could not parse"
    }

    private Tag toTag(Object value, Class<? extends Tag> type) {
        if (type == CompoundTag.class || type == ListTag.class)
            throw new NbtException("Cannot construct a " + type.getName());
        try {
            return (Tag) type.getConstructors()[0].newInstance(value);
        } catch (Exception e) {
            throw new NbtException("Cannot convert " + value.getClass().getName() + " (" + value + ") to tag " + type.getName(), e);
        }
    }
}

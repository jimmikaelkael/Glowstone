package net.glowstone.util.nbt;

/**
 * Represents NBT data that cannot be represented by a simple field. This may
 * be used in place of a constant variable for special casing the stored value,
 * such as the case of storing the entity type.
 * <br/>
 * <br/>
 * Fields with this class will not be overwritten by deserialization. This is
 * intended to be used as semi-constant data (or complex data that cannot be
 * easily represented) for use in NBT serialization.
 * <br/>
 * <br/>
 * Uses of this NbtData class may include identity information so that the
 * deserializer may be informed as to what it is reading. If this is the
 * case, a custom deserializer should be used to first parse that information
 * before sending the job to a deserializer.
 *
 * @param <T> The type of data being stored
 */
public interface NbtData<T> {

    T getData();

}

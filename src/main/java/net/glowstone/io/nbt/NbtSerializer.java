package net.glowstone.io.nbt;

/**
 * Represents an NBT Serializer. This typed class is capable of converting
 * complex types to NBT tags where needed.
 *
 * @param <T> The type of object to (de)serialize as NBT
 * @param <K> The type of NBT Tag to use
 */
public interface NbtSerializer<T, K> {

    /**
     * Thrown if there is an issue with (de)serializing NBT data.
     */
    public class NbtException extends RuntimeException {

        public NbtException(){
            super();
        }

        public NbtException(String message){
            super(message);
        }

        public NbtException(Exception parent){
            super(parent);
        }

        public NbtException(String message, Exception parent){
            super(message, parent);
        }
    }

    /**
     * Serializes the supplied object into a tag. Implementations are expected
     * to reject null or otherwise invalid types.
     *
     * @param obj The object to convert, cannot be null.
     *
     * @return A tag representing the object, never null
     */
    K serialize(T obj);

    /**
     * Deserializes the supplied tag into an object. Implementations are
     * expected to reject null or otherwise invalid types.
     *
     * @param tag The tag to deserialize, cannot be null
     *
     * @return An object representing the tag, never null
     *
     * @throws net.glowstone.io.nbt.NbtSerializer.NbtException Thrown if the
     * supplied tag cannot be converted to a valid object.
     */
    T deserialize(K tag);
}

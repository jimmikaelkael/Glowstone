package net.glowstone.util.nbt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Assigns a field to be an NBT value when saving values. When applied to a
 * field in a class, that class becomes a compound tag (if applicable) and
 * the marked fields as "properties" in that compound tag.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NBT {

    /**
     * The default tag type to indicate "auto-determine".
     */
    public static final Class<? extends Tag> DEFAULT_TAG = Tag.class;

    /**
     * Gets or sets the NBT tag name.
     *
     * @return The NBT tag name
     */
    String value();

    /**
     * Gets or sets whether or not CompoundTags modify "this" level of the NBT.
     * If false, CompoundTags that are created will appear under the NBT tag
     * name supplied. If true, the NBT tag name is <strong>ignored</strong> to
     * allow for the entries in the CompoundTag to be applied to the "root"
     * level (ie: as though it was a field in the tagged class). Entries in the
     * returned CompoundTag will <strong>overwrite</strong> those that have
     * been already defined.
     * <br/>
     * <br/>
     * This does not affect non-CompoundTags.
     *
     * @return True if the resulting CompoungTag should be 'merged' with the
     * root-level NBT
     */
    boolean root() default false;

    /**
     * Gets or sets the tag type to force the annotated value into. If the type
     * {@link #DEFAULT_TAG} is supplied, the tag type will be determined
     * on-demand. If this is any other value, then the value will be placed
     * into the supplied tag type. If the value cannot be put into the
     * specified type (such as a String into an IntTag), an exception will be
     * raised upon being determined.
     *
     * @return The tag type to use
     */
    Class<? extends Tag> type() default DEFAULT_TAG;

    /**
     * Gets or sets whether or not this NBT tag may be missing from the NBT
     * file. If missing and this is true, the field is not modified. If
     * missing and this is false, the deserializer will fail. If the value
     * is not missing, the field will be set.
     *
     * @return True if this field is optional, false otherwise
     */
    boolean optional() default false;

    /**
     * Gets or sets whether or not this field is readonly. If true then
     * deserialization will not modify the field's contents. If false, then
     * deserialization may modify the field's contents. Fields of the type
     * {@link net.glowstone.util.nbt.NbtData} are always considered readonly.
     *
     * @return True if readonly, false otherwise
     */
    boolean readonly() default false;
}

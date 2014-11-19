package net.glowstone.io.nbt.common;

import net.glowstone.io.nbt.NbtSerializer;
import net.glowstone.util.nbt.CompoundTag;
import org.bukkit.potion.PotionEffect;

public class PotionEffectNbt implements NbtSerializer<PotionEffect, CompoundTag> {

    @Override
    public CompoundTag serialize(PotionEffect obj) {
        return null;
    }

    @Override
    public void deserialize(CompoundTag tag, PotionEffect into) {

    }

//    public PotionEffect deserialize(CompoundTag tag) {
//        // Note: This is a duplicate of what LivingEntityStore does for each
//        // PotionEffect. Possible to refactor this otherwise
//        if (!tag.isByte("Id") || !tag.isInt("Duration")) {
//            return null;
//        }
//
//        PotionEffectType type = PotionEffectType.getById(tag.getByte("Id"));
//        int duration = tag.getInt("Duration");
//        if (type == null || duration < 0) {
//            return null;
//        }
//        int amplifier = 0;
//        boolean ambient = false;
//
//        if (tag.isByte("Amplifier")) {
//            amplifier = tag.getByte("Amplifier");
//        }
//        if (tag.isByte("Ambient")) {
//            ambient = tag.getBool("Ambient");
//        }
//        // bool "ShowParticles"
//
//        return new PotionEffect(type, duration, amplifier, ambient);
//    }
}

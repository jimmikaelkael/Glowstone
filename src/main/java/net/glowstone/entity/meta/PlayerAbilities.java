package net.glowstone.entity.meta;

import net.glowstone.util.nbt.NBT;

/**
 * Represents the abilities a player can have, such as the ability to fly.
 */
public class PlayerAbilities {

    @NBT("walkSpeed")
    private float walkSpeed = 0.1f;

    @NBT("flySpeed")
    private float flySpeed = 0.05f;

    @NBT("mayBuild")
    private boolean canBuild;

    @NBT("instaBuild")
    private boolean canInstaBuild;

    @NBT("mayFly")
    private boolean canFly;

    @NBT("flying")
    private boolean flying;

    @NBT("invulnerable")
    private boolean invulnerable;

    // Note: Bukkit's API expects a walk/fly speed that is 2x vanilla. But in order to not break
    // the NBT serialization, they are maintained as "vanilla" values in the variable.

    public float getWalkSpeed() {
        return walkSpeed * 2f;
    }

    public void setWalkSpeed(float f) {
        walkSpeed = f / 2f;
    }

    public float getFlySpeed() {
        return flySpeed * 2f;
    }

    public void setFlySpeed(float f) {
        flySpeed = f / 2f;
    }

    public boolean canBuild() {
        return canBuild;
    }

    public void setCanBuild(boolean b) {
        canBuild = b;
    }

    public boolean canInstaBuild() {
        return canInstaBuild;
    }

    public void setCanInstaBuild(boolean b) {
        canInstaBuild = b;
    }

    public boolean canFly() {
        return canFly;
    }

    public void setCanFly(boolean b) {
        canFly = b;
    }

    public boolean isFlying() {
        return flying;
    }

    public void setFlying(boolean b) {
        flying = b;
    }

    public boolean isInvulnerable() {
        return invulnerable;
    }

    public void setInvulnerable(boolean b) {
        invulnerable = b;
    }

}

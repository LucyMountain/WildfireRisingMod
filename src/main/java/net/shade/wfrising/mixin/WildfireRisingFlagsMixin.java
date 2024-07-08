package net.shade.wfrising.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.shade.wfrising.accessor.LivingEntityAccessor;
import net.shade.wfrising.effects.WildfireRisingEffectFlags;
import net.shade.wfrising.effects.WildfireRisingStatusEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class WildfireRisingFlagsMixin extends Entity implements LivingEntityAccessor {
    @Shadow public abstract ItemStack getMainHandStack();
    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);
    @Shadow public abstract boolean damage(DamageSource source, float amount);
    @Shadow public abstract void kill();
    @Shadow public abstract Map<StatusEffect, StatusEffectInstance> getActiveStatusEffects();
    @Shadow public abstract ItemStack getOffHandStack();
    @Shadow public abstract boolean removeStatusEffect(StatusEffect type);
    @Shadow public abstract LivingEntity getLastAttacker();

    @Shadow private long lastDamageTime;

    @Unique
    @SuppressWarnings("WrongEntityDataParameterClass")
    private static final TrackedData<Integer> WILDFIRE_RISING_FLAGS = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public WildfireRisingFlagsMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "initDataTracker()V", at = @At("TAIL"))
    public void initDataTracker(CallbackInfo ci) {
        this.dataTracker.startTracking(WILDFIRE_RISING_FLAGS, 0);
    }

    @Override
    public void clearCurableStatusEffects() {
        Iterator<StatusEffectInstance> iterator = this.getActiveStatusEffects().values().iterator();
        while (iterator.hasNext()) {
            StatusEffectInstance statusEffectInstance = iterator.next();
            if (statusEffectInstance.getEffectType() instanceof WildfireRisingStatusEffect effect && effect.isIncurable()) {
                continue;
            }
            ((LivingEntityInvoker) this).invokeOnStatusEffectRemoved(statusEffectInstance);
            iterator.remove();
        }
    }

    @Override
    public boolean hasIncurableStatusEffects() {
        for (StatusEffectInstance statusEffectInstance : this.getActiveStatusEffects().values()) {
            if (statusEffectInstance.getEffectType() instanceof WildfireRisingStatusEffect effect && effect.isIncurable()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public long getLastDamageTime() {
        return lastDamageTime;
    }

    public boolean getWildfireRisingFlag(WildfireRisingEffectFlags flag) {
        return (this.dataTracker.get(WILDFIRE_RISING_FLAGS) & flag.getOffset()) != 0;
    }

    public void setWildfireRisingEffectFlag(WildfireRisingEffectFlags flag, boolean value) {
        int flags = this.dataTracker.get(WILDFIRE_RISING_FLAGS);
        if (value) {
            flags |= flag.getOffset();
        } else {
            flags &= ~flag.getOffset();
        }
        this.dataTracker.set(WILDFIRE_RISING_FLAGS, flags);
    }

    @Inject(method = "readCustomDataFromNbt(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("TAIL"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        this.dataTracker.set(WILDFIRE_RISING_FLAGS, nbt.getInt("WildifreRisingFlags"));
    }

    @Inject(method = "writeCustomDataToNbt(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("TAIL"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("WildifreRisingFlags", this.dataTracker.get(WILDFIRE_RISING_FLAGS));
    }
}

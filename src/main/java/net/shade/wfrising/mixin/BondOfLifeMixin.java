package net.shade.wfrising.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.shade.wfrising.effects.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class BondOfLifeMixin {
    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow public abstract Map<StatusEffect, StatusEffectInstance> getActiveStatusEffects();

    @Shadow public abstract boolean removeStatusEffect(StatusEffect type);

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

    @ModifyVariable(at = @At("HEAD"), method = "heal")
    private float injected(float amount) {
        if (this.hasStatusEffect(ModEffects.BOND_OF_LIFE)) {
            int bond_amplifier = this.getActiveStatusEffects().get(ModEffects.BOND_OF_LIFE).getAmplifier();
            float bond_strength = (bond_amplifier + 1) * 1.0F;
            if (bond_strength > amount) {
                int new_amplifier = (int) (bond_amplifier - amount);
                this.removeStatusEffect(ModEffects.BOND_OF_LIFE);
                this.addStatusEffect(new StatusEffectInstance(ModEffects.BOND_OF_LIFE, -1, new_amplifier, false, false));
                return 0.0F;
            }else{
                this.removeStatusEffect(ModEffects.BOND_OF_LIFE);
                return amount - bond_strength;
            }
        }
        return amount;
    }
}

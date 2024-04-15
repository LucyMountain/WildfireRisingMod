package net.shade.wfrising.effects.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageEffects;
import net.minecraft.entity.damage.DamageScaling;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class BloodDebtEffect extends StatusEffect {
    public BloodDebtEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity livingEntity, int amplifier) {
        livingEntity.damage(livingEntity.getDamageSources().magic(), (float)(6));
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 25 == 0;
    }
}
package net.shade.wfrising.effects.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageEffects;
import net.minecraft.entity.damage.DamageScaling;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.shade.wfrising.effects.WildfireRisingEffectFlags;
import net.shade.wfrising.effects.WildfireRisingStatusEffect;

public class BloodDebtEffect extends WildfireRisingStatusEffect {
    public BloodDebtEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color, true, WildfireRisingEffectFlags.BLOOD_DEBT, true);
    }

    @Override
    public void applyUpdateEffect(LivingEntity livingEntity, int amplifier) {
        livingEntity.damage(livingEntity.getDamageSources().magic(), (float)(4));
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 40 == 0;
    }
}
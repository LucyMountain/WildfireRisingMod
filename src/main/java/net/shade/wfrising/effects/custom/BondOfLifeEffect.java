package net.shade.wfrising.effects.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.shade.wfrising.effects.ModEffects;

public class BondOfLifeEffect extends StatusEffect {
    public BondOfLifeEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity livingEntity, int amplifier) {
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {return true;}
}
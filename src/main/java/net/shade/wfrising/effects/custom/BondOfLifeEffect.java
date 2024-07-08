package net.shade.wfrising.effects.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.shade.wfrising.effects.ModEffects;
import net.shade.wfrising.effects.WildfireRisingEffectFlags;
import net.shade.wfrising.effects.WildfireRisingStatusEffect;

public class BondOfLifeEffect extends WildfireRisingStatusEffect {
    public BondOfLifeEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color, true, WildfireRisingEffectFlags.BOND_OF_LIFE, false);
    }

    @Override
    public void applyUpdateEffect(LivingEntity livingEntity, int amplifier) {
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {return true;}
}
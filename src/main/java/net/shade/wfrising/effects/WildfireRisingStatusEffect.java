package net.shade.wfrising.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.shade.wfrising.accessor.LivingEntityAccessor;

public class WildfireRisingStatusEffect extends StatusEffect {

    private final boolean shouldApplyUpdateEffect;
    private final WildfireRisingEffectFlags flag;
    private final boolean curable;

    public WildfireRisingStatusEffect(StatusEffectCategory statusEffectCategory, int color, boolean applyUpdateEffect, WildfireRisingEffectFlags flag, boolean curable) {
        super(statusEffectCategory, color);
        this.shouldApplyUpdateEffect = applyUpdateEffect;
        this.flag = flag;
        this.curable = curable;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return this.shouldApplyUpdateEffect;
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (flag != null) {
            ((LivingEntityAccessor) entity).setWildfireRisingEffectFlag(flag, false);
        }
        super.onRemoved(entity, attributes, amplifier);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (flag != null) {
            ((LivingEntityAccessor) entity).setWildfireRisingEffectFlag(flag, true);
        }
        super.onApplied(entity, attributes, amplifier);
    }

    public boolean isIncurable() {
        return !curable;
    }
}

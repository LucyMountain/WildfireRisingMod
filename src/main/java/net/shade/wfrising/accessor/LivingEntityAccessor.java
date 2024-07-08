package net.shade.wfrising.accessor;

import net.shade.wfrising.effects.WildfireRisingEffectFlags;

public interface LivingEntityAccessor {
    boolean getWildfireRisingFlag(WildfireRisingEffectFlags flag);
    void setWildfireRisingEffectFlag(WildfireRisingEffectFlags flag, boolean value);
    void clearCurableStatusEffects();
    boolean hasIncurableStatusEffects();
    long getLastDamageTime();
}

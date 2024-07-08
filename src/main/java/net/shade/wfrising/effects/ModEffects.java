package net.shade.wfrising.effects;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.shade.wfrising.WildfireRisingMod;
import net.shade.wfrising.effects.custom.BloodDebtEffect;
import net.shade.wfrising.effects.custom.BondOfLifeEffect;

public class ModEffects {

    public static final StatusEffect BOND_OF_LIFE = registerEffect("bond_of_life",
            new BondOfLifeEffect(StatusEffectCategory.HARMFUL, 11141120).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, "CB3F55D3-645C-4F38-A497-9C13A33DB5CF", 0.025, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final StatusEffect BLOOD_DEBT = registerEffect("blood_debt",
            new BloodDebtEffect(StatusEffectCategory.HARMFUL, 11141120).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, "FA233E1C-4180-4865-B01B-BCCE9785ACA3", -0.1, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));

    private static StatusEffect registerEffect(String name, StatusEffect statusEffect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(WildfireRisingMod.MOD_ID, name), statusEffect);
    }

    public static void registerModEffects() {
        WildfireRisingMod.LOGGER.info("Registering Mod Effects for " + WildfireRisingMod.MOD_ID);
    }
}

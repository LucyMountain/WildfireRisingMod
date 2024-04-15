package net.shade.wfrising.effects;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.shade.wfrising.WildfireRisingMod;
import net.shade.wfrising.effects.custom.BloodDebtEffect;
import net.shade.wfrising.effects.custom.EtherealGlareEffect;
import net.shade.wfrising.item.ModItems;

public class ModEffects {

    public static final StatusEffect ETHEREAL_GLARE = registerEffect("ethereal_glare",
            new EtherealGlareEffect(StatusEffectCategory.HARMFUL, 11141290));

    public static final StatusEffect CELESTIAL_RADIANCE = registerEffect("celestial_radiance",
            new EtherealGlareEffect(StatusEffectCategory.HARMFUL, 16755200));

    public static final StatusEffect BLOOD_DEBT = registerEffect("blood_debt",
            new BloodDebtEffect(StatusEffectCategory.HARMFUL, 11141120));

    private static StatusEffect registerEffect(String name, StatusEffect statusEffect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(WildfireRisingMod.MOD_ID, name), statusEffect);
    }

    public static void registerModEffects() {
        WildfireRisingMod.LOGGER.info("Registering Mod Effects for " + WildfireRisingMod.MOD_ID);
    }
}
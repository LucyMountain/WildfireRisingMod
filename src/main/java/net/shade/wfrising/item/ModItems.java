package net.shade.wfrising.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.shade.wfrising.WildfireRisingMod;
import net.shade.wfrising.item.custom.AetherfallWeaponItem;
import net.shade.wfrising.item.custom.CrimsonMoonsSemblanceWeaponItem;

public class ModItems {

    public static final Item CRIMSON_MOONS_SEMBLANCE = registerItem("crimson_moons_semblance",
            new CrimsonMoonsSemblanceWeaponItem(new FabricItemSettings().maxCount(1)));
    public static final Item AETHERFALL = registerItem("aetherfall",
            new AetherfallWeaponItem(new FabricItemSettings().maxCount(1)));

    private static void addItesToIngredientTabItemGroup(FabricItemGroupEntries entries) {
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(WildfireRisingMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        WildfireRisingMod.LOGGER.info("Registering Mod Items for " + WildfireRisingMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItesToIngredientTabItemGroup);
    }
}

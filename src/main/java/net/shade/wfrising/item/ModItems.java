package net.shade.wfrising.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.shade.wfrising.WildfireRisingMod;
import net.shade.wfrising.item.custom.CrimsonMoonsSemblanceWeaponItem;
import net.shade.wfrising.item.custom.PrejudiceWeaponItem;
import net.shade.wfrising.item.custom.PrideWeaponItem;

public class ModItems {

    public static final Item CRIMSON_MOONS_SEMBLANCE = registerItem("crimson_moons_semblance",
            new CrimsonMoonsSemblanceWeaponItem(new FabricItemSettings()));
    public static final Item PRIDE = registerItem("pride",
            new PrideWeaponItem(new FabricItemSettings()));
    public static final Item PREJUDICE = registerItem("prejudice",
            new PrejudiceWeaponItem(new FabricItemSettings()));

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

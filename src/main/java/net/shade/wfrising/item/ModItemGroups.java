package net.shade.wfrising.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.shade.wfrising.WildfireRisingMod;

public class ModItemGroups {
    public static final ItemGroup WILDFIRE_RISING_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(WildfireRisingMod.MOD_ID, "wildfire_rising"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.wildfire_rising"))
                    .icon(() -> new ItemStack(ModItems.CRIMSON_MOONS_SEMBLANCE)).entries((displayContext, entries) -> {
                        entries.add(ModItems.CRIMSON_MOONS_SEMBLANCE);
                        entries.add(ModItems.PRIDE);
                        entries.add(ModItems.PREJUDICE);
                    }).build());

    public static void registerItemGroups() {
        WildfireRisingMod.LOGGER.info("Registering Item Groups for " + WildfireRisingMod.MOD_ID);
    }
}

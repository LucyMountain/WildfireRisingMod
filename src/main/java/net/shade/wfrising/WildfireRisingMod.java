package net.shade.wfrising;

import net.fabricmc.api.ModInitializer;

import net.shade.wfrising.effects.ModEffects;
import net.shade.wfrising.item.ModItemGroups;
import net.shade.wfrising.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WildfireRisingMod implements ModInitializer {
	public static final String MOD_ID = "wfrising";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModEffects.registerModEffects();
	}
}
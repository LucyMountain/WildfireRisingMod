package net.shade.wfrising;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

import static net.shade.wfrising.item.ModItems.CRIMSON_MOONS_SEMBLANCE;

public class WildfireRisingModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelPredicateProviderRegistry.register(CRIMSON_MOONS_SEMBLANCE, new Identifier("form"), (itemStack, clientWorld, livingEntity, id) -> {
            return itemStack.hasNbt() ? (float)itemStack.getNbt().getInt("Form") : 0.0F;
        });
    }
}

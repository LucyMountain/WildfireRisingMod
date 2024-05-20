package net.shade.wfrising.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.shade.wfrising.WildfireRisingMod;
import net.shade.wfrising.client.render.BloodDebtRenderer;
import net.shade.wfrising.effects.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class BloodDebtRendererMixin {

    private static final Identifier ICONS = new Identifier("textures/gui/icons.png");

    @Inject(method = "render", at = @At("TAIL"))
    private void injected(AbstractClientPlayerEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (entity.hasStatusEffect(ModEffects.BLOOD_DEBT)) {
            BloodDebtRenderer.render(matrices, vertexConsumers, entity.getPos(), entity);
            WildfireRisingMod.LOGGER.info("cHECK");
        }
    }
}

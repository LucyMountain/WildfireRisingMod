package net.shade.wfrising.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.util.Identifier;
import net.shade.wfrising.WildfireRisingMod;
import net.shade.wfrising.accessor.LivingEntityAccessor;
import net.shade.wfrising.client.render.BloodDebtRenderer;
import net.shade.wfrising.effects.ModEffects;
import net.shade.wfrising.effects.WildfireRisingEffectFlags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class BloodDebtRendererMixin {

    private static final Identifier ICONS = new Identifier("textures/gui/icons.png");

    @Inject(method = "render", at = @At("TAIL"))
    private void injected(LivingEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        LivingEntityAccessor entityAccessor = (LivingEntityAccessor) livingEntity;
        if (entityAccessor.getWildfireRisingFlag(WildfireRisingEffectFlags.BLOOD_DEBT)) {
            BloodDebtRenderer.render(matrixStack, vertexConsumerProvider, livingEntity);
        }
    }
}

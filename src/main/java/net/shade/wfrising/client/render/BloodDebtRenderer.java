package net.shade.wfrising.client.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.shade.wfrising.WildfireRisingMod;
import net.shade.wfrising.util.RenderUtils;

import java.util.Objects;

public interface BloodDebtRenderer {
    Identifier TEXTURE = WildfireRisingMod.createId("textures/entity/blood_debt.png");

    static void render(MatrixStack matrices, VertexConsumerProvider vertices, LivingEntity livingEntity) {
        var player = MinecraftClient.getInstance().player;
        var tickDelta = MinecraftClient.getInstance().getTickDelta();
        if (player == null) {return;}
        var direction = livingEntity.getCameraPosVec(tickDelta).subtract(player.getCameraPosVec(tickDelta));
        var rx = (float) -Math.atan2(direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z));
        var ry = (float) -Math.atan2(direction.x, direction.z);
        matrices.push();
        matrices.translate(0.D, 1.0D, 0.0D);
        var dispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        matrices.multiply(dispatcher.getRotation());
        matrices.translate(0.0D, 0.0D, -0.75D);
        var consumer = vertices.getBuffer(RenderLayer.getEntityCutout(TEXTURE));
        RenderUtils.drawBillboard(consumer, matrices, 0xF000F0, 0.9F, 0.9F, 0xC8FFFFFF);
        matrices.pop();
    }
}

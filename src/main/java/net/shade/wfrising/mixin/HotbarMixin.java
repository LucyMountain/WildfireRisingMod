package net.shade.wfrising.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.shade.wfrising.WildfireRisingMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(InGameHud.class)
public abstract class HotbarMixin {
    @Shadow
    static final Identifier ICONS = new Identifier("textures/gui/icons.png");

    private void drawBondedHeart(DrawContext context, int x, int y, boolean halfHeart) {
        context.drawTexture(ICONS, x, y, 16 + (halfHeart ? 1 : 0) * 9, 54, 9, 9);
    }

    @Inject(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;getMatrices()Lnet/minecraft/client/util/math/MatrixStack;"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void injected(float tickDelta, DrawContext context, CallbackInfo ci, PlayerEntity playerEntity, ItemStack itemStack, Arm arm, int i, int j, int k) {
        WildfireRisingMod.LOGGER.info("hihi");
    }
}



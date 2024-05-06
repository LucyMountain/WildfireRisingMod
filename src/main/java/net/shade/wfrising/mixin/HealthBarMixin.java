package net.shade.wfrising.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.shade.wfrising.effects.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class HealthBarMixin {
    @Shadow
    static final Identifier ICONS = new Identifier("textures/gui/icons.png");
    private void drawBondedHeart(DrawContext context, int x, int y, boolean halfHeart) {
        context.drawTexture(ICONS, x, y, 16 + (halfHeart ? 1 : 0) * 9, 54, 9, 9);
    }
    @Inject(method = "renderHealthBar", at = @At("TAIL"))
    private void injected(DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci) {
        if (player.hasStatusEffect(ModEffects.BOND_OF_LIFE)) {
            int bonded_amount = player.getActiveStatusEffects().get(ModEffects.BOND_OF_LIFE).getAmplifier() + 1;
            int full_hearts = bonded_amount / 2;
            for(int h = 9; h > 9 - full_hearts; --h) {
                this.drawBondedHeart(context, x + h * 8, y, false);
            }
            if (bonded_amount - (2 * full_hearts) > 0) {
                this.drawBondedHeart(context, x + (9 - full_hearts) * 8, y, true);
            }
        }
    }
}


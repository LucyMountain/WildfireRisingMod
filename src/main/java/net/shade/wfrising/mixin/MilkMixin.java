package net.shade.wfrising.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.MilkBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(MilkBucketItem.class)
public abstract class MilkMixin {
    // todo: fix this
    @Inject(method = "finishUsing", at = @At("HEAD"))
    private void injected {

    }
}

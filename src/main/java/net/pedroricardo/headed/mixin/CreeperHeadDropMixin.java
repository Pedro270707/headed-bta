package net.pedroricardo.headed.mixin;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityCreeper;
import net.minecraft.src.Item;
import net.pedroricardo.headed.Headed;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityCreeper.class)
public abstract class CreeperHeadDropMixin {
    @Inject(method = "onDeath", at = @At("TAIL"), remap = false)
    public void onDeath(Entity entity, CallbackInfo ci) {
        if (entity instanceof EntityCreeper) {
            if (((EntityCreeper)entity).getPowered()) {
                entity.dropItem(Item.itemsList[Headed.IDs.CREEPER_HEAD].itemID, 1);
            }
        }
    }
}

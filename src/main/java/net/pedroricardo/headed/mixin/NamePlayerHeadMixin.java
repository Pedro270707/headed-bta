package net.pedroricardo.headed.mixin;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.pedroricardo.headed.Headed;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemStack.class, remap = false)
public abstract class NamePlayerHeadMixin {
    @Shadow public abstract Item getItem();

    @Inject(method = "canItemBeRenamed", at = @At("HEAD"), cancellable = true)
    public void canItemBeRenamed(CallbackInfoReturnable<Boolean> cir) {
        if (((ItemStack)(Object)this).getItem() == Item.itemsList[Headed.IDs.PLAYER_HEAD]) {
            cir.setReturnValue(true);
        }
    }
}

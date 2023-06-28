package net.pedroricardo.headed.mixin;

import net.minecraft.src.ItemStack;
import net.minecraft.src.SlotArmor;
import net.pedroricardo.headed.item.HeadedSkullItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SlotArmor.class)
public class PutSkullOnHeadMixin {

    @Mixin(SlotArmor.class)
    private interface ArmorTypeAccessor {
        @Accessor(value = "armorType", remap = false)
        int armorType();
    }

    @Inject(method = "canPutStackInSlot", at = @At("HEAD"), cancellable = true, remap = false)
    public void canPutStackInSlot(ItemStack itemstack, CallbackInfoReturnable<Boolean> cir) {
        if (itemstack.getItem() instanceof HeadedSkullItem) {
            cir.setReturnValue(((ArmorTypeAccessor)((SlotArmor)(Object)this)).armorType() == 0);
        }
    }
}

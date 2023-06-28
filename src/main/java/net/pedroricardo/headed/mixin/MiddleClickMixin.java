package net.pedroricardo.headed.mixin;

import net.minecraft.src.*;
import net.pedroricardo.headed.Headed;
import net.pedroricardo.headed.block.HeadedSkullBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityPlayer.class, remap = false)
public class MiddleClickMixin {
    @Inject(method = "middleClickBlock", at = @At("HEAD"), cancellable = true)
    public void middleClickBlock(int x, int y, int z, CallbackInfo ci) {
        TileEntity blockEntity = ((EntityPlayer)(Object)this).worldObj.getBlockTileEntity(x, y, z);
        if (blockEntity instanceof HeadedSkullBlockEntity) {
            HeadedSkullBlockEntity skullBlockEntity = (HeadedSkullBlockEntity)blockEntity;
            ItemStack itemStack;
            switch (skullBlockEntity.getSkullType()) {
                case "skeleton":
                    itemStack = new ItemStack(Item.itemsList[Headed.IDs.SKELETON_SKULL]);
                    break;
                case "creeper":
                    itemStack = new ItemStack(Item.itemsList[Headed.IDs.CREEPER_HEAD]);
                    break;
                case "player":
                    itemStack = new ItemStack(Item.itemsList[Headed.IDs.PLAYER_HEAD]);
                    if (skullBlockEntity.getSkullOwner() != null && !skullBlockEntity.getSkullOwner().isEmpty()) {
                        itemStack.tag.setBoolean("overrideName", true);
                        itemStack.tag.setString("name", skullBlockEntity.getSkullOwner());
                    }
                    break;
                default:
                    itemStack = new ItemStack(Item.itemsList[Headed.IDs.ZOMBIE_HEAD]);
                    break;
            }
            ((EntityPlayer)(Object)this).inventory.setCurrentItem(itemStack, ((EntityPlayer)(Object)this).getGamemode().hasMiddleClick);
            ci.cancel();
        }
    }
}

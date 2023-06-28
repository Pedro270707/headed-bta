package net.pedroricardo.headed.mixin;

import net.minecraft.src.*;
import net.pedroricardo.headed.Headed;
import net.pedroricardo.headed.block.model.HeadedSkullModel;
import net.pedroricardo.headed.item.HeadedSkullItem;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderPlayer.class)
public class RenderSkullOnHeadMixin {
    private final HeadedSkullModel skullModel = new HeadedSkullModel();

    @Mixin(RenderPlayer.class)
    private interface ModelAccessor {
        @Accessor(value = "modelBipedMain", remap = false)
        ModelBiped modelBipedMain();
    }

    @Mixin(Render.class)
    private interface RenderManagerAccessor {
        @Accessor(value = "renderManager", remap = false)
        RenderManager renderManager();
    }

    @Inject(method = "setArmorModel", at = @At("RETURN"), cancellable = true, remap = false)
    private void setArmorModel(EntityPlayer entityplayer, int i, float f, CallbackInfoReturnable<Boolean> cir) {
        ItemStack itemStack = entityplayer.inventory.armorItemInSlot(3 - i);
        if (itemStack != null) {
            Item item = itemStack.getItem();
            if (item instanceof HeadedSkullItem) {
                HeadedSkullItem skullItem = (HeadedSkullItem)item;
                if (item == Item.itemsList[Headed.IDs.ZOMBIE_HEAD] || item == Item.itemsList[Headed.IDs.SKELETON_SKULL] || item == Item.itemsList[Headed.IDs.CREEPER_HEAD]) {
                    if (item == Item.itemsList[Headed.IDs.SKELETON_SKULL])
                        ((LoadTextureInvoker)((RenderPlayer)(Object)this)).setLoadTexture("/mob/skeleton.png");
                    else if (item == Item.itemsList[Headed.IDs.CREEPER_HEAD])
                        ((LoadTextureInvoker)((RenderPlayer)(Object)this)).setLoadTexture("/mob/creeper.png");
                    else
                        ((LoadTextureInvoker)((RenderPlayer)(Object)this)).setLoadTexture("/mob/zombie.png");

                    ((RenderPlayer)(Object)this).setRenderPassModel(this.skullModel);
                    cir.setReturnValue(true);
                }
            }
        }
    }

    @Inject(method = "renderSpecials", at = @At("HEAD"), remap = false)
    protected void renderSpecials(EntityPlayer player, float f, CallbackInfo ci) {
        ItemStack itemStack = player.inventory.armorItemInSlot(3);
        if (itemStack != null && itemStack.getItem() instanceof HeadedSkullItem) {
            GL11.glPushMatrix();
            ((ModelAccessor)((RenderPlayer)(Object)this)).modelBipedMain().bipedHead.postRender(0.0625F);
            float f1 = 1.125F;
            GL11.glTranslatef(0.0F, -0.8125F, 0.0F);
            GL11.glScalef(f1, f1, f1);
            this.skullModel.render();
            GL11.glPopMatrix();
        }
    }
}

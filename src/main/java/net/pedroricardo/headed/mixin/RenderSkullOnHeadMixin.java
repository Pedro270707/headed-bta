package net.pedroricardo.headed.mixin;

import net.minecraft.src.*;
import net.pedroricardo.headed.Headed;
import net.pedroricardo.headed.block.HeadedSkullBlockRenderer;
import net.pedroricardo.headed.block.model.HeadedSkullModel;
import net.pedroricardo.headed.item.HeadedSkullItem;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RenderPlayer.class, remap = false)
public class RenderSkullOnHeadMixin {
    private final HeadedSkullModel skullModel = new HeadedSkullModel();

    @Mixin(value = RenderPlayer.class, remap = false)
    private interface ModelAccessor {
        @Accessor("modelBipedMain")
        ModelBiped modelBipedMain();

        @Accessor("modelArmor")
        ModelBiped modelArmor();
    }

    @Inject(method = "renderSpecials", at = @At("HEAD"))
    protected void renderSpecials(EntityPlayer player, float f, CallbackInfo ci) {
        ItemStack itemStack = player.inventory.armorItemInSlot(3);
        if (itemStack != null && itemStack.getItem() instanceof HeadedSkullItem) {
            Item item = itemStack.getItem();

            this.skullModel.skullLayer.showModel = false;
            this.skullModel.leftEar.showModel = false;
            this.skullModel.rightEar.showModel = false;

            if (item == Item.itemsList[Headed.IDs.SKELETON_SKULL])
                ((LoadTextureInvoker)((RenderPlayer)(Object)this)).setLoadTexture("/mob/skeleton.png");
            else if (item == Item.itemsList[Headed.IDs.CREEPER_HEAD])
                ((LoadTextureInvoker)((RenderPlayer)(Object)this)).setLoadTexture("/mob/creeper.png");
            else if (item == Item.itemsList[Headed.IDs.PLAYER_HEAD]) {
                RenderManager.instance.renderEngine.loadDownloadableTexture(HeadedSkullBlockRenderer.getPlayerSkinURL(itemStack.tag.getString("name")), "/mob/char.png", PlayerSkinParser.instance);
                this.skullModel.skullLayer.showModel = true;
                if (itemStack.tag.getString("name").equals("deadmau5")) {
                    this.skullModel.leftEar.showModel = true;
                    this.skullModel.rightEar.showModel = true;
                }
            } else
                ((LoadTextureInvoker)((RenderPlayer)(Object)this)).setLoadTexture("/mob/zombie.png");

            GL11.glPushMatrix();
            ((ModelAccessor)((RenderPlayer)(Object)this)).modelArmor().bipedHead.showModel = false;
            ((ModelAccessor)((RenderPlayer)(Object)this)).modelBipedMain().bipedHead.postRender(0.0625F);
            float f1 = 1.125F;
            GL11.glTranslatef(0.0F, -0.8125F, 0.0F);
            GL11.glScalef(f1, f1, f1);
            this.skullModel.render();
            GL11.glPopMatrix();
        }
    }
}

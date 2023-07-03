package net.pedroricardo.headed.item;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ItemStack;
import net.minecraft.src.PlayerSkinParser;
import net.pedroricardo.headed.block.HeadedSkullBlockRenderer;
import net.pedroricardo.headed.block.model.HeadedSkullModel;
import net.pedroricardo.pedrolibrary.DownloadableTextureHelper;
import net.pedroricardo.pedrolibrary.ItemWithModelRenderer;
import org.lwjgl.opengl.GL11;

public class HeadedSkullItemRenderer extends ItemWithModelRenderer {
    @Override
    public int getTextureToRender(ItemStack itemStack) {
        if (itemStack.getItem() instanceof HeadedSkullItem) {
            switch (((HeadedSkullItem)itemStack.getItem()).type) {
                case "skeleton":
                    return Minecraft.getMinecraft().renderEngine.getTexture("/mob/skeleton.png");
                case "creeper":
                    return Minecraft.getMinecraft().renderEngine.getTexture("/mob/creeper.png");
                case "player":
                    return new DownloadableTextureHelper().getDownloadableTexture(
                            HeadedSkullBlockRenderer.getPlayerSkinURL(itemStack.tag.getString("name")),
                            "/mob/char.png", PlayerSkinParser.instance);
                default:
                    return Minecraft.getMinecraft().renderEngine.getTexture("/mob/zombie.png");
            }
        }
        return Minecraft.getMinecraft().renderEngine.getTexture("/mob/zombie.png");
    }

    @Override
    public void render(ItemStack itemStack, float v, float v1, float v2, float v3, float v4, float v5) {
        HeadedSkullModel model = new HeadedSkullModel();
        model.skullLayer.showModel = false;
        model.leftEar.showModel = false;
        model.rightEar.showModel = false;
        if (itemStack.getItem() instanceof HeadedSkullItem) {
            HeadedSkullItem headedSkullItem = (HeadedSkullItem)itemStack.getItem();
            if (headedSkullItem.type.equals("player")) {
                model.skullLayer.showModel = true;
                if (itemStack.tag.getString("name").equals("deadmau5")) {
                    model.leftEar.showModel = true;
                    model.rightEar.showModel = true;
                }
            }
        }
        model.render();
    }

    @Override
    public void applyBipedModelTransformations() {
        GL11.glTranslatef(-0.25F, -0.125F, 0.25F);
    }

    @Override
    public void applyFirstPersonModelTransformations() {
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        GL11.glTranslatef(-0.5F, 0.0F, -0.5F);
    }

    @Override
    public void applyGuiModelTransformations() {
        GL11.glScalef(1.25F, 1.25F, 1.25F);
    }
}

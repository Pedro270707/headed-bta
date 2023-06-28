package net.pedroricardo.headed.block;

import net.minecraft.src.*;
import net.pedroricardo.headed.Headed;
import net.pedroricardo.headed.block.model.HeadedSkullModel;
import org.lwjgl.opengl.GL11;

public class HeadedSkullBlockRenderer extends TileEntitySpecialRenderer {
    private HeadedSkullModel skullModel = new HeadedSkullModel();

    public HeadedSkullBlockRenderer() {
    }

    public void renderSkullAt(HeadedSkullBlockEntity blockEntity, double d, double d1, double d2, float f) {
        Block block = blockEntity.getBlockType();
        GL11.glPushMatrix();
        float f3;
        if (block == Headed.SKULL) {
        // Head on the floor here.
            GL11.glTranslatef((float)d + 0.5F, (float)d1 + 0.75F, (float)d2 + 0.5F);
            float f2 = (float)(blockEntity.getBlockMetadata() * 360) / 16.0F;
            GL11.glRotatef(-f2, 0.0F, 1.0F, 0.0F);
        } else {
            int i = blockEntity.getBlockMetadata();
            f3 = 0.0F;
            if (i == 2) {
                f3 = 180.0F;
            }

            if (i == 4) {
                f3 = 90.0F;
            }

            if (i == 5) {
                f3 = -90.0F;
            }

            GL11.glTranslatef((float)d + 0.5F, (float)d1 + 0.75F, (float)d2 + 0.5F);
            GL11.glRotatef(-f3, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.0F, 0.25F, -0.25F);
        }

        switch (blockEntity.getSkullType()) {
            case "skeleton":
                this.bindTextureByName("/mob/skeleton.png");
                break;
            case "creeper":
                this.bindTextureByName("/mob/creeper.png");
                break;
            default:
                this.bindTextureByName("/mob/zombie.png");
                break;
        }
        GL11.glPushMatrix();
        GL11.glScalef(1, -1, -1);
        this.skullModel.render();
        GL11.glPopMatrix();
        GL11.glDepthMask(true);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

    public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
        this.renderSkullAt((HeadedSkullBlockEntity) tileentity, d, d1, d2, f);
    }
}

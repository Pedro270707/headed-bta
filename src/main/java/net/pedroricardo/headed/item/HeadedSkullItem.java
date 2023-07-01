package net.pedroricardo.headed.item;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import net.pedroricardo.headed.Headed;
import net.pedroricardo.headed.block.HeadedSkullBlockEntity;
import net.pedroricardo.headed.block.HeadedSkullBlockRenderer;
import net.pedroricardo.headed.block.model.HeadedSkullModel;
import net.pedroricardo.pedrolibrary.DownloadableTextureHelper;
import net.pedroricardo.pedrolibrary.interfaces.IBlockEntityRenderer;
import org.lwjgl.opengl.GL11;

public class HeadedSkullItem extends Item implements IBlockEntityRenderer {
    private String type;
    private boolean isPlayerHead;
    public HeadedSkullItem(int i, String type, boolean isPlayerHead) {
        super(i);
        this.type = type;
        this.isPlayerHead = isPlayerHead;
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int skullRotationType, double heightPlaced) {
        if (skullRotationType == 0) {
            return false;
        } else if (!world.getBlockMaterial(x, y, z).isSolid()) {
            return false;
        } else {
            if (skullRotationType == 1) {
                ++y;
            }

            if (skullRotationType == 2) {
                --z;
            }

            if (skullRotationType == 3) {
                ++z;
            }

            if (skullRotationType == 4) {
                --x;
            }

            if (skullRotationType == 5) {
                ++x;
            }

            if (!Headed.SKULL.canPlaceBlockAt(world, x, y, z)) {
                return false;
            } else {
                if (skullRotationType == 1) {
                    world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), Headed.SKULL.stepSound.func_1145_d(), (Headed.SKULL.stepSound.getVolume() + 1.0F) / 2.0F, Headed.SKULL.stepSound.getPitch() * 0.8F);
                    world.setBlockAndMetadataWithNotify(x, y, z, Headed.SKULL.blockID, MathHelper.floor_double((double)((player.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5) & 15);
                    ((HeadedSkullBlockEntity)world.getBlockTileEntity(x, y, z)).setSkullType(type);
                } else {
                    world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), Headed.SKULL.stepSound.func_1145_d(), (Headed.SKULL.stepSound.getVolume() + 1.0F) / 2.0F, Headed.SKULL.stepSound.getPitch() * 0.8F);
                    world.setBlockAndMetadataWithNotify(x, y, z, Headed.WALL_SKULL.blockID, skullRotationType);
                    ((HeadedSkullBlockEntity)world.getBlockTileEntity(x, y, z)).setSkullType(type);
                }

                if (this.isPlayerHead && itemstack.tag.getString("name") != null && !itemstack.tag.getString("name").isEmpty()) {
                    String playerName = itemstack.tag.getString("name");
                    ((HeadedSkullBlockEntity) world.getBlockTileEntity(x, y, z)).setSkullOwner(playerName);
                }

                itemstack.consumeItem(player);
                return true;
            }
        }
    }

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
                            HeadedSkullBlockRenderer.getPlayerSkinURL(itemStack.tag.getString("name")), "/mob/char.png", PlayerSkinParser.instance);
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

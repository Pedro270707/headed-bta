package net.pedroricardo.headed.item;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import net.pedroricardo.headed.Headed;
import net.pedroricardo.headed.block.HeadedSkullBlockEntity;
import net.pedroricardo.pedrolibrary.ItemWithModelRenderer;
import net.pedroricardo.pedrolibrary.interfaces.IItemWithModelRenderer;
import net.pedroricardo.pedrolibrary.interfaces.ICustomDescription;

public class HeadedSkullItem extends Item implements IItemWithModelRenderer, ICustomDescription {
    String type;
    private boolean isPlayerHead;
    private ItemWithModelRenderer renderer = new HeadedSkullItemRenderer();
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
    public ItemWithModelRenderer getRenderer() {
        return renderer;
    }

    @Override
    public String getDescription(EntityPlayer entityPlayer, Slot slot) {
        StringTranslate stringTranslate = StringTranslate.getInstance();
        if (this.isPlayerHead && slot.getStack().tag.getString("name") != null && !slot.getStack().tag.getString("name").isEmpty()) {
            return stringTranslate.translateKey("item.headed.skull.player.named.desc").replace("%s", slot.getStack().tag.getString("name"));
        } else {
            return stringTranslate.translateKey(slot.getStack().getItemName() + ".desc");
        }
    }
}

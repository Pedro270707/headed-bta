package net.pedroricardo.headed;

import net.fabricmc.api.ModInitializer;
import net.minecraft.src.*;
import net.pedroricardo.headed.block.HeadedSkullBlock;
import net.pedroricardo.headed.block.HeadedSkullBlockEntity;
import net.pedroricardo.headed.block.HeadedSkullBlockRenderer;
import net.pedroricardo.headed.item.HeadedSkullItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.BlockHelper;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.helper.ItemHelper;
import turniplabs.halplibe.mixin.accessors.BlockAccessor;


public class Headed implements ModInitializer {
    public static final String MOD_ID = "headed";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Block SKULL = BlockHelper.createBlock(MOD_ID, new HeadedSkullBlock(902, HeadedSkullBlockEntity.class, true), "skull", Block.soundStoneFootstep, 1.0F, 1.0F, 0.0F).setNotInCreativeMenu();
    public static final Block WALL_SKULL = BlockHelper.createBlock(MOD_ID, new HeadedSkullBlock(903, HeadedSkullBlockEntity.class, false), "skull.wall", Block.soundStoneFootstep, 1.0F, 1.0F, 0.0F).setNotInCreativeMenu();

    @Override
    public void onInitialize() {
        EntityHelper.createSpecialTileEntity(HeadedSkullBlockEntity.class, new HeadedSkullBlockRenderer(), "Skull");
        Item.itemsList[WALL_SKULL.blockID + 1] = ItemHelper.createItem(Headed.MOD_ID, new HeadedSkullItem(WALL_SKULL.blockID + 1, "zombie"), "skull.zombie", "zombie_head.png");
        Item.itemsList[WALL_SKULL.blockID + 2] = ItemHelper.createItem(Headed.MOD_ID, new HeadedSkullItem(WALL_SKULL.blockID + 2, "skeleton"), "skull.skeleton", "skeleton_skull.png");
        Item.itemsList[WALL_SKULL.blockID + 3] = ItemHelper.createItem(Headed.MOD_ID, new HeadedSkullItem(WALL_SKULL.blockID + 3, "creeper"), "skull.creeper", "creeper_head.png");
        LOGGER.info("Headed initialized");
    }
}

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

    public static final Block SKULL = BlockHelper.createBlock(MOD_ID, new HeadedSkullBlock(1707, HeadedSkullBlockEntity.class, true), "skull", "skull.png", Block.soundStoneFootstep, 1.0F, 1.0F, 0.0F).setNotInCreativeMenu();
    public static final Block WALL_SKULL = BlockHelper.createBlock(MOD_ID, new HeadedSkullBlock(SKULL.blockID + 1, HeadedSkullBlockEntity.class, false), "skull.wall", "skull.png", Block.soundStoneFootstep, 1.0F, 1.0F, 0.0F).setNotInCreativeMenu();

    public static class IDs {
        public static final int ZOMBIE_HEAD = WALL_SKULL.blockID + 1;
        public static final int SKELETON_SKULL = WALL_SKULL.blockID + 2;
        public static final int CREEPER_HEAD = WALL_SKULL.blockID + 3;
    }

    @Override
    public void onInitialize() {
        EntityHelper.createSpecialTileEntity(HeadedSkullBlockEntity.class, new HeadedSkullBlockRenderer(), "Skull");
        Item.itemsList[IDs.ZOMBIE_HEAD] = ItemHelper.createItem(Headed.MOD_ID, new HeadedSkullItem(IDs.ZOMBIE_HEAD, "zombie"), "skull.zombie", "zombie_head.png");
        Item.itemsList[IDs.SKELETON_SKULL] = ItemHelper.createItem(Headed.MOD_ID, new HeadedSkullItem(IDs.SKELETON_SKULL, "skeleton"), "skull.skeleton", "skeleton_skull.png");
        Item.itemsList[IDs.CREEPER_HEAD] = ItemHelper.createItem(Headed.MOD_ID, new HeadedSkullItem(IDs.CREEPER_HEAD, "creeper"), "skull.creeper", "creeper_head.png");
        LOGGER.info("Headed initialized");
    }
}

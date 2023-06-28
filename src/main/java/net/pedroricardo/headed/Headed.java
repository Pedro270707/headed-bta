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
import turniplabs.halplibe.mixin.accessors.CraftingManagerAccessor;

import java.util.Random;


public class Headed implements ModInitializer {
    public static final String MOD_ID = "headed";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Block SKULL = BlockHelper.createBlock(MOD_ID, new HeadedSkullBlock(1707, HeadedSkullBlockEntity.class, true), "skull", "skull.png", Block.soundStoneFootstep, 1.0F, 1.0F, 0.0F).setNotInCreativeMenu();
    public static final Block WALL_SKULL = BlockHelper.createBlock(MOD_ID, new HeadedSkullBlock(SKULL.blockID + 1, HeadedSkullBlockEntity.class, false), "skull.wall", "skull.png", Block.soundStoneFootstep, 1.0F, 1.0F, 0.0F).setNotInCreativeMenu();

    public static class IDs {
        public static final int ZOMBIE_HEAD = WALL_SKULL.blockID + 1;
        public static final int SKELETON_SKULL = WALL_SKULL.blockID + 2;
        public static final int CREEPER_HEAD = WALL_SKULL.blockID + 3;
        public static final int PLAYER_HEAD = WALL_SKULL.blockID + 4;
    }

    @Override
    public void onInitialize() {
        EntityHelper.createSpecialTileEntity(HeadedSkullBlockEntity.class, new HeadedSkullBlockRenderer(), "Skull");
        Item.itemsList[IDs.ZOMBIE_HEAD] = ItemHelper.createItem(Headed.MOD_ID, new HeadedSkullItem(IDs.ZOMBIE_HEAD, "zombie", false), "skull.zombie", "zombie_head.png");
        Item.itemsList[IDs.SKELETON_SKULL] = ItemHelper.createItem(Headed.MOD_ID, new HeadedSkullItem(IDs.SKELETON_SKULL, "skeleton", false), "skull.skeleton", "skeleton_skull.png");
        Item.itemsList[IDs.CREEPER_HEAD] = ItemHelper.createItem(Headed.MOD_ID, new HeadedSkullItem(IDs.CREEPER_HEAD, "creeper", false), "skull.creeper", "creeper_head.png");
        Item.itemsList[IDs.PLAYER_HEAD] = ItemHelper.createItem(Headed.MOD_ID, new HeadedSkullItem(IDs.PLAYER_HEAD, "player", true), "skull.player", "player_head.png").setMaxStackSize(1);

        int randomInt = new Random().nextInt(4);
        switch (randomInt) {
            case 0:
                LOGGER.info("Headed initialized. Get ready to dive HEAD first into the fun!");
                break;
            case 1:
                LOGGER.info("Headed initialized. You must have flipped HEADS to see this message!");
                break;
            case 2:
                LOGGER.info("Headed initialized. The HEADquarters are so happy that you're playing this mod!");
                break;
            case 3:
                LOGGER.info("Headed initialized. I HEAD a joke to tell you, but I forgot it!");
                break;
        }
    }
}

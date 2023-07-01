package net.pedroricardo.headed.block;

import b100.json.JsonParser;
import b100.json.element.JsonArray;
import b100.json.element.JsonObject;
import b100.utils.StringUtils;
import net.minecraft.src.*;
import net.minecraft.src.utils.GetSkinUrlThread;
import net.pedroricardo.headed.Headed;
import net.pedroricardo.headed.block.model.HeadedSkullModel;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class HeadedSkullBlockRenderer extends TileEntitySpecialRenderer {
    private final HeadedSkullModel skullModel = new HeadedSkullModel();

    private static final String urlUUID = "https://api.mojang.com/users/profiles/minecraft/";
    private static final String urlSkin = "https://sessionserver.mojang.com/session/minecraft/profile/";
    private static final JsonParser jsonParser = new JsonParser();
    private static final Map<String, String> nameToUUID = new HashMap<>();

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

        this.skullModel.skullLayer.showModel = false;
        this.skullModel.leftEar.showModel = false;
        this.skullModel.rightEar.showModel = false;

        switch (blockEntity.getSkullType()) {
            case "skeleton":
                this.bindTextureByName("/mob/skeleton.png");
                break;
            case "creeper":
                this.bindTextureByName("/mob/creeper.png");
                break;
            case "player":
                RenderManager.instance.renderEngine.loadDownloadableTexture(getPlayerSkinURL(blockEntity.getSkullOwner()), "/mob/char.png", PlayerSkinParser.instance);
                this.skullModel.skullLayer.showModel = true;
                if (blockEntity.getSkullOwner() != null) {
                    if (blockEntity.getSkullOwner().equals("deadmau5")) {
                        this.skullModel.leftEar.showModel = true;
                        this.skullModel.rightEar.showModel = true;
                    }
                }
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

    @Nullable
    public static String getPlayerSkinURL(String name) {
        if (!nameToUUID.containsKey(name)) {
            String string;
            if (name == null || name.length() == 0) {
                return null;
            }
            try {
                string = StringUtils.getWebsiteContentAsString(urlUUID + name);
            } catch (Exception e2) {
                System.err.println("Can't connect to Mojang API.");
                e2.printStackTrace();
                return null;
            }
            if (string.length() == 0) {
                System.err.println("Player " + name + " doesn't exist!");
                return null;
            }
            String playerUUID = null;
            try {
                playerUUID = jsonParser.parse(string).getString("id");
            } catch (Exception e3) {
                e3.printStackTrace();
                return null;
            }
            if (playerUUID == null) {
                return null;
            }
            System.out.println("Loading Skin for Player " + name + "...");
            try {
                string = StringUtils.getWebsiteContentAsString(urlSkin + playerUUID);
            } catch (Exception e4) {
                System.err.println("Invalid UUID " + playerUUID + ", or can't connect to the Mojang API.");
                return null;
            }
            JsonObject object = jsonParser.parse(string);
            JsonArray properties = object.getArray("properties");
            JsonObject textureProperty = properties.query(
                    e -> e.getAsObject().getString("name").equalsIgnoreCase("textures")).getAsObject();
            JsonObject texturesObject = jsonParser.parse(GetSkinUrlThread.decodeBase64(textureProperty.getString("value")))
                    .getObject("textures");
            if (texturesObject.has("SKIN")) {
                nameToUUID.put(name, texturesObject.getObject("SKIN").getString("url"));
            }
        }
        return nameToUUID.get(name);
    }

    public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
        this.renderSkullAt((HeadedSkullBlockEntity) tileentity, d, d1, d2, f);
    }
}

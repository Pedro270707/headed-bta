package net.pedroricardo.headed.block;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;
import net.pedroricardo.headed.packet.SetHeadTypePacket;

public class HeadedSkullBlockEntity extends TileEntity {
    private String skullType = "zombie";

    public HeadedSkullBlockEntity() {
    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setString("SkullType", this.skullType);
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.skullType = nbttagcompound.getString("SkullType");
    }

    public String getSkullType() {
        return this.skullType;
    }

    public void setSkullType(String skullType) {
        if (skullType != null && skullType.equalsIgnoreCase("none")) {
            this.skullType = null;
        } else {
            this.skullType = skullType;
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        return new SetHeadTypePacket(this.xCoord, this.yCoord, this.zCoord, this.skullType);
    }
}

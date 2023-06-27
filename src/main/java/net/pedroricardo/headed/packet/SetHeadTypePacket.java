package net.pedroricardo.headed.packet;

import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SetHeadTypePacket extends Packet {
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public String skullType;

    public SetHeadTypePacket() {
        this.isChunkDataPacket = true;
    }

    public SetHeadTypePacket(int x, int y, int z, String skullType) {
        this.isChunkDataPacket = true;
        this.xPosition = x;
        this.yPosition = y;
        this.zPosition = z;
        if (skullType == null) {
            skullType = "zombie";
        }

        this.skullType = skullType;
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException {
        this.xPosition = datainputstream.readInt();
        this.yPosition = datainputstream.readShort();
        this.zPosition = datainputstream.readInt();
        this.skullType = readString(datainputstream, 16);
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeInt(this.xPosition);
        dataoutputstream.writeShort(this.yPosition);
        dataoutputstream.writeInt(this.zPosition);
        writeString(this.skullType, dataoutputstream);
    }

    public void processPacket(NetHandler nethandler) {
        nethandler.handleInvalidPacket(this);
    }

    public int getPacketSize() {
        return 12 + this.skullType.length();
    }
}
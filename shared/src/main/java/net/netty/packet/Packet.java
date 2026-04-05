package net.netty.packet;

public abstract class Packet {
    protected PacketType type;

    public PacketType getType() {
        return type;
    }

    public void setType(PacketType type) {
        this.type = type;
    }
}

package net.netty.packet;


public class ExitGamePacket extends Packet {

    @Override
    public PacketType getType() {
        return PacketType.EXIT_GAME;
    }

    @Override
    public String toString() {
        return "ExitGamePacket{}";
    }
}

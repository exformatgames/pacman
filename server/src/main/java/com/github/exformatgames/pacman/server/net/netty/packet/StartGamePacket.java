package com.github.exformatgames.pacman.server.net.netty.packet;

public class StartGamePacket extends Packet {

    @Override
    public PacketType getType() {
        return PacketType.START_GAME;
    }

    @Override
    public String toString() {
        return "StartGamePacket{}";
    }
}

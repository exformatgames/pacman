package com.github.exformatgames.pacman.server.netty.packet;

public class StartGamePacket extends Packet {

    @Override
    public PacketType getType() {
        return PacketType.JOIN_GAME;
    }

    @Override
    public String toString() {
        return "StartGamePacket{}";
    }
}

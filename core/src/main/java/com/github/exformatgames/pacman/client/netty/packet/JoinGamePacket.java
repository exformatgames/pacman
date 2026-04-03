package com.github.exformatgames.pacman.client.netty.packet;

public class JoinGamePacket extends Packet {

    @Override
    public PacketType getType() {
        return PacketType.JOIN_GAME;
    }

    @Override
    public String toString() {
        return "JoinGamePacket{}";
    }
}

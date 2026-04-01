package com.github.exformatgames.pacman.client.netty.packet;

public class ExitGamePacket extends Packet {

    @Override
    public PacketType getType() {
        return PacketType.EXIT_GAME;
    }
}

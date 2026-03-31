package com.github.exformatgames.pacman.server.net.netty.packet;


public class ExitGamePacket extends Packet {

    @Override
    public PacketType getType() {
        return PacketType.EXIT_GAME;
    }
}

package com.github.exformatgames.pacman.managers.net.client.packet;

public class ExitGamePacket extends Packet {

    @Override
    public PacketType getType() {
        return PacketType.EXIT_GAME;
    }
}

package com.github.exformatgames.pacman.managers.net.client.packet;

public class StartGamePacket extends Packet {

    @Override
    public PacketType getType() {
        return PacketType.START_GAME;
    }
}

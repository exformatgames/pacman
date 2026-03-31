package com.github.exformatgames.pacman.managers.net.client.packet;

public class RequestGameMapPacket extends Packet {

    @Override
    public PacketType getType() {
        return PacketType.REQUEST_GAME_MAP;
    }
}

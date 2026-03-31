package com.github.exformatgames.pacman.server.net.netty.packet;

public class RequestGameMapPacket extends Packet {

    @Override
    public PacketType getType() {
        return PacketType.REQUEST_GAME_MAP;
    }
}

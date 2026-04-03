package com.github.exformatgames.pacman.server.netty.packet;

import com.github.exformatgames.pacman.server.data.MapData;

public class ResponseGameMapPacket extends Packet {

    public MapData map;


    @Override
    public PacketType getType() {
        return PacketType.RESPONSE_GAME_MAP;
    }

    @Override
    public String toString() {
        return "ResponseGameMapPacket{" +
            "map=" + map +
            '}';
    }
}

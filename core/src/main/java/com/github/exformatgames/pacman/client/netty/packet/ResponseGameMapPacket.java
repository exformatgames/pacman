package com.github.exformatgames.pacman.client.netty.packet;

import com.github.exformatgames.pacman.data.MapData;

public class ResponseGameMapPacket extends Packet {

    public MapData map;


    @Override
    public PacketType getType() {
        return PacketType.RESPONSE_GAME_MAP;
    }
}

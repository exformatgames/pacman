package com.github.exformatgames.pacman.managers.net.client.packet;

import com.github.exformatgames.pacman.data.MapData;

public class ResponseGameMapPacket extends Packet {

    public MapData map;


    @Override
    public PacketType getType() {
        return PacketType.RESPONSE_GAME_MAP;
    }
}

package net.netty.packet;

import data.MapData;

public class ResponseGameMapPacket extends Packet {

    public MapData map;


    @Override
    public PacketType getType() {
        return PacketType.RESPONSE_GAME_MAP;
    }

    @Override
    public String toString() {
        return "ResponseGameMapPacket{" +
            "map=" + map.toString() +
            '}';
    }
}

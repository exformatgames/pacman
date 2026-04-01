package com.github.exformatgames.pacman.server.server.netty.packet;

import com.github.exformatgames.pacman.server.data.MapData;

public class MapPacket extends Packet {

    public MapData data;
	public PacketType type;



    @Override
    public PacketType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "MapPacket{" +
            "data=" + data +
            ", type=" + type +
            '}';
    }
}

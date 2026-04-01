package com.github.exformatgames.pacman.client.netty.packet;

import com.github.exformatgames.pacman.data.MapData;

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

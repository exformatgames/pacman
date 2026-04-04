package net.netty.packet;

import data.MapData;

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
            "data=" + data.toString() +
            ", type=" + type +
            '}';
    }
}

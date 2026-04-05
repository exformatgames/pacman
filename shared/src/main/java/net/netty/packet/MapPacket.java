package net.netty.packet;

import data.MapData;

public class MapPacket extends Packet {

    public MapData data;

    @Override
    public String toString() {
        return "MapPacket{" +
            "data=" + data.toString() +
            ", type=" + type +
            '}';
    }
}

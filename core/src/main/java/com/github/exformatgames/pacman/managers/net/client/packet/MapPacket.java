package com.github.exformatgames.pacman.managers.net.client.packet;

import com.github.exformatgames.pacman.data.MapData;

public class MapPacket extends Packet {

    public MapData data;
	public PacketType type;



    @Override
    public PacketType getType() {
        return type;
    }
}

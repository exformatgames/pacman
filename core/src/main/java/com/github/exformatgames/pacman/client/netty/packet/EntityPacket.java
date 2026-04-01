package com.github.exformatgames.pacman.client.netty.packet;

import com.github.exformatgames.pacman.data.EntityData;

public class EntityPacket extends Packet {

    public EntityData data;
	public PacketType type;

	@Override
	public PacketType getType () {
		return type;
	}

    @Override
    public String toString() {
        return "EntityPacket{" +
            "data=" + data +
            ", type=" + type +
            '}';
    }
}

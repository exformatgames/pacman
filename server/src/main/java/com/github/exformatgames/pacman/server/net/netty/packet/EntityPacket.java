package com.github.exformatgames.pacman.server.net.netty.packet;


import com.github.exformatgames.pacman.server.data.EntityData;

public class EntityPacket extends Packet {

    public EntityData data;
	public PacketType type;

	@Override
	public PacketType getType () {
		return type;
	}
}

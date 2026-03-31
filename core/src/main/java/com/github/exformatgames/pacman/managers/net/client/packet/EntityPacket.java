package com.github.exformatgames.pacman.managers.net.client.packet;

import com.github.exformatgames.pacman.data.EntityData;
import com.github.exformatgames.pacman.data.EntityType;

public class EntityPacket extends Packet {

    public EntityData data;
	public PacketType type;
	
	@Override
	public PacketType getType () {
		return type;
	}
}

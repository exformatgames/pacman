package com.github.exformatgames.pacman.server.net.netty.packet.writers;

import com.github.exformatgames.pacman.server.data.EntityData;
import com.github.exformatgames.pacman.server.net.netty.packet.MapPacket;
import io.netty.buffer.ByteBuf;

public class MapPacketWriter implements PacketWriter<MapPacket> {

	private EntityPacketWriter entityWriter = new EntityPacketWriter();

	@Override
	public void write (MapPacket packet, ByteBuf out) {
		out.writeInt(packet.data.ID);
        out.writeInt(packet.data.width);
		out.writeInt(packet.data.height);

		out.writeInt(packet.data.entityList.length);

		for (EntityData data : packet.data.entityList) {
			entityWriter.write(data, out);
		}
	}
}

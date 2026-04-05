package net.netty.utils.writers;

import data.EntityData;
import net.netty.packet.MapPacket;
import io.netty.buffer.ByteBuf;
import net.netty.packet.ResponseGameMapPacket;

public class MapPacketWriter implements PacketWriter<ResponseGameMapPacket> {

	private final EntityPacketWriter entityWriter = new EntityPacketWriter();

	@Override
	public void write (ResponseGameMapPacket packet, ByteBuf out) {
		out.writeInt(packet.map.ID);
        out.writeInt(packet.map.width);
		out.writeInt(packet.map.height);

		out.writeInt(packet.map.entityList.length);

		for (EntityData data : packet.map.entityList) {
            entityWriter.write(data, out);
		}
	}
}

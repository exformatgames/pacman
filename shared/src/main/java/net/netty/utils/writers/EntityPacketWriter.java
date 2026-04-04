package net.netty.utils.writers;

import data.EntityData;
import net.netty.packet.EntityPacket;
import io.netty.buffer.ByteBuf;

public class EntityPacketWriter implements PacketWriter<EntityPacket> {

	@Override
	public void write (EntityPacket packet, ByteBuf out) {
		write(packet.data, out);
	}

	public void write (EntityData data, ByteBuf out) {
		out.writeInt(data.ID);
        out.writeInt(data.type.ordinal());
        out.writeInt(data.position.x);
        out.writeInt(data.position.y);
        out.writeInt(data.speed);
	}
}

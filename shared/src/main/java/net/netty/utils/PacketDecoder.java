package net.netty.utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import net.netty.packet.Packet;
import net.netty.packet.PacketType;
import net.netty.utils.readers.PacketReader;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    private final PacketRegistry registry;

    public PacketDecoder(PacketRegistry registry) {
        this.registry = registry;
    }

    @Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {

		if (in.readableBytes() < 4) {
            System.out.println("buffer size < 4");
            return;
        }

		int typeId = in.readInt();

		if (typeId < 0 || typeId >= PacketType.values().length) {
			System.out.println("INVALID TYPE: " + typeId);
			return;
		}

		PacketType type = PacketType.values()[typeId];

		PacketReader reader = registry.getReader(type);
        if (reader != null) {
            Packet packet = reader.read(in);
            if (packet != null) {
                if (packet.getType() == null) {
                    packet.setType(type);
                }
                out.add(packet);
            }
        }
        else {
            Packet packet = PacketBuilder.build(type);
            out.add(packet);
        }
	}
}



package net.netty.utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.netty.packet.Packet;
import net.netty.packet.PacketType;
import net.netty.utils.writers.PacketWriter;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

    private final PacketRegistry registry;

    public PacketEncoder(PacketRegistry registry) {
        this.registry = registry;
    }

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) {
        System.out.println("PacketEncoder.encode: " + msg.toString());
		ByteBuf buffer = ctx.alloc().buffer();

		PacketType type = msg.getType();
		buffer.writeInt(type.ordinal());

		PacketWriter writer = registry.getWriter(type);
        if (writer != null) {
            System.out.println("PacketEncoder.encode.writer not null");
            writer.write(msg, buffer);
        }

		out.writeBytes(buffer);

        System.out.println("PacketEncoder.encode. buffer size: " + out.toString());
    }
}

package com.github.exformatgames.pacman.server.server.netty.packet;


import com.github.exformatgames.pacman.server.server.netty.packet.writers.PacketWriter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

    private final PacketRegistry registry;

    public PacketEncoder(PacketRegistry registry) {
        this.registry = registry;
    }

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) {

		ByteBuf buffer = ctx.alloc().buffer();

		PacketType type = msg.getType();
		buffer.writeInt(type.ordinal());

		PacketWriter writer = registry.getWriter(type);
		writer.write(msg, buffer);

		out.writeInt(buffer.readableBytes());
		out.writeBytes(buffer);
	}
}

package com.github.exformatgames.pacman.client.netty.packet;

import com.github.exformatgames.pacman.client.netty.packet.readers.PacketReader;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    private final PacketRegistry registry;

    public PacketDecoder(PacketRegistry registry) {
        this.registry = registry;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        int typeId = in.readInt();
        PacketType type = PacketType.values()[typeId];

        PacketReader reader = registry.getReader(type);
        Packet packet = reader.read(in);

        if (packet != null) {
            out.add(packet);
        }
    }
}

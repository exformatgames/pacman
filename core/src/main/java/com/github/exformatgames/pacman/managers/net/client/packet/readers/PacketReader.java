package com.github.exformatgames.pacman.managers.net.client.packet.readers;

import com.github.exformatgames.pacman.managers.net.client.packet.Packet;

import io.netty.buffer.ByteBuf;

public interface PacketReader<T extends Packet> {

    T read(ByteBuf in);
}

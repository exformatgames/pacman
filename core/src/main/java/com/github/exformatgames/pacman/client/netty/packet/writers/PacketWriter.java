package com.github.exformatgames.pacman.client.netty.packet.writers;

import com.github.exformatgames.pacman.client.netty.packet.Packet;

import io.netty.buffer.ByteBuf;

public interface PacketWriter<T extends Packet> {
    void write(T packet, ByteBuf out);
}

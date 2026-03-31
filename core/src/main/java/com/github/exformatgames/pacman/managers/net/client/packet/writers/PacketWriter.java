package com.github.exformatgames.pacman.managers.net.client.packet.writers;

import com.github.exformatgames.pacman.managers.net.client.packet.Packet;

import io.netty.buffer.ByteBuf;

public interface PacketWriter<T extends Packet> {
    void write(T packet, ByteBuf out);
}

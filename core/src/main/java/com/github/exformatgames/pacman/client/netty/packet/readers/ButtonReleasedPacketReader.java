package com.github.exformatgames.pacman.client.netty.packet.readers;

import com.github.exformatgames.pacman.client.netty.packet.ButtonReleasedPacket;
import com.github.exformatgames.pacman.data.InputData;

import io.netty.buffer.ByteBuf;

public class ButtonReleasedPacketReader implements PacketReader<ButtonReleasedPacket> {
    @Override
    public ButtonReleasedPacket read(ByteBuf in) {
        ButtonReleasedPacket packet = new ButtonReleasedPacket();

        packet.action = InputData.values()[in.readInt()];

        return packet;
    }
}

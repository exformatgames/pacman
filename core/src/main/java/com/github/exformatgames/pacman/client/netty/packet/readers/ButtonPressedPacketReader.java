package com.github.exformatgames.pacman.client.netty.packet.readers;

import com.github.exformatgames.pacman.client.netty.packet.ButtonPressedPacket;
import com.github.exformatgames.pacman.data.InputData;

import io.netty.buffer.ByteBuf;

public class ButtonPressedPacketReader implements PacketReader<ButtonPressedPacket> {
    @Override
    public ButtonPressedPacket read(ByteBuf in) {
        ButtonPressedPacket packet = new ButtonPressedPacket();

        packet.action = InputData.values()[in.readInt()];

        return packet;
    }
}

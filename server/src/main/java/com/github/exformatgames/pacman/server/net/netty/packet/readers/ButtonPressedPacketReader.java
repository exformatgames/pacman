package com.github.exformatgames.pacman.server.net.netty.packet.readers;

import com.github.exformatgames.pacman.server.data.InputData;
import com.github.exformatgames.pacman.server.net.netty.packet.ButtonPressedPacket;
import io.netty.buffer.ByteBuf;

public class ButtonPressedPacketReader implements PacketReader<ButtonPressedPacket> {
    @Override
    public ButtonPressedPacket read(ByteBuf in) {
        ButtonPressedPacket packet = new ButtonPressedPacket();

        packet.action = InputData.values()[in.readInt()];

        return packet;
    }
}

package com.github.exformatgames.pacman.managers.net.client.packet.readers;

import com.github.exformatgames.pacman.managers.net.InputAction;
import com.github.exformatgames.pacman.managers.net.client.packet.InputPacket;

import io.netty.buffer.ByteBuf;

public class inputPacketReader implements PacketReader<InputPacket> {
    @Override
    public InputPacket read(ByteBuf in) {
        InputPacket packet = new InputPacket();

        packet.action = InputAction.values()[in.readInt()];
        packet.pressed = in.readBoolean();

        return packet;
    }
}

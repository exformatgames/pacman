package net.netty.utils.readers;

import data.InputData;
import net.netty.packet.ButtonReleasedPacket;
import io.netty.buffer.ByteBuf;

public class ButtonReleasedPacketReader implements PacketReader<ButtonReleasedPacket> {
    @Override
    public ButtonReleasedPacket read(ByteBuf in) {
        ButtonReleasedPacket packet = new ButtonReleasedPacket();


        packet.action = InputData.values()[in.readInt()];

        return packet;
    }
}

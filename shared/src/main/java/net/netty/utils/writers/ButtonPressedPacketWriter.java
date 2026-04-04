package net.netty.utils.writers;

import net.netty.packet.ButtonPressedPacket;
import io.netty.buffer.ByteBuf;

public class ButtonPressedPacketWriter implements PacketWriter<ButtonPressedPacket> {

    @Override
    public void write(ButtonPressedPacket packet, ByteBuf out) {
        out.writeInt(packet.action.ordinal());
    }
}

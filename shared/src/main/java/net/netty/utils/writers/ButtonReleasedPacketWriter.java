package net.netty.utils.writers;

import net.netty.packet.ButtonReleasedPacket;
import io.netty.buffer.ByteBuf;

public class ButtonReleasedPacketWriter implements PacketWriter<ButtonReleasedPacket> {

    @Override
    public void write(ButtonReleasedPacket packet, ByteBuf out) {
        out.writeInt(packet.action.ordinal());
    }
}

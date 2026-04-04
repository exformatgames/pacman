package net.netty.utils;

import io.netty.channel.Channel;
import net.netty.packet.Packet;

public interface PacketHandler<T extends Packet> {
    void handle(Channel channel, T packet);
}

package com.github.exformatgames.pacman.server.netty.packet;

import io.netty.channel.Channel;

public interface PacketHandler<T extends Packet> {
    void handle(Channel channel, T packet);
}

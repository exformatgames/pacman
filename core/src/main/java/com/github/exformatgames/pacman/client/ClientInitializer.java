package com.github.exformatgames.pacman.client;

//ChatGPT

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {

        ChannelPipeline p = ch.pipeline();

        // пока без протокола — просто строка
        p.addLast(new StringDecoder());
        p.addLast(new StringEncoder());

        //p.addLast(new ClientHandler());
    }
}

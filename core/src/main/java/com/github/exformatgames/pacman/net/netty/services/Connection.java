package com.github.exformatgames.pacman.net.netty.services;

import com.github.exformatgames.pacman.net.netty.ClientHandler;
import com.github.exformatgames.pacman.net.netty.NettyClient;
import com.github.exformatgames.pacman.net.service.ConnectionService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import net.netty.utils.PacketDecoder;
import net.netty.utils.PacketEncoder;

public class Connection implements ConnectionService {

    private Channel channel;
    private final Bootstrap bootstrap;

	public Connection (final NettyClient client) {

        bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();

		bootstrap.group(group)
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel (SocketChannel ch) {
					ChannelPipeline p = ch.pipeline();
					p.addLast(new LoggingHandler(LogLevel.DEBUG));

					p.addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 4));
					p.addLast(new PacketDecoder(client.getRegistry()));

					p.addLast(new LengthFieldPrepender(4));
					p.addLast(new PacketEncoder(client.getRegistry()));

					p.addLast(new ClientHandler(client));
				}
			});
	}

	@Override
	public void connect (String host, int port) {
        if ( ! isConnected()) {
            ChannelFuture future = bootstrap.connect(host, port);

            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        channel = future.channel();
                        System.out.println("connected...");
                    }
                    else {
                        channel = null;
                        System.out.println("failed to connect...");

                        future.cause().printStackTrace();
                    }
                }
            });
        }
	}

	@Override
	public void disconnect () {
		if (channel != null) {
            try {
                channel.close().sync();
                channel = null;
                System.out.println("disconnected...");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
	}

	public Channel getChannel () {
		return channel;
	}

	@Override
	public boolean isConnected () {
		return channel != null && channel.isActive();
	}
}

/*сперва парочка гпт/дипсик предлагают костыльно проверять длину пакетоа,
*а потом говорят что этот механизм уже присутвует..
*и типа "ты че тупой?"
*и ведь да.. нет бы просто взять и доки почитать.. эхь...
*/

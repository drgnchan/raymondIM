package com.raymond.comct;

import com.raymond.comct.channelHandler.LoginHandler;
import com.raymond.comct.channelHandler.LoginResponseHandler;
import com.raymond.comct.channelHandler.MessageResponseHandler;
import com.raymond.comct.channelHandler.Spliter;
import com.raymond.comct.codec.PacketDecoder;
import com.raymond.comct.codec.PacketEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class NettyClient {
    private static final int MAX_RETRY = 5;
    public static void main(String[] args) {
        String host = "localhost";
        int port = 8000;
        NioEventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new LoginHandler());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });
        connect(bootstrap, host, port, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("connect success...");
            } else if (retry == 0) {
                log.error("connect times exhaust...");
                bootstrap.config().group().shutdownGracefully();
            } else {
                int retryOrder = (MAX_RETRY - retry) + 1;
                int delay = 1 << retryOrder;
                log.error("connect failed times: " + retryOrder);
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

}

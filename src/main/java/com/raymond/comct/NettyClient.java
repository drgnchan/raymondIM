package com.raymond.comct;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.raymond.comct.channelHandler.*;
import com.raymond.comct.codec.PacketCodec;
import com.raymond.comct.codec.PacketDecoder;
import com.raymond.comct.codec.PacketEncoder;
import com.raymond.comct.codec.packet.LoginRequestPacket;
import com.raymond.comct.codec.packet.MessageRequestPacket;
import com.raymond.comct.command.ConsoleCommand;
import com.raymond.comct.command.ConsoleCommandManager;
import com.raymond.comct.command.LoginConsoleCommand;
import com.raymond.comct.util.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
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
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new CreateGroupResponseHandler());
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
                //启动控制台线程
                ChannelFuture channelFuture = (ChannelFuture) future;
                startConsoleThread(channelFuture.channel());
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

    private static void startConsoleThread(Channel channel) {
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        Scanner scanner = new Scanner(System.in);
        new Thread(() -> {
            while (!Thread.interrupted()) {

                if (SessionUtil.hasLogin(channel)) {
                    consoleCommandManager.exec(scanner, channel);
                } else {
                    loginConsoleCommand.exec(scanner, channel);
                }
            }
        }).start();
    }

}

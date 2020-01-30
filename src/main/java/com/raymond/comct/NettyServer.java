package com.raymond.comct;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServer {
    public static void main(String[] args) {
        int port = 8000;
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {

                    }
                });
        GenericFutureListener<Future<? super Void>> genericFutureListener = new ServerBindListener(port, serverBootstrap);
        serverBootstrap.bind(port).addListener(genericFutureListener);

    }

    private static void bindAfterFailure(ServerBootstrap serverBootstrap, int port, GenericFutureListener<Future<? super Void>> listener) {
        serverBootstrap.bind(port).addListener(listener);
    }

    private static class ServerBindListener implements GenericFutureListener<Future<? super Void>> {

        private int port;
        private ServerBootstrap serverBootstrap;
        ServerBindListener(int port, ServerBootstrap serverBootstrap) {
            this.port = port;
            this.serverBootstrap = serverBootstrap;
        }

        @Override
        public void operationComplete(Future<? super Void> future) throws Exception {
            if (future.isSuccess()) {
                log.info(String.format("port %d bind success...", port));
            } else {
                log.error(String.format("port %d bind fail...", port));
                this.port++;
                bindAfterFailure(serverBootstrap, port, this);
            }
        }
    }
}

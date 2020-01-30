package com.raymond.comct.channelHandler;

import com.raymond.comct.codec.PacketCodec;
import com.raymond.comct.codec.packet.LoginResponsePacket;
import com.raymond.comct.codec.packet.MessageRequestPacket;
import com.raymond.comct.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;


@Slf4j
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if (msg.getSuccess()) {
            log.info("登录成功");
            LoginUtil.markLogin(ctx.channel());
            //启动控制台线程
            startConsoleThread(ctx.channel());
        } else {
            log.error("登录失败");
        }
    }

    private void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (LoginUtil.hasLogin(channel)) {
                    log.info("输入消息发送至服务端：");
                    Scanner scanner = new Scanner(System.in);
                    String line = scanner.nextLine();

                    MessageRequestPacket requestPacket = new MessageRequestPacket();
                    requestPacket.setMessage(line);
                    ByteBuf byteBuf = PacketCodec.INSTANCE.encode(requestPacket);
                    channel.writeAndFlush(byteBuf);

                } else {
                    break;
                }
            }
        }).start();
    }
}

package com.raymond.comct.channelHandler;

import com.raymond.comct.codec.packet.LoginRequestPacket;
import com.raymond.comct.codec.PacketCodec;
import com.raymond.comct.codec.packet.LoginResponsePacket;
import com.raymond.comct.codec.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.UUID;

@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        Packet packet = PacketCodec.INSTANCE.decode(byteBuf);
        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
            if (loginResponsePacket.getSuccess()) {
                log.info("login success...");
            } else {
                log.error("login failed...");
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info(new Date() + ": client is login...");

        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("raymond");
        loginRequestPacket.setPassword("password");

        ByteBuf byteBuf = PacketCodec.INSTANCE.encode(loginRequestPacket);

        ctx.channel().writeAndFlush(byteBuf);

    }
}

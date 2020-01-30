package com.raymond.comct.channelHandler;

import com.raymond.comct.codec.PacketCodec;
import com.raymond.comct.codec.packet.LoginRequestPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.UUID;

public class LoginHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("raymond");
        loginRequestPacket.setPassword("pwd");

        ByteBuf byteBuf = PacketCodec.INSTANCE.encode(loginRequestPacket);
        ctx.channel().writeAndFlush(byteBuf);
    }
}

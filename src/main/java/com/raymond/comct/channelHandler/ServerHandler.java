package com.raymond.comct.channelHandler;

import com.raymond.comct.codec.packet.LoginRequestPacket;
import com.raymond.comct.codec.packet.LoginResponsePacket;
import com.raymond.comct.codec.packet.Packet;
import com.raymond.comct.codec.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        log.info(new Date() + ": server reading data: " + byteBuf.toString(StandardCharsets.UTF_8));

        Packet packet = PacketCodec.INSTANCE.decode(byteBuf);
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            if (valid(loginRequestPacket.getUsername())) {
                loginResponsePacket.setSuccess(true);
            } else {
                loginResponsePacket.setReason("login failed...");
                loginResponsePacket.setSuccess(false);
            }
            ByteBuf response = PacketCodec.INSTANCE.encode(loginResponsePacket);
            ctx.channel().writeAndFlush(response);
        }

    }

    public boolean valid(String username) {
        return true;
    }
}

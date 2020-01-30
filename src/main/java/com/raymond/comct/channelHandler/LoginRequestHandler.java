package com.raymond.comct.channelHandler;

import com.raymond.comct.codec.PacketCodec;
import com.raymond.comct.codec.packet.LoginRequestPacket;
import com.raymond.comct.codec.packet.LoginResponsePacket;
import com.raymond.comct.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        if (valid(msg.getUsername())) {
            loginResponsePacket.setSuccess(true);
            //标记为已登录
            LoginUtil.markLogin(ctx.channel());
        } else {
            loginResponsePacket.setReason("login failed...");
            loginResponsePacket.setSuccess(false);
        }
        ByteBuf response = PacketCodec.INSTANCE.encode(loginResponsePacket);
        ctx.channel().writeAndFlush(response);
    }

    public boolean valid(String username) {
        return false;
    }
}

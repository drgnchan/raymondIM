package com.raymond.comct.channelHandler;

import com.raymond.comct.codec.PacketCodec;
import com.raymond.comct.codec.packet.LoginResponsePacket;
import com.raymond.comct.codec.packet.MessageRequestPacket;
import com.raymond.comct.util.SessionUtil;
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
            SessionUtil.markLogin(ctx.channel());
        } else {
            log.error("登录失败");
        }
    }


}

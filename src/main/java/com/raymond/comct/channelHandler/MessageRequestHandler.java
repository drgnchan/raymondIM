package com.raymond.comct.channelHandler;

import com.raymond.comct.codec.PacketCodec;
import com.raymond.comct.codec.packet.MessageRequestPacket;
import com.raymond.comct.codec.packet.MessageResponsePacket;
import com.raymond.comct.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        log.info("接收到客户端消息：【" + msg.getMessage() + "】");
        messageResponsePacket.setMessage("接收到客户端消息：【" + msg.getMessage() + "】");
        ByteBuf messageResponse = PacketCodec.INSTANCE.encode(messageResponsePacket);
        ctx.channel().writeAndFlush(messageResponse);
    }
}

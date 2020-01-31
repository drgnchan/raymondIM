package com.raymond.comct.channelHandler;

import com.raymond.comct.Session;
import com.raymond.comct.codec.PacketCodec;
import com.raymond.comct.codec.packet.MessageRequestPacket;
import com.raymond.comct.codec.packet.MessageResponsePacket;
import com.raymond.comct.util.SessionUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        //消息发送方的session
        Session session = SessionUtil.getSession(ctx.channel());

        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUsername());
        messageResponsePacket.setMessage(msg.getMessage());

        //消息目标方的channel
        Channel channel = SessionUtil.getChannel(msg.getToUserId());

        if (channel == null) {
            log.warn("目标用户不在线");
            //TODO: 存离线消息
        } else {
            ByteBuf messageResponse = PacketCodec.INSTANCE.encode(messageResponsePacket);
            channel.writeAndFlush(messageResponse);
        }

    }
}

package com.raymond.comct.channelHandler;

import com.raymond.comct.codec.packet.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket msg) throws Exception {
        if (msg.getSuccess()) {
            System.out.println("加入群聊成功,groupId为：【" + msg.getGroupId() + "】，成员有：【" + String.join(",", msg.getUsernames()) + "】");
        } else {
            System.err.println("加入群聊失败");
        }
    }
}

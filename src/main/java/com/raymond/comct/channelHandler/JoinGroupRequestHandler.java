package com.raymond.comct.channelHandler;

import com.raymond.comct.codec.packet.JoinGroupRequestPacket;
import com.raymond.comct.codec.packet.JoinGroupResponsePacket;
import com.raymond.comct.util.GroupUtil;
import com.raymond.comct.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket msg) throws Exception {
        String groupId = msg.getGroupId();
        ChannelGroup channels = GroupUtil.getChannelGroup(groupId);
        if (channels != null) {
            //发消息给其他成员
            JoinGroupResponsePacket joinGroupResponsePacket = new JoinGroupResponsePacket();
            joinGroupResponsePacket.setNewUserId(SessionUtil.getSession(ctx.channel()).getUserId());
            joinGroupResponsePacket.setOriginalUserIds(GroupUtil.getGroupUserIds(groupId));
            joinGroupResponsePacket.setGroupId(groupId);

            channels.writeAndFlush(joinGroupResponsePacket);

            channels.add(ctx.channel());
        }

    }
}

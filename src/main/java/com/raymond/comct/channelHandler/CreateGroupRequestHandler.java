package com.raymond.comct.channelHandler;

import com.raymond.comct.Session;
import com.raymond.comct.codec.packet.CreateGroupRequestPacket;
import com.raymond.comct.codec.packet.CreateGroupResponsePacket;
import com.raymond.comct.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket msg) throws Exception {
        List<String> userIds = msg.getUserIds();
        ChannelGroup channels = new DefaultChannelGroup(ctx.executor());
        List<String> usernames = new ArrayList<>();
        for (String userId : userIds) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel == null) {
                System.err.println("用户【" + SessionUtil.getUsername(userId) + "】不在线");
                break;
            } else {
                channels.add(channel);
                usernames.add(SessionUtil.getUsername(userId));
                usernames.add(SessionUtil.getSession(ctx.channel()).getUsername());
            }
        }
        if (usernames.size() > 0) {
            //构建相应
            String groupId = UUID.randomUUID().toString();
            CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
            createGroupResponsePacket.setSuccess(true);
            createGroupResponsePacket.setUsernames(usernames);
            createGroupResponsePacket.setGroupId(groupId);
            ctx.channel().writeAndFlush(createGroupResponsePacket);
            channels.writeAndFlush(createGroupResponsePacket);

            System.out.println("创建群聊成功，groupId为：" + groupId + "；成员有：【" + String.join(",", usernames) + "】");
        }







    }
}

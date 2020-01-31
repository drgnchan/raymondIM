package com.raymond.comct.channelHandler;

import com.raymond.comct.codec.packet.JoinGroupResponsePacket;
import com.raymond.comct.util.GroupUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket msg) throws Exception {
        msg.getOriginalUserIds().add(msg.getNewUserId());
        List<String> allUserIds = msg.getOriginalUserIds();
        System.out.println(msg.getNewUserId() + "加入群聊成功,成员有：" + String.join(",", allUserIds));
    }
}

package com.raymond.comct.util;

import com.raymond.comct.Session;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
public class GroupUtil {
    private static final Map<String, ChannelGroup> CHANNEL_GROUP_MAP = new ConcurrentHashMap<>();

    public static void addGroup(ChannelGroup channels, String groupId) {
        CHANNEL_GROUP_MAP.put(groupId, channels);
    }

    public static ChannelGroup getChannelGroup(String groupId) {
        return CHANNEL_GROUP_MAP.get(groupId);
    }

    public static void leaveGroup(String groupId, Channel channel) {
        ChannelGroup channels = CHANNEL_GROUP_MAP.get(groupId);
        channels.remove(channel);
    }

    public static List<String> getGroupUserIds(String groupId) {
        ChannelGroup channels = getChannelGroup(groupId);
        return channels.stream().map(SessionUtil::getSession).map(Session::getUserId).collect(Collectors.toList());
    }

    public static List<String> getGroupUserNames(String groupId) {
        ChannelGroup channels = getChannelGroup(groupId);
        if (channels == null) {
            log.error("groupId为" + groupId + "的群聊不存在");
            return Collections.emptyList();
        }
        return channels.stream().map(SessionUtil::getSession).map(Session::getUsername).collect(Collectors.toList());
    }

}

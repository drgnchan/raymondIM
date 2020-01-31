package com.raymond.comct.command;

import com.raymond.comct.codec.packet.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Scanner;

public class CreateGroupConsoleCommand implements ConsoleCommand {

    private static final String USER_ID_SPLITTER = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();
        System.out.println("【拉人群聊】请输入要拉进来的用户id，用分隔符【" + USER_ID_SPLITTER + "】分开：");
        String userIdsString = scanner.next();
        String[] userIds = userIdsString.split(USER_ID_SPLITTER);
        createGroupRequestPacket.setUserIds(Arrays.asList(userIds));

        channel.writeAndFlush(createGroupRequestPacket);

    }
}

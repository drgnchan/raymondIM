package com.raymond.comct.command;

import com.raymond.comct.codec.packet.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入用户id：");
        String userId = scanner.nextLine();
        System.out.println("请输入消息：");
        String msg = scanner.nextLine();

        MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
        messageRequestPacket.setToUserId(userId);
        messageRequestPacket.setMessage(msg);

        channel.writeAndFlush(messageRequestPacket);
    }
}

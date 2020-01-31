package com.raymond.comct.command;

import com.raymond.comct.codec.packet.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入用户id登录：");
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        String userId = scanner.nextLine();
        loginRequestPacket.setUserId(userId);
        loginRequestPacket.setPassword("pwd");
        channel.writeAndFlush(loginRequestPacket);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

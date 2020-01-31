package com.raymond.comct.command;

import com.raymond.comct.command.ConsoleCommand;
import io.netty.channel.Channel;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Data
public class ConsoleCommandManager implements ConsoleCommand {

    private Map<String, ConsoleCommand> consoleCommandMap;

    public ConsoleCommandManager() {
        this.consoleCommandMap = new HashMap<>();
        consoleCommandMap.put("sendToUser", new SendToUserConsoleCommand());
        consoleCommandMap.put("logout", new LogoutConsoleCommand());
        consoleCommandMap.put("createGroup", new CreateGroupConsoleCommand());
        consoleCommandMap.put("joinGroup", new JoinGroupConsoleCommand());

    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入控制台命令：");
        String command = scanner.nextLine();

        ConsoleCommand consoleCommand = this.consoleCommandMap.get(command);

        if (consoleCommand != null) {
            consoleCommand.exec(scanner, channel);
        } else {
            System.err.println("无法识别该控制台命令");
        }

    }
}

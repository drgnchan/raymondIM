package com.raymond.comct.codec.packet;

import com.raymond.comct.command.Command;
import lombok.Data;

@Data
public class MessageResponsePacket extends Packet {

    private String fromUserId;

    private String fromUserName;

    private String message;

    @Override
    public Byte getCommand() {
        return Command.MSG_RESPONSE;
    }
}

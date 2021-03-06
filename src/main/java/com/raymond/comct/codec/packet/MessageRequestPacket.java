package com.raymond.comct.codec.packet;

import com.raymond.comct.command.Command;
import lombok.Data;

@Data
public class MessageRequestPacket extends Packet {

    private String toUserId;

    private String message;

    @Override
    public Byte getCommand() {
        return Command.MSG_REQUEST;
    }
}

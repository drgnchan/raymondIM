package com.raymond.comct.codec.packet;

import com.raymond.comct.codec.Command;
import lombok.Data;

@Data
public class MessageResponsePacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return Command.MSG_RESPONSE;
    }
}

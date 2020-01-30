package com.raymond.comct.codec.packet;

import com.raymond.comct.codec.Command;
import lombok.Data;

@Data
public class LoginResponsePacket extends Packet {

    private String reason;
    private Boolean success;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE_COMMAND;
    }
}

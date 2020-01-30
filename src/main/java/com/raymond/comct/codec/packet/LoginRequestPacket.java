package com.raymond.comct.codec.packet;

import com.raymond.comct.codec.Command;
import lombok.Data;

@Data
public class LoginRequestPacket extends Packet {

    private String userId;
    private String username;
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST_COMMAND;
    }
}

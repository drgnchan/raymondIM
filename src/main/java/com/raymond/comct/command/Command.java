package com.raymond.comct.command;

import com.raymond.comct.codec.packet.*;

import java.util.HashMap;
import java.util.Map;

public interface Command {
    byte LOGIN_REQUEST_COMMAND = 1;
    byte LOGIN_RESPONSE_COMMAND = 2;
    byte MSG_REQUEST = 3;
    byte MSG_RESPONSE = 4;
    byte CREATE_GROUP_REQUEST = 5;
    byte CREATE_GROUP_RESPONSE = 6;
    Map<Byte, Class<? extends Packet>> COMMAND_TO_PACKET_CLASS = new HashMap<Byte, Class<? extends Packet>>(){{
        put(Command.LOGIN_REQUEST_COMMAND, LoginRequestPacket.class);
        put(Command.LOGIN_RESPONSE_COMMAND, LoginResponsePacket.class);
        put(Command.MSG_REQUEST, MessageRequestPacket.class);
        put(Command.MSG_RESPONSE, MessageResponsePacket.class);
        put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        put(Command.CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
    }};
}

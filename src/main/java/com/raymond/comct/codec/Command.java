package com.raymond.comct.codec;

import com.raymond.comct.codec.packet.LoginRequestPacket;
import com.raymond.comct.codec.packet.LoginResponsePacket;
import com.raymond.comct.codec.packet.Packet;

import java.util.HashMap;
import java.util.Map;

public interface Command {
    byte LOGIN_REQUEST_COMMAND = 1;
    byte LOGIN_RESPONSE_COMMAND = 2;
    Map<Byte, Class<? extends Packet>> COMMAND_TO_PACKET_CLASS = new HashMap<Byte, Class<? extends Packet>>(){{
        put(Command.LOGIN_REQUEST_COMMAND, LoginRequestPacket.class);
        put(Command.LOGIN_RESPONSE_COMMAND, LoginResponsePacket.class);
    }};
}

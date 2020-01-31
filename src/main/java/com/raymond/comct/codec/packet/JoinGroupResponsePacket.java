package com.raymond.comct.codec.packet;

import com.raymond.comct.command.Command;
import lombok.Data;

import java.util.List;

@Data
public class JoinGroupResponsePacket extends Packet {

    private String newUserId;
    private List<String> originalUserIds;
    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_RESPONSE;
    }
}

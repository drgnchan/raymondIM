package com.raymond.comct.codec.packet;

import com.raymond.comct.command.Command;
import lombok.Data;

import java.util.List;

@Data
public class CreateGroupResponsePacket extends Packet {

    private Boolean success;
    private List<String> usernames;
    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}

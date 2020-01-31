package com.raymond.comct.codec.packet;

import com.raymond.comct.command.Command;
import lombok.Data;

import java.util.List;

@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIds;



    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}

package com.raymond.comct.codec.packet;

import lombok.Data;

@Data
public abstract class Packet {
    /**
     * 版本
     */
    private Byte version = 1;

    /**
     * 指令
     */
    public abstract Byte getCommand();
}

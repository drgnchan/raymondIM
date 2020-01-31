package com.raymond.comct.codec;

import com.raymond.comct.codec.packet.Packet;
import com.raymond.comct.codec.serialization.Serializer;
import com.raymond.comct.command.Command;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class PacketCodec {

    public static PacketCodec INSTANCE = new PacketCodec();

    public static final int MAGIC_NUMBER = 0x88888888;

    public ByteBuf encode(Packet packet) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        byte[] bytes = Serializer.DEFAULT.serialize(packet);
        //魔数
        byteBuf.writeInt(MAGIC_NUMBER);
        //版本号
        byteBuf.writeByte(packet.getVersion());
        //序列化算法
        byteBuf.writeByte(Serializer.DEFAULT.getSerializeAlgorithm());
        //命令
        byteBuf.writeByte(packet.getCommand());
        //数据长度
        byteBuf.writeInt(bytes.length);
        //数据
        byteBuf.writeBytes(bytes);

        return byteBuf;

    }

    public Packet decode(ByteBuf byteBuf) {
        //跳过魔数
        byteBuf.skipBytes(4);
        //跳过版本号
        byteBuf.skipBytes(1);
        //序列化算法
        byte algorithm = byteBuf.readByte();
        //命令
        byte command = byteBuf.readByte();
        //数据长度
        int length = byteBuf.readInt();
        //数据
        byte[] data = new byte[length];
        byteBuf.readBytes(data);

        Serializer serializer = getSerializer(algorithm);
        Class<? extends Packet> clazz = getClazz(command);
        if (clazz != null && serializer != null) {
            return serializer.deserialize(clazz, data);
        }

        return null;
    }

    public Class<? extends Packet> getClazz(byte command) {
        return Command.COMMAND_TO_PACKET_CLASS.get(command);
    }

    public Serializer getSerializer(byte algorithm) {
        return Serializer.ALGORITHM_TO_SERIALIZER.get(algorithm);
    }


}

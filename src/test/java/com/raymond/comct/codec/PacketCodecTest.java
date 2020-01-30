package com.raymond.comct.codec;

import com.raymond.comct.codec.packet.LoginRequestPacket;
import com.raymond.comct.codec.packet.Packet;
import com.raymond.comct.codec.serialization.JSONSerializer;
import com.raymond.comct.codec.serialization.Serializer;
import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.Arrays;

class PacketCodecTest {

    @Test
    public void encode() {

        Serializer serializer = new JSONSerializer();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setVersion(((byte) 1));
        loginRequestPacket.setUserId("123");
        loginRequestPacket.setUsername("raymond");
        loginRequestPacket.setPassword("password");

        PacketCodec packetCodeC = new PacketCodec();
        ByteBuf byteBuf = packetCodeC.encode(loginRequestPacket);
        Packet decodedPacket = packetCodeC.decode(byteBuf);

        Assert.isTrue(Arrays.equals(serializer.serialize(decodedPacket), serializer.serialize(loginRequestPacket)), "wrong");

    }

}
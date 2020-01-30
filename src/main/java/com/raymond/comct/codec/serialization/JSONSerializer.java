package com.raymond.comct.codec.serialization;

import com.alibaba.fastjson.JSON;

public class JSONSerializer implements Serializer {
    @Override
    public Byte getSerializeAlgorithm() {
        return SerializeAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object o) {
        return JSON.toJSONBytes(o);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}

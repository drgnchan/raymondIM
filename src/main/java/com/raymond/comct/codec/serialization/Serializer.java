package com.raymond.comct.codec.serialization;

import java.util.HashMap;
import java.util.Map;

public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    Map<Byte, Serializer> ALGORITHM_TO_SERIALIZER = new HashMap<Byte, Serializer>() {{
        put(SerializeAlgorithm.JSON, new JSONSerializer());
    }};

    Byte getSerializeAlgorithm();

    byte[] serialize(Object o);

    <T> T deserialize(Class<T> clazz, byte[] bytes);
}

package com.baizhi.common.impl;

import com.baizhi.common.ObjectSerializer;
import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;

/**
 * 对象序列化实现
 * @author gaozhy
 * @date 2018/3/11.23:13
 */
public class JavaObjectSerialization implements ObjectSerializer {

    @Override
    public byte[] serialization(Object obj) {
        return SerializationUtils.serialize((Serializable) obj);
    }

    @Override
    public Object deSerialization(byte[] bytes) {

        return SerializationUtils.deserialize(bytes);
    }
}

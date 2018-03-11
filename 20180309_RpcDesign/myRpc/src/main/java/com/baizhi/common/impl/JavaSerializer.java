package com.baizhi.common.impl;

import com.baizhi.common.ObjectSerializer;
import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;

/**
 * @author gaozhy
 * @date 2018/3/9.16:09
 */
public class JavaSerializer implements ObjectSerializer {

    @Override
    public byte[] serialization(Object object) {
        return SerializationUtils.serialize((Serializable) object);
    }

    @Override
    public Object deSerialization(byte[] bytes) {
        return SerializationUtils.deserialize(bytes);
    }
}

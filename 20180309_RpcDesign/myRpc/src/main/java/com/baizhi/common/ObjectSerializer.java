package com.baizhi.common;

/**
 * 对象序列化器
 *
 * @author gaozhy
 * @date 2018/3/9.15:20
 */
public interface ObjectSerializer {

    /**
     * 序列化方法
     * @param object
     * @return
     */
    byte[] serialization(Object object);

    /**
     * 反序列化方法
     * @param bytes
     * @return
     */
    Object deSerialization(byte[] bytes);
}

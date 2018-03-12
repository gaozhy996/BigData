package com.baizhi.common;

/**
 * 对象的序列化接口
 *
 * @author gaozhy
 * @date 2018/3/11.22:16
 */
public interface ObjectSerializer {

    /**
     * 序列化方法
     *
     * @param obj
     * @return
     */
    byte[] serialization(Object obj);

    /**
     * 反序列化方法
     *
     * @param bytes
     * @return
     */
    Object deSerialization(byte[] bytes);
}

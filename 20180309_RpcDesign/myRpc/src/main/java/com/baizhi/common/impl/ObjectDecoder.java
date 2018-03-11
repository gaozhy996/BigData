package com.baizhi.common.impl;

import com.baizhi.common.ObjectSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author gaozhy
 * @date 2018/3/9.16:45
 */
public class ObjectDecoder extends MessageToMessageDecoder {
    private ObjectSerializer objectSerializer;

    public ObjectDecoder(ObjectSerializer objectSerializer){
        this.objectSerializer = objectSerializer;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        Object obj = objectSerializer.deSerialization(bytes);
        out.add(obj);
    }
}

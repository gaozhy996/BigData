package com.baizhi.common.impl;

import com.baizhi.common.ObjectSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author gaozhy
 * @date 2018/3/12.9:44
 */
public class ObjectEncoder extends MessageToMessageEncoder {

    private ObjectSerializer objectSerializer;

    public ObjectEncoder(ObjectSerializer objectSerializer){
        this.objectSerializer = objectSerializer;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
        System.out.println("编码中！！！");
        byte[] bytes = objectSerializer.serialization(msg);

        ByteBuf byteBuf = Unpooled.buffer();

        byteBuf.writeBytes(bytes);

        out.add(byteBuf);
    }
}

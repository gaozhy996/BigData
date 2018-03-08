package com.baizhi.netty3;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.apache.commons.lang.SerializationUtils;

import java.util.List;

/**
 * @author gaozhy
 * @date 2018/3/8.11:04
 */
public class ObjectDecodeHandlerAdapter extends MessageToMessageDecoder {
    /**
     * 解码 将ByteBuf 转换成对象
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {

        System.out.println("解码中.......");

        ByteBuf byteBuf = (ByteBuf) msg;

        byte[] bytes = new byte[byteBuf.readableBytes()];

        byteBuf.readBytes(bytes);

        System.out.println(new String(bytes));

        //Object obj = SerializationUtils.deserialize(bytes);

        Object obj = JSONObject.parse(new String(bytes));

        out.add(obj);
    }
}

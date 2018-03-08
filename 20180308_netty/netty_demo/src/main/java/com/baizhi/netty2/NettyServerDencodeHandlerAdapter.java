package com.baizhi.netty2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.apache.commons.lang.SerializationUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author gaozhy
 * @date 2018/3/8.11:04
 */
public class NettyServerDencodeHandlerAdapter extends MessageToMessageDecoder {
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

        Object obj = SerializationUtils.deserialize(bytes);

        out.add(obj);
    }
}

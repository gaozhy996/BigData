package com.baizhi.netty3;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author gaozhy
 * @date 2018/3/8.11:05
 */
public class ObjectEncodeHandlerAdapter extends MessageToMessageEncoder {

    /**
     * 编码方法 对对象进行编码
     * @param ctx
     * @param msg  outBound数据
     * @param out  数据帧
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
        System.out.println("编码中......");
        // 序列化
        //byte[] bytes = SerializationUtils.serialize((Serializable) msg);

        byte[] bytes = JSONObject.toJSONBytes(msg, SerializerFeature.SortField);

        ByteBuf byteBuf = Unpooled.buffer();

        byteBuf.writeBytes(bytes);

        out.add(byteBuf);
    }
}

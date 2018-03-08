package com.baizhi.netty1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

import java.util.Date;

/**
 * @author gaozhy
 * @date 2018/3/8.10:10
 */
public class NettyClientChannelHandlerAdapter extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端: 发送请求中......");
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeBytes("Hello Server,I am Client".getBytes());
        ChannelFuture channelFuture = ctx.writeAndFlush(buffer);

        channelFuture.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        channelFuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("收到服务器响应："+byteBuf.toString(CharsetUtil.UTF_8));
    }
}

package com.baizhi.netty1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

/**
 * @author gaozhy
 * @date 2018/3/8.10:07
 */
public class NettyServerChannelHandlerAdapter extends ChannelHandlerAdapter {

    /**
     * 异常回调方法
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("服务器错误！");
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 读事件回调方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("收到客户端数据:"+byteBuf.toString(CharsetUtil.UTF_8));
        ChannelFuture channelFuture = ctx.writeAndFlush(byteBuf);
        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }
}

package com.baizhi.netty2;

import io.netty.buffer.ByteBuf;
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
        // ===============测试发送对象=====================
        // 注意：netty只会捕获 网络异常  序列化异常 无法捕获 需做额外配置
        // 解决方案：为pipeline添加编解码器
        ChannelFuture channelFuture = null;
        channelFuture = ctx.writeAndFlush(new User(1, "zs" ));
        channelFuture.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        channelFuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        //================================================
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf byteBuf = (ByteBuf) msg;
//        System.out.println("收到服务器响应："+byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println(msg);
    }
}

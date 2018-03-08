package com.baizhi.netty3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * 基于Netty的Server程序
 *  1. 创建服务引导对象
 *  2. 创建请求转发和IO处理线程池
 *  3. 设置服务的实现类
 *  4. 绑定线程池
 *  5. 初始化通讯管道
 *  6. 绑定端口 接受客户端请求
 *  7. 关闭通道
 *  8. 释放线程池资源
 *
 * @author gaozhy
 * @date 2018/3/8.9:35
 */
public class NettyServerBootStrap {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup boss = null;
        EventLoopGroup worker = null;
        try {
            ServerBootstrap sbs = new ServerBootstrap();
            boss = new NioEventLoopGroup();
            worker = new NioEventLoopGroup();
            sbs.channel(NioServerSocketChannel.class);
            sbs.group(boss,worker);
            sbs.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    // 初始化通道
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));//数据帧解码
                    pipeline.addLast(new LengthFieldPrepender(2));//数据头编码
                    pipeline.addLast(new ObjectEncodeHandlerAdapter());
                    pipeline.addLast(new ObjectDecodeHandlerAdapter());
                    pipeline.addLast(new NettyServerChannelHandlerAdapter());
                }
            });
            // 异步执行的结果
            System.out.println("服务器：我再9999监听！");
            ChannelFuture channelFuture = sbs.bind(9999).sync();
            channelFuture.channel().closeFuture().sync();
            System.out.println("服务器：我再9999监听······");
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }
}

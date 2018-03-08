package com.baizhi.netty3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * 基于Netty的Client程序
 *
 * 1. 初始化客户端引导程序
 * 2. 创建IO处理线程池
 * 3. 设置客户端的实现类
 * 4. 绑定线程池
 * 5. 初始化客户端通道
 * 6. 绑定服务器
 * 7. 关闭通道
 * 8. 释放资源
 *
 * @author gaozhy
 * @date 2018/3/8.9:55
 */
public class NettyClientBootstrap {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup worker = null;
        try {
            Bootstrap bootstrap = new Bootstrap();
            worker = new NioEventLoopGroup();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(worker);

            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));//数据帧解码
                    pipeline.addLast(new LengthFieldPrepender(2));//数据头编码
                    pipeline.addLast(new ObjectDecodeHandlerAdapter());
                    pipeline.addLast(new ObjectEncodeHandlerAdapter());
                    pipeline.addLast(new NettyClientChannelHandlerAdapter());
                }
            });

            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9999).sync();

            channelFuture.channel().closeFuture().sync();
        } finally {
            worker.shutdownGracefully();
        }
    }
}

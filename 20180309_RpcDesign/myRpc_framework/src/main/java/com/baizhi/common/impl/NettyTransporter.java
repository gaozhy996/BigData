package com.baizhi.common.impl;

import com.baizhi.common.HostAndName;
import com.baizhi.common.MethodInvokeDataWrap;
import com.baizhi.common.ResultWrap;
import com.baizhi.common.Transporter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 *
 * 基于netty的服务消费调用器
 * @author gaozhy
 * @date 2018/3/12.9:26
 */
public class NettyTransporter implements Transporter {

    private Bootstrap bootstrap;
    private EventLoopGroup workerGroup;

    public NettyTransporter(){
        this.bootstrap = new Bootstrap();
        this.workerGroup = new NioEventLoopGroup();
    }

    @Override
    public ResultWrap invoke(final MethodInvokeDataWrap methodInvokeDataWrap, HostAndName hostAndName) {
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(workerGroup);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline channelPipeline = ch.pipeline();
                // 数据帧头解码器
                channelPipeline.addLast(new LengthFieldBasedFrameDecoder(65535,0,2,0,2));
                // 数据帧头编码器
                channelPipeline.addLast(new LengthFieldPrepender(2));
                channelPipeline.addLast(new ObjectEncoder(new JavaObjectSerialization()));
                channelPipeline.addLast(new ObjectDecoder(new JavaObjectSerialization()));
                channelPipeline.addLast(new ChannelHandlerAdapter(){
                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                        cause.printStackTrace();
                        ctx.close();
                    }
                    /**
                     * 通道可用时 发送请求数据 进行远程调用
                     * @param ctx
                     * @throws Exception
                     */
                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("消费者：发送数据！");
                        ctx.writeAndFlush(methodInvokeDataWrap);
                    }

                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        ResultWrap resultWrap = (ResultWrap) msg;
                        methodInvokeDataWrap.setResultWrap(resultWrap);
                    }
                });
            }
        });
        try {
            ChannelFuture channelFuture = bootstrap.connect(hostAndName.getHostName(), hostAndName.getPort()).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return methodInvokeDataWrap.getResultWrap();
    }

    @Override
    public void close() {
        workerGroup.shutdownGracefully();
    }
}

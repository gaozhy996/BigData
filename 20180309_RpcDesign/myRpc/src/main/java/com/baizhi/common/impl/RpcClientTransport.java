package com.baizhi.common.impl;

import com.baizhi.common.HostAndName;
import com.baizhi.common.MethodInvokeDataWrap;
import com.baizhi.common.ResultWrap;
import com.baizhi.common.Transport;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;

import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * @author gaozhy
 * @date 2018/3/11.11:30
 */
public class RpcClientTransport implements Transport{


    private Bootstrap bootstrap;
    private EventLoopGroup workerGroup;

    // 远程调用的返回值
    private ResultWrap resultWrap;

    public RpcClientTransport() {
        init();
    }
    public ResultWrap getResultWrap() {
        return resultWrap;
    }

    public void setResultWrap(ResultWrap resultWrap) {
        this.resultWrap = resultWrap;
    }

    /**
     * 网络信息初始化方法
     */
    public void init(){
        this.bootstrap = new Bootstrap();
        this.workerGroup = new NioEventLoopGroup();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(workerGroup);
    }

    @Override
    public void invoke(HostAndName hostAndName, MethodInvokeDataWrap methodInvokeDataWrap) {

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                //数据帧头解码器
                pipeline.addLast(new LengthFieldBasedFrameDecoder(65535,0,2,0,2));
                //数据帧头编码器
                pipeline.addLast(new LengthFieldPrepender(2));
                pipeline.addLast(new ObjectEncoder(new JavaSerializer()));
                pipeline.addLast(new ObjectDecoder(new JavaSerializer()));
                pipeline.addLast(new ChannelHandlerAdapter(){
                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                        cause.printStackTrace();
                        ctx.close();
                    }

                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("发送请求数据："+methodInvokeDataWrap);
                        ChannelFuture channelFuture = ctx.writeAndFlush(methodInvokeDataWrap);
                        channelFuture.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                        channelFuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
                    }

                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        resultWrap = (ResultWrap) msg;
                    }
                });
            }
        });

        try {
            ChannelFuture channelFuture = bootstrap.connect(hostAndName.getHostname(), hostAndName.getPort()).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        workerGroup.shutdownGracefully();
    }
}

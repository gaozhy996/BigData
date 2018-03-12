package com.baizhi.common;

import com.baizhi.common.*;
import com.baizhi.common.impl.JavaObjectSerialization;
import com.baizhi.common.impl.ObjectDecoder;
import com.baizhi.common.impl.ObjectEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;

/**
 *
 * 服务提供者引导程序
 * @author gaozhy
 * @date 2018/3/12.9:30
 */
public class ProviderBootstrap {
    /**
     * 注册中心
     */
    private Registry registry;

    /**
     * 需要暴露的服务
     */
    private Map<String,Object> exposeBeans;
    /**
     * netty服务端口号
     */
    private int port;

    private ServerBootstrap serverBootstrap;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    /**
     *
     * @param registry 注册中心
     * @param exposeBeans 暴露服务信息
     * @param port netty服务端口
     */
    public ProviderBootstrap(Registry registry,Map<String,Object> exposeBeans,int port){
        this.registry = registry;
        this.exposeBeans = exposeBeans;
        this.port = port;
        serverBootstrap = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
    }

    /**
     * 初始化方法
     *     注册服务 + 接受客户端远程调用
     */
    public void init(){
        System.out.println("开始初始化");
        // ============处理消费者远程调用 start ==================
        serverBootstrap.group(bossGroup,workerGroup);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
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
                     * 远程调用处理方法
                     * @param ctx
                     * @param msg
                     * @throws Exception
                     */
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        MethodInvokeDataWrap methodInvokeDataWrap = (MethodInvokeDataWrap) msg;
                        // 请求数据 接口信息 方法名 参数列表 参数类型
                        MethodInvokeData methodInvokeData = methodInvokeDataWrap.getMethodInvokeData();
                        // 注：附件信息暂不处理
                        
                        // 获取消费接口信息
                        Class<?> targetInterface = methodInvokeData.getTargetInterface();
                        
                        // 根据接口获取本地实现
                        Object nativeObj = exposeBeans.get(targetInterface.getName());

                        // 获取服务提供者本地方法对象
                        Method method = targetInterface.getMethod(methodInvokeData.getMethodName(), methodInvokeData.getParameterTypes());

                        Result result = new Result();
                        try {
                            Object returnValue = method.invoke(nativeObj, methodInvokeData.getArgs());
                            System.out.println("调用的返回值："+returnValue);
                            result.setReturnValue(returnValue);
                        } catch (Exception e) {
                            e.printStackTrace();
                            result.setException(e);
                        }
                        ResultWrap resultWrap = new ResultWrap();
                        resultWrap.setResult(result);
                        resultWrap.setAttachment(methodInvokeDataWrap.getAttachment());

                        // 响应调用结果
                        ChannelFuture channelFuture = ctx.writeAndFlush(resultWrap);
                        channelFuture.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                        channelFuture.addListener(ChannelFutureListener.CLOSE);
                        channelFuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
                    }
                });
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
                    System.out.println("提供者：我在"+port+"监听");
                    channelFuture.channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        // ============ 处理消费者远程调用 end ==================

        // ============ 注册服务 start==============
        Set<String> keySet = exposeBeans.keySet();
        try {
            HostAndName hostAndName = new HostAndName(InetAddress.getLocalHost().getHostAddress(), port);
            for (String key : keySet) {
                registry.registerService(key,hostAndName);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        // ============ 注册服务 end==============
    }

    public void close(){
        registry.close();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}

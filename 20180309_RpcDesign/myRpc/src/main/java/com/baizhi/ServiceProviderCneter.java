package com.baizhi;

import com.baizhi.common.*;
import com.baizhi.common.impl.JavaSerializer;
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
 * 服务提供中心：注册服务 + 接受客户端的调用
 *
 * @author gaozhy
 * @date 2018/3/9.16:20
 */
public class ServiceProviderCneter {

    /**
     * 注册中心
     */
    private Registry registry;
    private int port;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    // 暴露服务的map
    private Map<String,Object> exposeBean;

    public ServiceProviderCneter(Registry registry, int port,Map<String,Object> exposeBean) {
        this.port = port;
        this.registry = registry;
        this.exposeBean = exposeBean;
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
    }

    /**
     * 启动方法
     */
    public void start() throws InterruptedException {
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        //数据帧头解码器
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(65535,0,2,0,2));
                        //数据帧头编码器
                        pipeline.addLast(new LengthFieldPrepender(2));
                        pipeline.addLast(new ObjectEncoder(new JavaSerializer()));
                        pipeline.addLast(new ObjectDecoder(new JavaSerializer()));
                        // 初始化管道
                        pipeline.addLast(new ChannelHandlerAdapter() {
                            @Override
                            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                cause.printStackTrace();
                                ctx.close();
                            }
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                MethodInvokeDataWrap methodInvokeDataWrap = (MethodInvokeDataWrap) msg;
                                // 获取请求封装的数据
                                MethodInvokeData methodInvokeData = methodInvokeDataWrap.getMethodInvokeData();
                                System.out.println("接受到请求数据："+methodInvokeData);
                                Object realTarget = exposeBean.get(methodInvokeData.getTargetInterface().getName());
                                System.out.println(methodInvokeData.getMethodName()+"\t"+methodInvokeData.getParamterTypes());
                                Method method = realTarget.getClass().getMethod(methodInvokeData.getMethodName(), methodInvokeData.getParamterTypes());
                                Result result = new Result();
                                try {
                                    Object returnValue = method.invoke(realTarget, methodInvokeData.getArgs());
                                    System.out.println(returnValue);
                                    result.setReturnValue(returnValue);
                                } catch (Exception e) {
                                    result.setE(e);
                                    e.printStackTrace();
                                }
                                // 设置附件信息
                                ResultWrap resultWrap = new ResultWrap();
                                resultWrap.setResult(result);
                                resultWrap.setAttachments(null);
                                System.out.println("响应回结果:"+resultWrap);
                                ChannelFuture channelFuture = ctx.writeAndFlush(resultWrap);
                                channelFuture.addListener(ChannelFutureListener.CLOSE);
                                channelFuture.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                                channelFuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
                            }
                        });
                    }
                });

        // 异步启动netty server
        // 新建线程原因：netty server启动时会阻塞进程 导致spring工厂无法正常加载
        new Thread(new Runnable() {
            @Override
            public void run() {
                ChannelFuture f = null;
                try {
                    f = b.bind(port).sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    f.channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // 注册服务
        Set<String> keys = exposeBean.keySet();
        try {
            String host = InetAddress.getLocalHost().getHostAddress();
            final HostAndName hostAndName = new HostAndName(host, port);
            keys.forEach(interfaceName ->
                    registry.register(interfaceName,hostAndName)
            );
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


    public void close() {
        registry.close();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}

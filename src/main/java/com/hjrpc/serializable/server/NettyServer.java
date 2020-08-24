package com.hjrpc.serializable.server;

import com.hjrpc.constant.Constant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            ServerBusiHandler serverBusiHandler = new ServerBusiHandler();
            b.group(group).localAddress(Constant.DEFAULT_PORT).channel(NioServerSocketChannel.class)
                    .childHandler(new ServerChannelHandlerInitializer(serverBusiHandler));

            ChannelFuture f = b.bind().sync();
            System.out.println("server is started!");
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

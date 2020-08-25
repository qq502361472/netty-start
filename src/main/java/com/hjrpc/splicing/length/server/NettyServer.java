package com.hjrpc.splicing.length.server;

import com.hjrpc.constant.Constant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.io.UnsupportedEncodingException;

public class NettyServer {

    public static void main(String[] args) {
        start();
    }

    private static void start() {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            final NettyServerHandler serverHandler = new NettyServerHandler();

            b.group(group).channel(NioServerSocketChannel.class)
                    .localAddress(Constant.DEFAULT_PORT)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws UnsupportedEncodingException {
                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(65535,
                                    0,2,0,2));
                            ch.pipeline().addLast(new LengthFieldPrepender(2));
                            ch.pipeline().addLast(serverHandler);
                        }
                    });

            ChannelFuture f = b.bind().sync();

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

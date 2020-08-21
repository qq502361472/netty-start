package com.hjrpc.splicing.fixed.client;

import com.hjrpc.constant.Constant;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;

public class NettyClient {

    public static void main(String[] args) {
        start();
    }

    private static void start() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(Constant.DEFAULT_HOST, Constant.DEFAULT_PORT)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new FixedLengthFrameDecoder(Constant.getFixedMessage()
                                    .getBytes("UTF-8").length));
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            ChannelFuture f = b.connect().sync();

            f.channel().closeFuture().sync();
            System.out.println("out");
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

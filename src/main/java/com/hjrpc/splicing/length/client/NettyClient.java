package com.hjrpc.splicing.length.client;

import com.hjrpc.constant.Constant;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

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
                            ByteBuf delimiter = Unpooled.copiedBuffer(Constant.DELIMITER.getBytes("UTF-8"));
                            //lengthFieldLength 消息头部添加长度字段，这个字段占用的字节数，2代表65536
                            ch.pipeline().addLast(new LengthFieldPrepender(2));
                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(65535,
                                    0,2,0,2));
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

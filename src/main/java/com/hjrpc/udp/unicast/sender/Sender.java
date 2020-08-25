package com.hjrpc.udp.unicast.sender;

import com.hjrpc.constant.Constant;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

public class Sender {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST,true)
                    .handler(new SenderChannelHandlerInitalizer());
            Channel f = b.bind(0).sync().channel();
            f.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("this is a udp message!", CharsetUtil.UTF_8),
                    new InetSocketAddress(Constant.DEFAULT_HOST,Constant.DEFAULT_PORT)));
            System.out.println("send message");
            f.closeFuture().await(15000);
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

package com.hjrpc.udp.broadcast.sender;

import com.hjrpc.constant.Constant;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

public class BroadcastSender {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST,true)
            .handler(new BroadcastSendHandler());
            Channel ch = b.bind(0).sync().channel();
            while (true){
                String message = Constant.getMessage(false);
                Thread.sleep(1500L);
                System.out.println("send:"+message);
                ch.writeAndFlush(new DatagramPacket(
                        Unpooled.copiedBuffer(message, CharsetUtil.UTF_8),
                        new InetSocketAddress("255.255.255.255",Constant.DEFAULT_PORT)
                ));
            }
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

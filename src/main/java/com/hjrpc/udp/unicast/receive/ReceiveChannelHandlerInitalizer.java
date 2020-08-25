package com.hjrpc.udp.unicast.receive;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class ReceiveChannelHandlerInitalizer extends ChannelInitializer<NioDatagramChannel> {
    @Override
    protected void initChannel(NioDatagramChannel ch) throws Exception {
        ch.pipeline().addLast(new ReceiveBusiHandler());
    }
}

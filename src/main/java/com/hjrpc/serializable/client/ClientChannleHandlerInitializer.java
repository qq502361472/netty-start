package com.hjrpc.serializable.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.LineBasedFrameDecoder;

public class ClientChannleHandlerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //解决粘包问题
        pipeline.addLast(new LengthFieldPrepender(2));
        //将发出去消息进行序列化编码
        pipeline.addLast(new MessagePackEncoder());
        pipeline.addLast(new LengthFieldBasedFrameDecoder(65535,0,2,
                0,2));
        //业务handle
        pipeline.addLast(new ClientBusiHandler());
    }
}

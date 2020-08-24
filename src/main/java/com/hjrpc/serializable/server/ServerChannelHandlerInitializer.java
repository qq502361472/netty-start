package com.hjrpc.serializable.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.LineBasedFrameDecoder;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class ServerChannelHandlerInitializer extends ChannelInitializer<SocketChannel> {

    private ServerBusiHandler serverBusiHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //解决粘包问题
        pipeline.addLast(new LengthFieldBasedFrameDecoder(65535,0,
                2,0,2));
        //获取到的消息进行解码
        pipeline.addLast(new MessagePackDeCoder());

        pipeline.addLast(new LengthFieldPrepender(2));
        //业务handle
        pipeline.addLast(serverBusiHandler);

    }
}

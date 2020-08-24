package com.hjrpc.serializable.server;

import com.hjrpc.entity.SClass;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.atomic.AtomicInteger;

@ChannelHandler.Sharable
public class ServerBusiHandler extends ChannelInboundHandlerAdapter {
    public AtomicInteger count = new AtomicInteger(0);
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SClass clazz = (SClass) msg;
        System.out.println("server accept object:" + clazz);
        ctx.writeAndFlush(Unpooled.copiedBuffer("class register success!",CharsetUtil.UTF_8));
        System.out.println(count.incrementAndGet());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        System.out.println("channelReadComplete");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

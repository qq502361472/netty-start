package com.hjrpc.httpserver.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@ChannelHandler.Sharable
public class NettyServerHandler  extends ChannelInboundHandlerAdapter {
    public AtomicInteger count = new AtomicInteger(0);
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest request = (FullHttpRequest)msg;
        String uri = request.uri();
        String message = "server accept: 访问时间 " + new Date();
        System.out.println(message);
        FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,Unpooled.copiedBuffer(message.getBytes(CharsetUtil.UTF_8)));
        fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,
                "text/plain;charset=UTF-8");
        System.out.println(count.incrementAndGet());
        ctx.writeAndFlush(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
//        ctx.close();
    }


}

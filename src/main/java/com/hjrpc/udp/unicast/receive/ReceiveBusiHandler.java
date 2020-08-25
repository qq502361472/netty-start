package com.hjrpc.udp.unicast.receive;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

public class ReceiveBusiHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        String req = packet.content().toString(CharsetUtil.UTF_8);
        System.out.println("req:"+req);
        InetSocketAddress sender = packet.sender();
        System.out.println(sender.toString());
        DatagramPacket datagramPacket = new DatagramPacket(Unpooled.copiedBuffer("this is receive message",
                CharsetUtil.UTF_8), sender);
        ctx.writeAndFlush(datagramPacket);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

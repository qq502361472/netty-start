package com.hjrpc.serializable.server;

import com.hjrpc.entity.SClass;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.concurrent.EventExecutorGroup;
import org.msgpack.MessagePack;

import java.util.List;

public class MessagePackDeCoder extends MessageToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object o, List list) throws Exception {
        ByteBuf byteBuf = (ByteBuf) o;
        int length = byteBuf.readableBytes();
        byte[] bytes = new byte[length];
        byteBuf.getBytes(byteBuf.readerIndex(),bytes,0,length);
        MessagePack messagePack = new MessagePack();
        SClass sClass = messagePack.read(bytes, SClass.class);
        list.add(sClass);
    }
}

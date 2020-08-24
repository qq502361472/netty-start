package com.hjrpc.serializable.client;

import com.hjrpc.entity.SClass;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.concurrent.EventExecutorGroup;
import org.msgpack.MessagePack;

public class MessagePackEncoder extends MessageToByteEncoder<SClass> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, SClass sClass, ByteBuf byteBuf) throws Exception {
        MessagePack messagePack = new MessagePack();
        byte[] bytes = messagePack.write(sClass);
        byteBuf.writeBytes(bytes);
    }
}

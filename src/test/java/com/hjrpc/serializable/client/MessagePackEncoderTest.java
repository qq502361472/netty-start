package com.hjrpc.serializable.client;

import com.hjrpc.serializable.server.MessagePackDeCoder;
import com.hjrpc.util.MessageUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;
import org.msgpack.MessagePack;

import static org.junit.Assert.*;

public class MessagePackEncoderTest {

    @Test
    public void test(){

        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new MessagePackEncoder());
        assertTrue(embeddedChannel.writeOutbound(MessageUtil.getClassInfo()));
        assertTrue(embeddedChannel.finish());
        ByteBuf byteBuf = embeddedChannel.readOutbound();

        EmbeddedChannel embeddedChannel2 = new EmbeddedChannel(new MessagePackDeCoder());
        assertTrue(embeddedChannel2.writeInbound(byteBuf));
        assertTrue(embeddedChannel2.finish());
        Object o = embeddedChannel2.readInbound();
        System.out.println(o);

    }

}
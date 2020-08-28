package com.hjrpc.httpserver.server;

import com.hjrpc.constant.Constant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

public class NettyServer {
    static SslContext sslContext;

    public static void main(String[] args) {
        start();
    }

    private static void start() {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslContext = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (SSLException e) {
            e.printStackTrace();
        }
        try {
            ServerBootstrap b = new ServerBootstrap();
            final NettyServerHandler serverHandler = new NettyServerHandler();

            b.group(group).channel(NioServerSocketChannel.class)
                    .localAddress(Constant.DEFAULT_PORT)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            //SSL认证
                            ch.pipeline().addLast(sslContext.newHandler(ch.alloc()));
                            ch.pipeline().addLast(new HttpServerCodec());
                            ch.pipeline().addLast(new HttpObjectAggregator(10*1024*1024));
                            ch.pipeline().addLast(serverHandler);
                        }
                    });

            ChannelFuture f = b.bind().sync();

            f.channel().closeFuture().sync();

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

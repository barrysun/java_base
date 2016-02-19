package com.ch05;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class AcceptHandler implements Handler {

	@Override
	public void handle(SelectionKey key) throws IOException {
		ServerSocketChannel serverSocketChannel=(ServerSocketChannel)key.channel();
		//在非阻塞模式下，serverSocketChannel.accept()有可能返回null
		//判断socketChannel是否为null,可以使程序更加健壮，避免NullPointerException
		SocketChannel socketChannel=serverSocketChannel.accept();
		if(socketChannel==null)return;
		System.out.println("接收到客户连接，来自："+socketChannel.socket().getInetAddress()+":"+socketChannel.socket().getPort());
		
		ChannelIO cio=new ChannelIO(socketChannel,false/*非阻塞模式*/);
		RequestHandler rh=new RequestHandler(cio);
		//注册读就绪事件，把RequestHandler作为附件
		//当这种事件发生时，将由RequestHandler处理该事件
		socketChannel.register(key.selector(), SelectionKey.OP_READ, rh);
		
	}

}

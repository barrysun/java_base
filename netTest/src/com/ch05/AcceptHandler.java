package com.ch05;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;

public class AcceptHandler implements Handler {

	@Override
	public void handle(SelectionKey key) throws IOException {
		ServerSocketChannel serverSocketChannel=(ServerSocketChannel)key.channel();
		//在非阻塞模式下，serverSocketChannel.accept()有可能返回null
		
		
	}

}

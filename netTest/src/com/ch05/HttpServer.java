package com.ch05;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class HttpServer {

	private Selector selector = null;
	private ServerSocketChannel serverSocketChannel = null;
	private int port = 8080;
	private Charset charset = Charset.forName("GBK");

	public HttpServer() throws IOException {
		// 创建Selector 和 ServerSocketChannel
		// 把ServerSocketChannel设置为非阻塞模式，绑定80 端口
		selector=Selector.open();
		serverSocketChannel=ServerSocketChannel.open();
		serverSocketChannel.socket().setReuseAddress(true);
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		System.out.println("Server start ....");
		

	}

	public void service() throws IOException {
		// 注册接收连接就绪事件
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, new AcceptHandler());

		for (;;) {
			int n = selector.select();
			if (n == 0)
				continue;
			Set<?> readyKeys = selector.selectedKeys();
			Iterator it = readyKeys.iterator();
			while (it.hasNext()) {
				SelectionKey key = null;
				try {
					it.remove();
					final Handler handler = (Handler) key.attachment();
					handler.handle(key); //由Handler处理相关事件
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}

	public static void main(String[] args) throws Exception {
		final HttpServer server = new HttpServer();
		server.service();
	}

}

package com.ch05;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class HttpServer {

	private Selector selector = null;
	private ServerSocketChannel serverSocketChannel = null;
	private int port = 80;
	private Charset charset = Charset.forName("GBK");

	public HttpServer() throws IOException {
		// ����Selector �� ServerSocketChannel
		// ��ServerSocketChannel����Ϊ������ģʽ����80 �˿�

	}

	public void service() throws IOException {
		// ע��������Ӿ����¼�
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
					handler.handle(key); //��Handler��������¼�
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

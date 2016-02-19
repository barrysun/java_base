package com.ch05;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class AcceptHandler implements Handler {

	@Override
	public void handle(SelectionKey key) throws IOException {
		ServerSocketChannel serverSocketChannel=(ServerSocketChannel)key.channel();
		//�ڷ�����ģʽ�£�serverSocketChannel.accept()�п��ܷ���null
		//�ж�socketChannel�Ƿ�Ϊnull,����ʹ������ӽ�׳������NullPointerException
		SocketChannel socketChannel=serverSocketChannel.accept();
		if(socketChannel==null)return;
		System.out.println("���յ��ͻ����ӣ����ԣ�"+socketChannel.socket().getInetAddress()+":"+socketChannel.socket().getPort());
		
		ChannelIO cio=new ChannelIO(socketChannel,false/*������ģʽ*/);
		RequestHandler rh=new RequestHandler(cio);
		//ע��������¼�����RequestHandler��Ϊ����
		//�������¼�����ʱ������RequestHandler������¼�
		socketChannel.register(key.selector(), SelectionKey.OP_READ, rh);
		
	}

}

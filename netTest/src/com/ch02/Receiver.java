package com.ch02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * Sender���Receiver���stopWay��Ա��������ָ������ͨ�ŵķ�ʽ
 * stopWay ����
 * 
 * 1.��Ȼ����Sender��Receiver��ͨ��
 * 
 * 2.��ǰ��ֹReceiver
 * 
 * 3.ͻȻ��ֹ Sender
 * 
 * 
 * 
 * @author apple
 *
 */


public class Receiver {
	
	private int port=8000;
	private ServerSocket serverSocket; 
	private static int stopWay=1; //����ͨ�ŵķ�ʽ
	private final int NATURAL_STOP=1;//��Ȼ����
	private final int SUDDEN_STOP=2;// ͻȻ��ֹ����
	private final int SOCKET_STOP=3;//�ر�Socket���ٽ�������
	private final int INPUT_STOP=4;//�ر����������ٽ�������
	private final int SERVERSOCKET_STOP=5;//�ر�ServerSocket���ٽ�������
	
	public Receiver() throws IOException{
		serverSocket = new ServerSocket(port);
		System.out.println("�������Ѿ�����");
	}
	
	private BufferedReader getReader(Socket socket) throws IOException{
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn));
	}
	
	public void receive() throws Exception{
		
	}
	

}

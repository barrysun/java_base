package com.ch02;

import java.io.IOException;
import java.net.Socket;

public class Sender {
	
	private String host="localhost";
	private int port=8000;
	
	private Socket socket;
	
	private static int stopWay=1;//����ͨ�ŵķ�ʽ
	private final int NATURAL_STOP=1;//��Ȼ����
	private final int SUDDEN_STOP=2;//ͻȻ��ֹ����
	private final int SOCKET_STOP=3;//�ر�Socket���ٽ�������
	private final int OUTPUT_STOP=4;//�ر���������ٽ�������
	
	public Sender() throws IOException{
		socket=new Socket(host,port);
	}
	
	

}

package com.ch02;

import java.io.IOException;
import java.net.Socket;

public class Sender {
	
	private String host="localhost";
	private int port=8000;
	
	private Socket socket;
	
	private static int stopWay=1;//结束通信的方式
	private final int NATURAL_STOP=1;//自然结束
	private final int SUDDEN_STOP=2;//突然终止程序
	private final int SOCKET_STOP=3;//关闭Socket，再结束程序
	private final int OUTPUT_STOP=4;//关闭输出流，再结束程序
	
	public Sender() throws IOException{
		socket=new Socket(host,port);
	}
	
	

}

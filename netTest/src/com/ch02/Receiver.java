package com.ch02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * Sender类和Receiver类的stopWay成员变量用来指定结束通信的方式
 * stopWay 变量
 * 
 * 1.自然结束Sender和Receiver的通信
 * 
 * 2.提前终止Receiver
 * 
 * 3.突然终止 Sender
 * 
 * 
 * 
 * @author apple
 *
 */


public class Receiver {
	
	private int port=8000;
	private ServerSocket serverSocket; 
	private static int stopWay=1; //结束通信的方式
	private final int NATURAL_STOP=1;//自然结束
	private final int SUDDEN_STOP=2;// 突然终止程序
	private final int SOCKET_STOP=3;//关闭Socket，再结束程序
	private final int INPUT_STOP=4;//关闭输入流，再结束程序
	private final int SERVERSOCKET_STOP=5;//关闭ServerSocket，再结束程序
	
	public Receiver() throws IOException{
		serverSocket = new ServerSocket(port);
		System.out.println("服务器已经启动");
	}
	
	private BufferedReader getReader(Socket socket) throws IOException{
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn));
	}
	
	public void receive() throws Exception{
		
	}
	

}

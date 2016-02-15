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
 * 设置Socket的选项
 * 
 * TCP_NODELAY:表示立即发送数据
 * SO_RESUSEADDR:表示是否允许重用Socket所绑定的本地地址
 * SO_TIMEOUT:表示接收数据时的等待超时时间
 * SO_LINGER:表示当执行Socket的close()方法时，是否立即关闭底层的Socket。
 * SO_SNFBUF: 表示发送数据的缓冲区的大小。
 * SO_RCVBUF:表示接收数据的缓冲区的大小。
 * SO_KEEPALIVE:表示对于长时间处理空闲状态的Socket，是否要自动把它关闭。
 * OOBINLINE:表示是否支持发送一个字节的TCP紧急数据。
 * 
 * 
 * 
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
		Socket socket=null;
		socket=serverSocket.accept();
		BufferedReader br=getReader(socket);
		
		for(int i=0;i<20;i++){
			String msg=br.readLine();
			System.out.println("receive:"+msg);
			Thread.sleep(1000);
			if(i==2){
				if(stopWay==SUDDEN_STOP){
					System.out.println("突然终止程序");
					System.exit(0);
				}else if(stopWay==SOCKET_STOP){
					System.out.println("关闭Socket并终止程序");
					socket.close();
					break;
				}else if(stopWay==INPUT_STOP){
					System.out.println("关闭输入流并终止程序");
					socket.shutdownInput();
					break;
				}else if(stopWay==SERVERSOCKET_STOP){
					System.out.println("关闭ServerSocket并终止程序");
					serverSocket.close();
					break;
				}
			}
		}
		
		if(stopWay==NATURAL_STOP){
			socket.close();
			serverSocket.close();
		}
	}
	
	public static void main(String[] args) throws Exception{
		if(args.length>0) stopWay=Integer.parseInt(args[0]);
		new Receiver().receive();
	}
	

}

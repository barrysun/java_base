package com.ch02;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
/**
 * 
 *  获取Socket的信息
 *  
 *  在一个Socket对象中同时包含了远程服务器的IP地址和
 *  端口信息，以及客户本地的IP地址和端口信息。此外，从Socket
 *  对象中还可以获得输出流和输入流，分别用于向服务器发送数据，以及接收从服务器发
 *  来的数据。以下方法用于获取Socket的有关信息。
 *  
 *  getInetAddress()：获得远程服务器的IP地址
 *  getPort():获得远程服务器的端口
 *  getLocalAddress():获得本地的IP地址
 *  getLocalPort():获得客户本地端口
 *  getInputStream():获得输入流。如果Socket还没有连接，或者已经关闭，或者已经通过shutdownInput() 方法
 *  关闭输入流，那么此方法会抛出IOException。
 *  getOutputStream():获得输出流。如果Socket还没有连接，或者已经关闭，或者已经通过shutdownOutput()方法关闭输出流，
 *  那么此方法会抛出IOException.
 *  
 * 
 * */

public class HTTPClient {
	
	String host="www.javathinker.org";
	
	int port=80;
	Socket socket;
	
	public void createSocket() throws Exception{
		socket=new Socket(host,80);
	}
	
	public void communicate() throws Exception{

		StringBuilder sb=new StringBuilder("GET "+"/index.jsp"+" HTTP/1.1\r\n");
		sb.append("Host:www.javathinker.org\r\n");
		sb.append("Accept:*/*\r\n");
		sb.append("Accept-Language:zh-cn\r\n");
		sb.append("User-Agent:Mozilla/4.0(compatible;MSIE 6.0;Windows NT 5.0)\r\n");
		sb.append("Connection:Keep-Alive\r\n\r\n");
		
		//发出HTTP请求
		OutputStream socketOut= socket.getOutputStream();
		socketOut.write(sb.toString().getBytes());
		socket.shutdownOutput();
		//接收响应结果
//		InputStream socketIn=socket.getInputStream();
//		ByteArrayOutputStream buffer=new ByteArrayOutputStream();
//		byte[] buff=new byte[1024];
//		int len=-1;
//		while((len=socketIn.read(buff))!=-1){
//			buffer.write(buff,0,len);
//		}
//		System.out.println(new String(buffer.toByteArray()));
		
		InputStream socketIn=socket.getInputStream();
		BufferedReader br=new BufferedReader(new InputStreamReader(socketIn));
		String data;
		while((data=br.readLine())!=null){
			System.out.println(data);
		}
		socket.close();
		

		
	}
	
	public static void main(String[] args) throws Exception{
		
		HTTPClient client=new HTTPClient();
		client.createSocket();
		client.communicate();
		
	}

}

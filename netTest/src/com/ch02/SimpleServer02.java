package com.ch02;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer02 {
	
	
	public static void main(String[] args) throws Exception{
		ServerSocket serverSocket=new ServerSocket(8000);
		Socket s=serverSocket.accept();
		Thread.sleep(5000);
		InputStream in=s.getInputStream();
		ByteArrayOutputStream buffer=new ByteArrayOutputStream();
		byte[] buff=new byte[1024];
		int len=-1;
		do{
			len=in.read(buff);
			if(len!=-1){
				buffer.write(buff,0,len);
			}
		}while(len!=-1);
		System.out.println(new String(buffer.toByteArray()));
	}

}

package com.ch02;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ReceiveServer {
	
	public static void main(String[] args) throws Exception{
		ServerSocket serverSocket=new ServerSocket(8000);
		Socket s=serverSocket.accept();
		
		InputStream in=s.getInputStream();
		ByteArrayOutputStream buffer=new ByteArrayOutputStream();
		byte[] buff=new byte[1024];
		
	    int len=-1;
	    do{
	    	try{
	    		len=in.read(buff);
	    		if(len!=-1)buffer.write(buff,0,len);
	    	}catch(Exception e){
	    		System.out.println("等待读超时");
	    		len=0;
	    	}
	    	
	    }while(len!=-1);
	    System.out.println(new String(buffer.toByteArray()));//把字节数组转为字符串
	    
	}

}

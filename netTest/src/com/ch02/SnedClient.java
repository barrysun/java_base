package com.ch02;

import java.io.OutputStream;
import java.net.Socket;


public class SnedClient {
	
	public static void main(String[] args) throws Exception{
		Socket s=new Socket("localhost",8000);
		OutputStream out=s.getOutputStream();
		out.write("hello".getBytes());
		out.write("everyone".getBytes());;
		Thread.sleep(60000);
		s.close();
	}

}

package com.ch02;

import java.io.IOException;
import java.net.ServerSocket;

public class SimpleServer {
	
	public static void main(String[] args) throws IOException, InterruptedException{
		ServerSocket serverSocket=new ServerSocket(8000,2);
		Thread.sleep(360000);
	}

}

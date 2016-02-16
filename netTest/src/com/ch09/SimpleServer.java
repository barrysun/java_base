package com.ch09;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * 
 * ·þÎñÆ÷³ÌÐò
 * @author apple
 *
 */
public class SimpleServer {
	
	public void send(Object object) throws IOException{
		ServerSocket serverSocket=new ServerSocket(8000);
		while(true){
			Socket socket=serverSocket.accept();
			OutputStream out=socket.getOutputStream();
			
		}
	}

}

class Customer implements Serializable{
	
	private transient String fullName;
}

package com.ch02;

import java.io.IOException;
import java.net.Socket;

public class PortScanner {
	
	public static void main(String[] args){
		String host="127.0.0.1";
		if(args.length>0){
			host=args[0];
		}
			new PortScanner().scan(host);
		
	}
	
	public void scan(String host){
		Socket socket=null;
		for(int port=1;port<1024;port++){
			try{
				socket=new Socket(host,port);
				System.out.println("There is a server on port "+port);
			}catch(IOException e){
				System.out.println("Can't connect to port "+port);
			}finally{
				try{
					if(socket!=null) socket.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

}

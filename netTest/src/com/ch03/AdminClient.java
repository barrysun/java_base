package com.ch03;

import java.net.Socket;

public class AdminClient {
	
	public static void main(String[] args){
		Socket socket=null;
		try{
			socket=new Socket("localhost",8001);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(socket!=null){
					socket.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}

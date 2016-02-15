package com.ch02;

import java.io.IOException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ConnectTester {
	
	
	public static void main(String args[]){
		String host="localhost";
		int port=25;
		if(args.length>1){
			host=args[0];
			port=Integer.parseInt(args[1]);
		}
		
		new ConnectTester().connect(host, port);
	}
	
	public void connect(String host,int port){
		SocketAddress remoteAddr=new InetSocketAddress(host,port);
		
		Socket socket=null;
		String result="";
		try{
			long begin=System.currentTimeMillis();
			socket=new Socket();
			
			socket.connect(remoteAddr, 1000);
			long end=System.currentTimeMillis();
			result=(end-begin)+"ms";
		}catch(BindException e){
			result="Local address and port can't be binded";
		}catch(UnknownHostException e){
			result="";
		}catch(ConnectException e){
			result="Connection Refused";
		}catch(SocketTimeoutException e){
			result="TimeOut";
		}catch(IOException e){
			result="failure";
		}finally{
			try{
				if(socket!=null) socket.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		System.out.println(remoteAddr+":"+result);
	}

}

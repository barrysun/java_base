package com.ch09;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
/**
 * 
 * 服务器程序
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
	
	
	public static void main(String[] args) throws Exception{
		Object object=null;
		//接收用户从命令行指定的类名，然后创建该类的对象
		if(args.length>0&& args[0].equals("Date")){
			object=new Date();
		}else if(args.length>0 && args[0].equals("Customer1")){
			object=new Customer1("Tom",123);
		}
	}

}

class Customer implements Serializable{
	
	private transient String fullName;
}

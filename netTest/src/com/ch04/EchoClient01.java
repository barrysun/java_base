package com.ch04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

/**
 * 阻塞模式
 * @author apple
 *
 */

public class EchoClient01 {

	private SocketChannel socketChannel=null;
	
	public EchoClient01() throws IOException{
		socketChannel=SocketChannel.open();
		InetAddress ia=InetAddress.getLocalHost();
		InetSocketAddress isa=new InetSocketAddress(ia,8000);
		socketChannel.connect(isa);//连接服务器
		System.out.println("与服务器的连接建立成功");
	}
	
	public static void main(String[] args) throws IOException{
		new EchoClient01().talk();
	}
	
	private PrintWriter getWriter(Socket socket) throws IOException{
		OutputStream socketOut=socket.getOutputStream();
		return new PrintWriter(socketOut,true);
	}
	
	private BufferedReader getReader(Socket socket)throws IOException{
		InputStream socketIn=socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn));
	}
	
	
	public void talk() throws IOException{
		
		try{
			BufferedReader br=getReader(socketChannel.socket());
			PrintWriter pw=getWriter(socketChannel.socket());
			BufferedReader localReader=new BufferedReader(new InputStreamReader(System.in));
			String msg=null;
			while((msg=localReader.readLine())!=null){
				pw.println(msg);
				System.out.println(br.readLine());
				if(msg.equals("bye")){
					break;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				socketChannel.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	
}

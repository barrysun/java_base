package com.ch08;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class EchoClient {
	
	private String remoteHost="localhost";
	private int remotePort=8000;
	private DatagramSocket socket;
	
	public EchoClient() throws IOException{
		socket=new DatagramSocket();//与本地的任意一个UDP端口绑定	
	}
	
	public static void main(String[] args) throws IOException{
		new EchoClient().talk();
	}
	
	public void talk() throws IOException{
		
		try{
			
			InetAddress remoteIP=InetAddress.getByName(remoteHost);
			BufferedReader localReader=new BufferedReader(new InputStreamReader(System.in));
			
			String msg=null;
			while((msg=localReader.readLine())!=null){
				byte[] outputData=msg.getBytes();
				DatagramPacket outputPacket=new DatagramPacket(outputData,outputData.length,remoteIP,remotePort);
				
				socket.send(outputPacket);//给EchoServer发送数据报
				DatagramPacket inputPacket=new DatagramPacket(new byte[512],512);
				socket.receive(inputPacket);//接收EchoServer的数据报
				System.out.println(new String(inputPacket.getData(),0,inputPacket.getLength()));
				
				if(msg.equals("bye")){
					break;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			socket.close();
		}
		
	}
	

}

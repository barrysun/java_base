package com.ch08;

import java.io.IOException;
import java.net.DatagramSocket;

public class DatagramTester {
	
	private int port=8000;
	private DatagramSocket sendSocket;
	private DatagramSocket receiveSocket;
	private static final int MAX_LENGTH=3584;
	
	
	public DatagramTester() throws IOException{
		
		sendSocket=new DatagramSocket();
		receiveSocket=new DatagramSocket(port);
		receiver.start();
		sender.start();
	}
	
	/**
	 * 把long数组转换为byte数组
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static byte[] longToByte(long[] data)throws IOException{
		return null;
	}
	/**
	 * 
	 * 把byte数组转换为long数组
	 * 
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static long[] byteToLong(byte[] data)throws IOException{
		return null;
	}
	
	public void send(byte[] bigData)throws IOException{
		
		
	}
	
	public byte[] receive() throws IOException{
		return null;
	}
	
	public Thread sender=new Thread(){
		
	};
	
	public Thread receiver=new Thread(){
		
	};
	
	public static void main(String[] args) throws IOException{
		DatagramTester tester=new DatagramTester();
	}
	
	

}

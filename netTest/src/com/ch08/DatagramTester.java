package com.ch08;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

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
		DatagramPacket packet=new DatagramPacket(bigData,0,512,InetAddress.getByName("localhost"),port);
		int bytesSent=0;//表示已经发送的字节数
		int count=0;//表示发送的次数
		while(bytesSent<bigData.length){
			sendSocket.send(packet);
			System.out.println("");
			bytesSent+=packet.getLength();//getLength()方法返回实际发送的字节数
			int remain=bigData.length-bytesSent;//计算剩余的未发送的字节数
			int length=(remain>512)?512:remain;//计算下次发送的数据的长度
			packet.setData(bigData,bytesSent,length);
			//改变DatagramPacket的offset和length属性
		}
	}
	
	
	public byte[] receive() throws IOException{
		byte[] bigData=new byte[MAX_LENGTH];
		DatagramPacket packet=new DatagramPacket(bigData,0,MAX_LENGTH);
		int bytesReceived=0;//表示已经接收的字节数
		int count=0;//表示接收的次数
		long beginTime=System.currentTimeMillis();
		//如果接收到了bigData.length个字节，或者超过了5分钟，就结束循环
		while((bytesReceived<bigData.length) && (System.currentTimeMillis()-beginTime<60000*5)){
			receiveSocket.receive(packet);
			System.out.println("");
			bytesReceived+=packet.getLength();//getLength()方法返回实际发送的字节数
			//改变DatagramPacket的offset和length属性
			packet.setData(bigData,bytesReceived,MAX_LENGTH-bytesReceived);
			
		}
		
		return packet.getData();
	}
	
	public Thread sender=new Thread(){
		//发送者线程
		public void run(){
			long[] longArray=new long[MAX_LENGTH/8];
			for(int i=0;i<longArray.length;i++){
				longArray[i]=i+1;
			}
			try{
				send(longToByte(longArray));//发送long型数组中的数据
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	};
	
	public Thread receiver=new Thread(){
		//接收者线程
		public void run(){
			try{
				long[] longArray=byteToLong(receive());//接收数据
				//打印接收到的数据
				for(int i=0;i<longArray.length;i++){
					if(i%100==0)System.out.println();
					System.out.print(longArray[i]+" ");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	};
	
	public static void main(String[] args) throws IOException{
		DatagramTester tester=new DatagramTester();
	}
	
	

}

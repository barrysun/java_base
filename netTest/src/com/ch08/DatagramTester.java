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
	 * ��long����ת��Ϊbyte����
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
	 * ��byte����ת��Ϊlong����
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
		int bytesSent=0;//��ʾ�Ѿ����͵��ֽ���
		int count=0;//��ʾ���͵Ĵ���
		while(bytesSent<bigData.length){
			sendSocket.send(packet);
			System.out.println("");
			bytesSent+=packet.getLength();//getLength()��������ʵ�ʷ��͵��ֽ���
			int remain=bigData.length-bytesSent;//����ʣ���δ���͵��ֽ���
			int length=(remain>512)?512:remain;//�����´η��͵����ݵĳ���
			packet.setData(bigData,bytesSent,length);
			//�ı�DatagramPacket��offset��length����
		}
	}
	
	
	public byte[] receive() throws IOException{
		byte[] bigData=new byte[MAX_LENGTH];
		DatagramPacket packet=new DatagramPacket(bigData,0,MAX_LENGTH);
		int bytesReceived=0;//��ʾ�Ѿ����յ��ֽ���
		int count=0;//��ʾ���յĴ���
		long beginTime=System.currentTimeMillis();
		//������յ���bigData.length���ֽڣ����߳�����5���ӣ��ͽ���ѭ��
		while((bytesReceived<bigData.length) && (System.currentTimeMillis()-beginTime<60000*5)){
			receiveSocket.receive(packet);
			System.out.println("");
			bytesReceived+=packet.getLength();//getLength()��������ʵ�ʷ��͵��ֽ���
			//�ı�DatagramPacket��offset��length����
			packet.setData(bigData,bytesReceived,MAX_LENGTH-bytesReceived);
			
		}
		
		return packet.getData();
	}
	
	public Thread sender=new Thread(){
		//�������߳�
		public void run(){
			long[] longArray=new long[MAX_LENGTH/8];
			for(int i=0;i<longArray.length;i++){
				longArray[i]=i+1;
			}
			try{
				send(longToByte(longArray));//����long�������е�����
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	};
	
	public Thread receiver=new Thread(){
		//�������߳�
		public void run(){
			try{
				long[] longArray=byteToLong(receive());//��������
				//��ӡ���յ�������
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

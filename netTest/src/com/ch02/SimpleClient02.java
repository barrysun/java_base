package com.ch02;

import java.io.OutputStream;
import java.net.Socket;

public class SimpleClient02 {
	
	
	public static void main(String[] args) throws Exception{
		Socket s=new Socket("localhost",8000);
		
		OutputStream out=s.getOutputStream();
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<10000;i++){
			sb.append(i);
		}
		
		out.write(sb.toString().getBytes());
		System.out.println("��ʼ�ر�Socket");
		long begin=System.currentTimeMillis();
		s.close();
		long end=System.currentTimeMillis();
		System.out.println("�ر�Socket���õ�ʱ��Ϊ��"+(end-begin)+"ms");
	}

}

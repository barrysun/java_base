package com.ch02;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
/**
 * 
 *  ��ȡSocket����Ϣ
 *  
 *  ��һ��Socket������ͬʱ������Զ�̷�������IP��ַ��
 *  �˿���Ϣ���Լ��ͻ����ص�IP��ַ�Ͷ˿���Ϣ�����⣬��Socket
 *  �����л����Ի������������������ֱ�������������������ݣ��Լ����մӷ�������
 *  �������ݡ����·������ڻ�ȡSocket���й���Ϣ��
 *  
 *  getInetAddress()�����Զ�̷�������IP��ַ
 *  getPort():���Զ�̷������Ķ˿�
 *  getLocalAddress():��ñ��ص�IP��ַ
 *  getLocalPort():��ÿͻ����ض˿�
 *  getInputStream():��������������Socket��û�����ӣ������Ѿ��رգ������Ѿ�ͨ��shutdownInput() ����
 *  �ر�����������ô�˷������׳�IOException��
 *  getOutputStream():�������������Socket��û�����ӣ������Ѿ��رգ������Ѿ�ͨ��shutdownOutput()�����ر��������
 *  ��ô�˷������׳�IOException.
 *  
 * 
 * */

public class HTTPClient {
	
	String host="www.javathinker.org";
	
	int port=80;
	Socket socket;
	
	public void createSocket() throws Exception{
		socket=new Socket(host,80);
	}
	
	public void communicate() throws Exception{

		StringBuilder sb=new StringBuilder("GET "+"/index.jsp"+" HTTP/1.1\r\n");
		sb.append("Host:www.javathinker.org\r\n");
		sb.append("Accept:*/*\r\n");
		sb.append("Accept-Language:zh-cn\r\n");
		sb.append("User-Agent:Mozilla/4.0(compatible;MSIE 6.0;Windows NT 5.0)\r\n");
		sb.append("Connection:Keep-Alive\r\n\r\n");
		
		//����HTTP����
		OutputStream socketOut= socket.getOutputStream();
		socketOut.write(sb.toString().getBytes());
		socket.shutdownOutput();
		//������Ӧ���
//		InputStream socketIn=socket.getInputStream();
//		ByteArrayOutputStream buffer=new ByteArrayOutputStream();
//		byte[] buff=new byte[1024];
//		int len=-1;
//		while((len=socketIn.read(buff))!=-1){
//			buffer.write(buff,0,len);
//		}
//		System.out.println(new String(buffer.toByteArray()));
		
		InputStream socketIn=socket.getInputStream();
		BufferedReader br=new BufferedReader(new InputStreamReader(socketIn));
		String data;
		while((data=br.readLine())!=null){
			System.out.println(data);
		}
		socket.close();
		

		
	}
	
	public static void main(String[] args) throws Exception{
		
		HTTPClient client=new HTTPClient();
		client.createSocket();
		client.communicate();
		
	}

}

package com.ch02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Base64;
import java.util.Base64.Encoder;

/**
 * ��ЩSMTP����������Ҫ��ͻ��ṩ�����֤��Ϣ������������£��ͻ�Ӧ���ȷ��͡�EHLO�����
 * ���ŷ��͡�AUTH LOGIN������ٷ��Ͳ���Base64������û����Ϳ����������ͨ���������˵������֤��
 * 
 * 
 * 
 * @author apple
 *
 */
public class MailSenderWithAuth {
	
	private static String smtpServer="smtp.citiz.net";
	private static int port=25;
	
	
	public static  void main(String[] args) throws Exception{
		
		Message msg=new Message("java_mail@citiz.net",
				"java_mail@citiz.net","hello","hi,I miss you very much.");
		
		new MailSenderWithAuth().sendMain(msg);
		
	}
	
	public void sendMain(Message msg){
		Socket socket=null;
		try{
			socket=new Socket(smtpServer,port);
			BufferedReader br=getReader(socket);
			PrintWriter pw=getWriter(socket);
			String localhost=InetAddress.getLocalHost().getHostName();
			
			String username="java_mail";
			String password="123456";
			//���û����Ϳ������Base64����
			
			username="";
			password="";
			
	
			sendAndReceive(null,br,pw);
			sendAndReceive("EHLO "+localhost,br,pw); 
			sendAndReceive("AUTH LOGIN",br,pw);
			sendAndReceive(username,br,pw);
			sendAndReceive(password,br,pw);
			sendAndReceive("MAIL FROM:"+msg.from+"",br,pw);
			
			
			sendAndReceive("MAIL TO:"+msg.to+"",br,pw);
			sendAndReceive("DATA",br,pw);
			pw.println(msg.data);
			System.out.println("Client>"+msg.data);
			sendAndReceive(".",br,pw);//�ʼ��������
			sendAndReceive("QUIT",br,pw);//����ͨ��
			
			
					
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(socket!=null)socket.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	/**����һ���ַ�����������һ�з���������Ӧ����
	 * @throws IOException **/
	private void sendAndReceive(String str,BufferedReader br,PrintWriter pw) throws IOException{
		if(str!=null){
			System.out.println("Client>"+str);
			pw.println(str);
		}
		String response;
		if((response=br.readLine())!=null){
			System.out.println("Server>"+response);
		}
	}
	
	private PrintWriter getWriter(Socket socket) throws IOException{
		OutputStream socketOut=socket.getOutputStream();
		return new PrintWriter(socketOut,true);
	}
	
	private BufferedReader getReader(Socket socket) throws IOException{
		InputStream socketIn=socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn));
	}

}

package com.ch02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * �����ʼ���SMTP�ͻ�����
 * 
 * SMTPЭ�� �����ʼ�����Э�飩��Ӧ�ò��Э�飬������TCP/IPЭ��
 * ����֮�ϡ�SMTPЭ��涨�˰��ʼ��ӷ��ͷ����䵽���շ��Ĺ���
 * 
 * SMTP�ͻ������������ʼ���SMTP�ͻ���SMTP��������һ�λỰ�����У�
 * SMTP�ͻ��ᷢ��һϵ��SMTP���
 * 
 * 
 * @author apple
 *
 */
public class MailSender {
	
	private String smtpServer="smtp.mydomain.com";//SMTP�ʼ�����������������
	private int port=25;
	public static void main(String[] args){
		Message msg=new Message("tom@abc.com", //�����ߵ��ʼ���ַ
				"linda@def.com",//�����ߵ��ʼ���ַ
				"hello",//�ʼ�����
				"hi,i miss you very much.");//�ʼ�����
		
	}
	
	
	
	public void sendMail(Message msg){
		Socket socket=null;
		try{
			socket=new Socket(smtpServer,port);
			BufferedReader br=getReader(socket);
			PrintWriter pw=getWriter(socket);
			String localhost=InetAddress.getLocalHost().getHostName();
			
			sendAndReceive(null,br,pw);
			sendAndReceive("HELO "+localhost,br,pw);
			sendAndReceive("MAIL FROM:<"+msg.from+">",br,pw);
			sendAndReceive("RCPT TO:<"+msg.to+">",br,pw);
			sendAndReceive("DATA",br,pw);
			pw.println(msg.data);
			System.out.println("Client>"+msg.data);
			sendAndReceive(".",br,pw);
			sendAndReceive("QUIT",br,pw);
			
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				if(socket!=null)socket.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/*����һ���ַ�����������һ�з���������Ӧ����*/
	public void sendAndReceive(String str,BufferedReader br,PrintWriter pw) throws IOException{
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




class Message{
	String from;
	String to;
	String subject;
	String content;
	String data;
	public Message(String from,String to,String subject,String content){
		this.from=from;
		this.to=to;
		this.subject=subject;
		this.content=content;
		data="Subject:"+subject+"\r\n"+content;
	}
}

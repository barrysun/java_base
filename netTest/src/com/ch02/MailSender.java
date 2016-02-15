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
 * 发送邮件的SMTP客户程序
 * 
 * SMTP协议 （简单邮件传输协议）是应用层的协议，建立在TCP/IP协议
 * 基础之上。SMTP协议规定了吧邮件从发送方传输到接收方的规则。
 * 
 * SMTP客户程序请求发送邮件，SMTP客户与SMTP服务器的一次会话过程中，
 * SMTP客户会发送一系列SMTP命令，
 * 
 * 
 * @author apple
 *
 */
public class MailSender {
	
	private String smtpServer="smtp.mydomain.com";//SMTP邮件服务器的主机名称
	private int port=25;
	public static void main(String[] args){
		Message msg=new Message("tom@abc.com", //发送者的邮件地址
				"linda@def.com",//接受者的邮件地址
				"hello",//邮件标题
				"hi,i miss you very much.");//邮件正文
		
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
	
	/*发送一行字符串，并接收一行服务器的响应数据*/
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

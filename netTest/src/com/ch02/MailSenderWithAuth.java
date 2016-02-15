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
 * 有些SMTP服务器还会要求客户提供身份认证信息。在这种情况下，客户应该先发送“EHLO”命令，
 * 接着发送“AUTH LOGIN”命令，再发送采用Base64编码的用户名和口令，这样就能通过服务器端的身份认证。
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
			//对用户名和口令进行Base64编码
			
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
			sendAndReceive(".",br,pw);//邮件发送完毕
			sendAndReceive("QUIT",br,pw);//结束通信
			
			
					
			
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
	/**发送一行字符串，并接收一行服务器的响应数据
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

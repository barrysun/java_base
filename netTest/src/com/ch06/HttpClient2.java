package com.ch06;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class HttpClient2 {
	
	public static void main(String args[]) throws IOException{
		URL url=new URL("http://www.javathinker.org/hello.htm");
		URLConnection connection=url.openConnection();
		//接收响应结果
		System.out.println("正文类型："+connection.getContentType());
		System.out.println("正文长度："+connection.getContentLength());
		
		InputStream in=connection.getInputStream();//读取响应正文
		
		ByteArrayOutputStream buffer=new ByteArrayOutputStream();
		byte[] buff=new byte[1024];
		int len=-1;
		
		while((len=in.read(buff))!=-1){
			buffer.write(buff,0,len);
		}
		System.out.println(new String(buffer.toByteArray()));
	}

}

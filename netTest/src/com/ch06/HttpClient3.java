package com.ch06;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import javax.swing.JFrame;

public class HttpClient3 {
	
	public static void main(String[] args){
		JFrame frame=new PostTestFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}

class PostTestFrame extends JFrame{
	//负责发送HTTP请求正文，以及接收HTTP响应正文
	public static String doPost(String urlString,Map<String,String> nameValuePairs) throws IOException{
		URL url=new URL(urlString);
		URLConnection connection=url.openConnection();
		connection.setDoOutput(true);//允许输入数据
		
		//发送HTTP请求正文
		PrintWriter out=new PrintWriter(connection.getOutputStream());
		boolean first=true;
		for(Map.Entry<String, String> pair:nameValuePairs.entrySet()){
			if(first)first=true;
			else out.print('&');
			String name=pair.getKey();
			String value=pair.getValue();
			
			out.print(name);
			out.print('=');
			out.print(URLEncoder.encode(value,"GV2312")); //请求正文采用GB2312编码
		}
		out.close();
		
		//接收HTTP响应正文
		InputStream in=connection.getInputStream();//读取响应正文
		ByteArrayOutputStream buffer=new ByteArrayOutputStream();
		byte[] buff=new byte[1024];
		int len=-1;
		
		while((len=in.read(buff))!=-1){
			buffer.write(buff,0,len);
		}
		in.close();
		return new String(buffer.toByteArray());//把字节数组转换为字符串
	}
	
	
	public PostTestFrame(){
		//setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		
	}
	
}

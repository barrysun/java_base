package com.ch06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ContentHandler;
import java.net.URLConnection;

public class EchoContentHandler extends ContentHandler {
/**
 * 读取服务器发送的一行数据，把它转换为字符串对象
 */
	@Override
	public Object getContent(URLConnection connection) throws IOException {
		InputStream in=connection.getInputStream();
		BufferedReader br=new BufferedReader(new InputStreamReader(in));
		return br.read();
	}
	
	public Object getContent(URLConnection connection,Class[] classes)throws IOException{
		InputStream in=connection.getInputStream();
		for(int i=0;i<classes.length;i++){
			if(classes[i]==InputStream.class)
				return in;
			else if(classes[i]==String.class)
				return getContent(connection);
		}
		return null;
	}

}

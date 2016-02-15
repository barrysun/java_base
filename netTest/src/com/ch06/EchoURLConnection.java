package com.ch06;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

/**
 * 实现协议处理框架
 * 
 * EchoURLConnection类：继承自URLConnection类
 * EchoURLStreamHandler：继承自URLStreamHandler类
 * EchoURLStreamHandlerFactory：实现URLStreamHandlerFactory接口。
 * EchoContentHandler：继承自ContentHandler类
 * 
 * @author apple
 *
 */

public class EchoURLConnection extends URLConnection {

	
	private Socket connection = null;
	public final static int DEFAULT_PORT=8000;
	
	
	protected EchoURLConnection(URL url) {
		super(url);
		// TODO Auto-generated constructor stub
	}
	
	public synchronized InputStream getInputStream() throws IOException{
		if(!connected)connect();
		return connection.getInputStream();
	}
	
	
	public synchronized OutputStream getOutputStream() throws IOException{
		if(!connected)connect();
		return connection.getOutputStream();
	}

	@Override
	public void connect() throws IOException {
		if(!connected){
			int port=url.getPort();
			if(port<0||port>65535)port=DEFAULT_PORT;
			this.connection=new Socket(url.getHost(),port);
			this.connected=true;
		}
	}
	
	public synchronized void disconnect() throws IOException{
		if(connected){
			this.connection.close();
			this.connected=false;
		}
	}
	
	public String geContentType(){
		return "text/plain";
	}

}

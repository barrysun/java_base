package com.ch05;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 阻塞模式
 * @author apple
 *
 */
public class SimpleHttpServer {
	
	private int port=80;
	private ServerSocketChannel serverSocketChannel=null;
	private ExecutorService executorService;
	private static final int POOL_MULTIPLE=4;
	
	public SimpleHttpServer() throws IOException{
		executorService=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * POOL_MULTIPLE);
		serverSocketChannel=ServerSocketChannel.open();
		serverSocketChannel.socket().setReuseAddress(true);
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		System.out.println("服务器启动....");
	}
	
	public void service(){
		while(true){
			SocketChannel socketChannel=null;
			try{
				socketChannel=serverSocketChannel.accept();
				executorService.execute(new Handler(socketChannel));
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws IOException{
		new SimpleHttpServer().service();
	}
	
	class Handler implements Runnable{ //Handler是内部类，负责处理HTTP请求
		private SocketChannel socketChannel;
       public Handler(SocketChannel socketChannel){
			this.socketChannel=socketChannel;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			handle(socketChannel);
		}
		
		
		
		
		public void handle(SocketChannel socketChannel){
			try{
				Socket socket=socketChannel.socket();
				System.out.println("接收到客户连接，来自："+socket.getInetAddress()+":"+socket.getPort());
				
				ByteBuffer buffer=ByteBuffer.allocate(1024);
				socketChannel.read(buffer);//接收HTTP请求，假定其长度不超过1024个字节
				buffer.flip();
				String request=decode(buffer);
				System.out.println(request);
				
				
				//生成HTTP响应结果
				StringBuffer sb=new StringBuffer("HTTP/1.1 200 OK\r\n");
				sb.append("Content-Type:text/html\r\n\r\n");
				socketChannel.write(encode(sb.toString()));//发送HTTP响应的第一行和响应头
				
				FileInputStream in;
				//获取HTTP请求的第一行
				String firstLineOfRequest=request.substring(0, request.indexOf("\r\n"));
				if(firstLineOfRequest.indexOf("login.html")!=-1){
					in=new FileInputStream("log.html");
				}else{
					in=new FileInputStream("hello.html");
				}
				FileChannel fileChannel=in.getChannel();
				fileChannel.transferTo(0, fileChannel.size(), socketChannel);//发送响应正文
				
				
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try{
					if(socketChannel!=null)socketChannel.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		
		private Charset charset=Charset.forName("GBK");
		public String decode(ByteBuffer buffer){ //解码
		 CharBuffer charBuffer=charset.decode(buffer);
		 return charBuffer.toString();
		}
		public ByteBuffer encode(String str){//编码
			return charset.encode(str);
		}
		
		
		
	}

}

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
 * ����ģʽ
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
		System.out.println("����������....");
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
	
	class Handler implements Runnable{ //Handler���ڲ��࣬������HTTP����
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
				System.out.println("���յ��ͻ����ӣ����ԣ�"+socket.getInetAddress()+":"+socket.getPort());
				
				ByteBuffer buffer=ByteBuffer.allocate(1024);
				socketChannel.read(buffer);//����HTTP���󣬼ٶ��䳤�Ȳ�����1024���ֽ�
				buffer.flip();
				String request=decode(buffer);
				System.out.println(request);
				
				
				//����HTTP��Ӧ���
				StringBuffer sb=new StringBuffer("HTTP/1.1 200 OK\r\n");
				sb.append("Content-Type:text/html\r\n\r\n");
				socketChannel.write(encode(sb.toString()));//����HTTP��Ӧ�ĵ�һ�к���Ӧͷ
				
				FileInputStream in;
				//��ȡHTTP����ĵ�һ��
				String firstLineOfRequest=request.substring(0, request.indexOf("\r\n"));
				if(firstLineOfRequest.indexOf("login.html")!=-1){
					in=new FileInputStream("log.html");
				}else{
					in=new FileInputStream("hello.html");
				}
				FileChannel fileChannel=in.getChannel();
				fileChannel.transferTo(0, fileChannel.size(), socketChannel);//������Ӧ����
				
				
				
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
		public String decode(ByteBuffer buffer){ //����
		 CharBuffer charBuffer=charset.decode(buffer);
		 return charBuffer.toString();
		}
		public ByteBuffer encode(String str){//����
			return charset.encode(str);
		}
		
		
		
	}

}

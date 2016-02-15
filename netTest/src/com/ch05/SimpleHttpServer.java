package com.ch05;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
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
	
	class Handler implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
	}

}

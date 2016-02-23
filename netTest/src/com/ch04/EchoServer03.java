package com.ch04;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * 混合使用阻塞模式与非阻塞模式
 * @author apple
 *
 */

public class EchoServer03 {

	private Selector selector=null;
	private ServerSocketChannel serverSocketChannel=null;
	private int port=8000;
	private Charset charset=Charset.forName("GBK");
	
	public EchoServer03()throws IOException{
		selector=Selector.open();
		serverSocketChannel=ServerSocketChannel.open();
		serverSocketChannel.socket().setReuseAddress(true);
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		System.out.println("服务器启动");
	}
	
	public void accept(){
		for(;;){
			try{
				SocketChannel socketChannel=serverSocketChannel.accept();
				System.out.println("接收到客户连接，来自："+socketChannel.socket().getInetAddress()+":"+socketChannel.socket().getPort());
				socketChannel.configureBlocking(false);
				
				ByteBuffer buffer=ByteBuffer.allocate(1024);
				synchronized(gate){
					selector.wakeup();
					socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, buffer);
					
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
		
	}
	private Object gate=new Object();
	
	public void service() throws IOException{
		for(;;){
			synchronized(gate){}
			int n=selector.select();
			if(n==0)continue;
			
			Set<?> readyKeys=selector.selectedKeys();
			Iterator it=readyKeys.iterator();
			
			while(it.hasNext()){
				SelectionKey key=null;
				try{
					key=(SelectionKey)it.next();
					it.remove();
					if(key.isReadable()){
						receive(key);
					}
					
					if(key.isWritable()){
						send(key);
					}
				}catch(Exception e){
					e.printStackTrace();
					try{
						if(key!=null){
							key.cancel();
							key.channel().close();
						}
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
				
			}
		}
	}
	
	
	public void send(SelectionKey key)throws Exception{
		
	}
	
	public void receive(SelectionKey key)throws Exception{
		
	}
	
	public String decode(ByteBuffer buffer){
		return null;
	}
	
	public ByteBuffer encode(String str){
		return null;
	}
	
	
	public static void main(String[] args)throws Exception{
		final EchoServer03 server=new EchoServer03();
		Thread accept=new Thread(){
			public void run(){
				server.accept();
			}
		};
		accept.start();
		server.service();
	}
	
	
}

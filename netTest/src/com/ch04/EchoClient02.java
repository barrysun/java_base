package com.ch04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * 非阻塞模式
 * 
 * @author apple
 *
 */
public class EchoClient02 {
	
	private SocketChannel socketChannel=null;
	private ByteBuffer sendBuffer=ByteBuffer.allocate(1024);
	private ByteBuffer receiveBuffer=ByteBuffer.allocate(1024);
	
	private Charset charset=Charset.forName("GBK");
	private Selector selector;
	
	public EchoClient02()throws IOException{
		socketChannel=SocketChannel.open();
		InetAddress is=InetAddress.getLocalHost();
		InetSocketAddress isa=new InetSocketAddress(is,8000);
		socketChannel.connect(isa);//采用阻塞模式连接服务器
		socketChannel.configureBlocking(false);//设置为非阻塞模式
		System.out.println("与服务器的连接建立成功");
		selector=Selector.open();
		
	}
	
	public static void main(String args[]) throws IOException{
		final EchoClient02 client=new EchoClient02();
		Thread receiver=new Thread(){
			public void run(){
				client.receiveFromUser();
			}
		};
	}
	
	public void receiveFromUser(){//接收用户从控制台输入的数据，把它放到sendBuffer中
		try{
			BufferedReader localReader=new BufferedReader(new InputStreamReader(System.in));
			String msg=null;
			while((msg=localReader.readLine())!=null){
				synchronized(sendBuffer){
					sendBuffer.put(encode(msg+"\r\n"));
				}
				if(msg.equals("bye")){
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void talk() throws IOException{//接收和发送数据
		socketChannel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);
		while(selector.select()>0){
			Set<?> readyKeys=selector.selectedKeys();
			Iterator it=readyKeys.iterator();
			while(it.hasNext()){
				SelectionKey key=null;
				try{
					key=(SelectionKey) it.next();
					it.remove();
					
					if(key.isReadable()){
						receive(key);
					}
					
					if(key.isWritable()){
						send(key);
					}
				}catch(IOException e){
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
	
	public void send(SelectionKey key)throws IOException{
		//发送sendBuffer中的数据
		SocketChannel socketChannel=(SocketChannel)key.channel();
		synchronized(sendBuffer){
			sendBuffer.flip();//把极限设为位置，把位置设为0
			socketChannel.write(sendBuffer);//发送数据
			sendBuffer.compact();//删除已经发送的数据
		}
	}
	
	public void receive(SelectionKey key)throws IOException{
		//接收EchoServer发送的数据，把它放到receiveBuffer中
		//如果receiveBuffer中有一行数据，就打印这行数据，然后把它从receiveBuffer中删除
		SocketChannel socketChannel=(SocketChannel)key.channel();
		socketChannel.read(receiveBuffer);
		receiveBuffer.flip();
		String receiveData=decode(receiveBuffer);
		
		if(receiveData.indexOf("\n")==-1) return;
		
		String outputData=receiveData.substring(0, receiveData.indexOf("\n")+1);
		System.out.println(outputData);
		if(outputData.equals("echo:bye\r\n")){
			key.cancel();
			socketChannel.close();
			System.out.println("关闭与服务器的连接");
			selector.close();
			System.exit(0);//结束程序
			
		}
		
		ByteBuffer temp=encode(outputData);
		receiveBuffer.position(temp.limit());
		receiveBuffer.compact();//删除已经打印的数据
		
	}
	
	public String decode(ByteBuffer buffer){
		CharBuffer charBuffer=charset.decode(buffer);
		return charBuffer.toString();
	}
	
	public ByteBuffer encode(String str){
		return charset.encode(str);
	}

}

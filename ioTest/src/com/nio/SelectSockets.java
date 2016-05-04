package com.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 使用select() 来为多个通道提供服务
 * @author Administrator
 *
 */
public class SelectSockets {
	
	public static int PORT_NUMBER=1234;
	
	public static void main(String[] argv) throws Exception{
		
	}
	
	public void go(String[] argv) throws Exception{
		int port=PORT_NUMBER;
		if(argv.length>0){
			port=Integer.parseInt(argv[0]);
		}
		System.out.println("Listening on port "+port);
		//Allocate an unbound server socket channel
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		
	    ServerSocket serverSocket=serverChannel.socket();
		//Create a new Selector for use below
		Selector selector=Selector.open();
		
		//set the port the server cahnnel will listen to
		serverSocket.bind(new InetSocketAddress(port));
		//Set nonblocking mode for the listening socket
		serverChannel.configureBlocking(false);
		
		//Register the ServerSocketChannel with the Selector
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		while(true){
			//This may block for a long time.Upon returning , the
			//selected set contains keys of the ready channels
			int n=selector.select();
			if(n==0){
				continue;
			}
			Iterator it=selector.selectedKeys().iterator();
			//Look at each key in the selected set
			while(it.hasNext()){
				SelectionKey key=(SelectionKey)it.next();
				//Is a new connection coming in?
				if(key.isAcceptable()){
					ServerSocketChannel server=(ServerSocketChannel)key.channel();
					SocketChannel channel=server.accept();
					registerChannel(selector,channel,SelectionKey.OP_READ);
					sayHello(channel);
				}
				//Is there data to read on this channel?
				if(key.isReadable()){
					readDataFromSocket(key);
				}
				//Remove key from selected set; it's been handled
				it.remove();
			}
			
		}
	}
	
	protected void registerChannel(Selector selector,SelectableChannel channel,int ops) throws Exception{
		if(channel == null){
			return;
		}
		//set the new channel nonblocking
		channel.configureBlocking(false);
		//Register it with the selector
		channel.register(selector,ops);
	}
	
	private ByteBuffer buffer=ByteBuffer.allocateDirect(1024);
	
	/**
	 * 
	 */
	protected void readDataFromSocket(SelectionKey key) throws Exception{
		SocketChannel socketChannel=(SocketChannel) key.channel();
		int count;
		buffer.clear();//Empty buffer
		//Loop while data is available; channel is nonblocking
		while((count=socketChannel.read(buffer))>0){
			buffer.flip();//Make buffer readable
			//send the data; don't assume it goes all at once
			while(buffer.hasRemaining()){
				socketChannel.write(buffer);
			}
			//
			buffer.clear();//Empty buffer
		}
		if(count<0){
			socketChannel.close();
		}
	}
	/**
	 * Spew a greeting to the incoming client connection
	 * 
	 * @param channel
	 *  The newly connected SocketChannel to say hello to.
	 * @throws Exception
	 */
	private void sayHello(SocketChannel channel) throws Exception{
		buffer.clear();
		buffer.put("Hi there!\r\n".getBytes());
		buffer.flip();
		channel.write(buffer);
	}

}

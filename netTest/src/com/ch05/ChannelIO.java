package com.ch05;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ChannelIO {
	
	protected SocketChannel socketChannel;
	protected ByteBuffer requestBuffer; //存放请求数据
	private static int requestBufferSize=4096;
	
	public ChannelIO(SocketChannel socketChannel,boolean blocking)throws IOException{
		this.socketChannel=socketChannel;
		socketChannel.configureBlocking(blocking);//设置模式
		requestBuffer=ByteBuffer.allocate(requestBufferSize);
	}
	
	public SocketChannel getSocketChannel(){
		return socketChannel;
	}
	
	/**
	 * 如果原缓冲区的剩余容量不够，就创建一个新的缓冲区，容量为原来的两倍
	 * 把原来缓冲区的数据复制到新缓冲区
	 * @param remaining
	 */
	protected void resizeRequestBuffer(int remaining){
		if(requestBuffer.remaining()<remaining){
			//把容量增大到原来两倍
			ByteBuffer bb=ByteBuffer.allocate(requestBuffer.capacity()*2);
			requestBuffer.flip();
			bb.put(requestBuffer);//把原来缓冲区中的数据复制到新的缓冲区
			requestBuffer=bb;
		}
	}
	/**
	 * 接收数据，把他们存放到requestBuffer中，如果requestBuffer的剩余容量不足5%
	 * 就通过reasizeRequestBuffer()方法扩充容量
	 * @return
	 * @throws IOException
	 */
	public int read() throws IOException{
		resizeRequestBuffer(requestBufferSize/20);
		return socketChannel.read(requestBuffer);
	}
	
	
	public ByteBuffer getReadBuf(){
		return requestBuffer;
	}

}

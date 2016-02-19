package com.ch05;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ChannelIO {
	
	protected SocketChannel socketChannel;
	protected ByteBuffer requestBuffer; //�����������
	private static int requestBufferSize=4096;
	
	public ChannelIO(SocketChannel socketChannel,boolean blocking)throws IOException{
		this.socketChannel=socketChannel;
		socketChannel.configureBlocking(blocking);//����ģʽ
		requestBuffer=ByteBuffer.allocate(requestBufferSize);
	}
	
	public SocketChannel getSocketChannel(){
		return socketChannel;
	}
	
	/**
	 * ���ԭ��������ʣ�������������ʹ���һ���µĻ�����������Ϊԭ��������
	 * ��ԭ�������������ݸ��Ƶ��»�����
	 * @param remaining
	 */
	protected void resizeRequestBuffer(int remaining){
		if(requestBuffer.remaining()<remaining){
			//����������ԭ������
			ByteBuffer bb=ByteBuffer.allocate(requestBuffer.capacity()*2);
			requestBuffer.flip();
			bb.put(requestBuffer);//��ԭ���������е����ݸ��Ƶ��µĻ�����
			requestBuffer=bb;
		}
	}
	/**
	 * �������ݣ������Ǵ�ŵ�requestBuffer�У����requestBuffer��ʣ����������5%
	 * ��ͨ��reasizeRequestBuffer()������������
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

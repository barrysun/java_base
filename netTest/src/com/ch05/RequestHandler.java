package com.ch05;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

public class RequestHandler implements Handler {
	
	private ChannelIO channelIO;
	private ByteBuffer requestByteBuffer=null;//存放HTTP请求的缓冲区
	
	private boolean requestReceived=false;//表示是否已经接收到HTTP请求的所有数据
	private Request request=null;//表示HTTP请求
	private Response response=null;//表示HTTP响应
	
	RequestHandler(ChannelIO channelIO){
		this.channelIO=channelIO;
	}
	
	/**
	 * 接收HTTP请求，如果已经接收到了HTTP请求的所有数据，就返回true，否则返回false
	 * @param sk
	 * @return
	 * @throws IOException
	 */
	private boolean receive(SelectionKey sk) throws IOException{
		ByteBuffer tmp=null;
		if(requestReceived)return true;//如果已经接收到HTTP请求的所有数据，就返回true
		
		//如果已经读到通道的末尾，或者已经读到HTTP请求数据的末尾标志，就返回ture
		if((channelIO.read()<0||Request.isComplete(channelIO.getReadBuf()))){
			requestByteBuffer=channelIO.getReadBuf();
			return (requestReceived=true);
		}
		
		return false;
	}
	
	/**
	 * 通过Request类的parse()方法，解析requestByteBuffer中的HTTP请求数据
	 * 构造相应的Request对象
	 * @return
	 * @throws IOException
	 */
	private boolean parse() throws IOException{
		
		try{
			request=Request.parse(requestByteBuffer);
			return true;
		}catch(Exception e){
			//如果HTTP请求的格式不正确，就发送错误信息
			response=new Response(Response.Code.BAD_REQUEST,new StringContent(e));
		}
		
		return false;
	}
	/**
	 * 创建HTTP响应
	 * @throws IOException
	 */
	private void build() throws IOException{
		Request.Action action=request.action();
		//仅仅支持GET和HEAD请求方式
		if((action!=Request.Action.GET)&&(action!=Request.Action.HEAD)){
			response=new Response(Response.Code.METHOD_NOT_ALLOWED,new StringContent("Method Not Allowd"));
		}else{
			response=new Response(Response.Code.OK,new FileContent(request.uri()),action);
		}
	}
	
	

	/**
	 * 接收HTTP请求，发送HTTP响应
	 */
	@Override
	public void handle(SelectionKey sk) throws IOException {
		try{
			if(request==null) //如果还没有收到HTTP请求的所有数据
			{
				//接收到HTTP请求
				if(!receive(sk))return;
				requestByteBuffer.flip();
				//如果成功解析了HTTP请求，就创建一个Response对象
				if(parse())build();
				
				try{
					response.prepare();//准备HTTP响应的内容
				}catch(IOException e){
					response.release();
					response=new Response(Response.Code.NOT_FOUND,new StringContent(e));
					response.prepare();
				}
				
				if(send()){
					//如果HTTP响应没有发送完毕，则需要注册写就绪事件，
					//以便在写就绪事件发生时继续发送数据
					sk.interestOps(SelectionKey.OP_WRITE);
				}else{
					//如果HTTP响应发送完毕，就断开底层的连接
					//并释放Response占用的资源
					channelIO.close();
					response.release();
				}
				
			}else{
				//如果已经接收到HTTP请求的所有数据
				//如果HTTP响应发送完毕
				if(!send()){
					channelIO.close();
					response.release();
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 发送HTTP响应，如果全部发送完毕，就返回false，否则返回true
	 * @return
	 * @throws IOException
	 */
    private boolean send() throws IOException{
    	return response.send(channelIO);
    }
	
	

}

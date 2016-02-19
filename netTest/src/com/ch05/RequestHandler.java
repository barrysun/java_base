package com.ch05;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

public class RequestHandler implements Handler {
	
	private ChannelIO channelIO;
	private ByteBuffer requestByteBuffer=null;//���HTTP����Ļ�����
	
	private boolean requestReceived=false;//��ʾ�Ƿ��Ѿ����յ�HTTP�������������
	private Request request=null;//��ʾHTTP����
	private Response response=null;//��ʾHTTP��Ӧ
	
	RequestHandler(ChannelIO channelIO){
		this.channelIO=channelIO;
	}
	
	/**
	 * ����HTTP��������Ѿ����յ���HTTP������������ݣ��ͷ���true�����򷵻�false
	 * @param sk
	 * @return
	 * @throws IOException
	 */
	private boolean receive(SelectionKey sk) throws IOException{
		ByteBuffer tmp=null;
		if(requestReceived)return true;//����Ѿ����յ�HTTP������������ݣ��ͷ���true
		
		//����Ѿ�����ͨ����ĩβ�������Ѿ�����HTTP�������ݵ�ĩβ��־���ͷ���ture
		if((channelIO.read()<0||Request.isComplete(channelIO.getReadBuf()))){
			requestByteBuffer=channelIO.getReadBuf();
			return (requestReceived=true);
		}
		
		return false;
	}
	
	/**
	 * ͨ��Request���parse()����������requestByteBuffer�е�HTTP��������
	 * ������Ӧ��Request����
	 * @return
	 * @throws IOException
	 */
	private boolean parse() throws IOException{
		
		try{
			request=Request.parse(requestByteBuffer);
			return true;
		}catch(Exception e){
			//���HTTP����ĸ�ʽ����ȷ���ͷ��ʹ�����Ϣ
			response=new Response(Response.Code.BAD_REQUEST,new StringContent(e));
		}
		
		return false;
	}
	/**
	 * ����HTTP��Ӧ
	 * @throws IOException
	 */
	private void build() throws IOException{
		Request.Action action=request.action();
		//����֧��GET��HEAD����ʽ
		if((action!=Request.Action.GET)&&(action!=Request.Action.HEAD)){
			response=new Response(Response.Code.METHOD_NOT_ALLOWED,new StringContent("Method Not Allowd"));
		}else{
			response=new Response(Response.Code.OK,new FileContent(request.uri()),action);
		}
	}
	
	

	/**
	 * ����HTTP���󣬷���HTTP��Ӧ
	 */
	@Override
	public void handle(SelectionKey sk) throws IOException {
		try{
			if(request==null) //�����û���յ�HTTP�������������
			{
				//���յ�HTTP����
				if(!receive(sk))return;
				requestByteBuffer.flip();
				//����ɹ�������HTTP���󣬾ʹ���һ��Response����
				if(parse())build();
				
				try{
					response.prepare();//׼��HTTP��Ӧ������
				}catch(IOException e){
					response.release();
					response=new Response(Response.Code.NOT_FOUND,new StringContent(e));
					response.prepare();
				}
				
				if(send()){
					//���HTTP��Ӧû�з�����ϣ�����Ҫע��д�����¼���
					//�Ա���д�����¼�����ʱ������������
					sk.interestOps(SelectionKey.OP_WRITE);
				}else{
					//���HTTP��Ӧ������ϣ��ͶϿ��ײ������
					//���ͷ�Responseռ�õ���Դ
					channelIO.close();
					response.release();
				}
				
			}else{
				//����Ѿ����յ�HTTP�������������
				//���HTTP��Ӧ�������
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
	 * ����HTTP��Ӧ�����ȫ��������ϣ��ͷ���false�����򷵻�true
	 * @return
	 * @throws IOException
	 */
    private boolean send() throws IOException{
    	return response.send(channelIO);
    }
	
	

}

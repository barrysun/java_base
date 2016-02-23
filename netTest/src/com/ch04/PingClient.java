package com.ch04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * ������ģʽ
 * @author apple
 *
 */
public class PingClient {
	
	private Selector selector;
	//����û����ύ������
	private LinkedList targets=new LinkedList();
	//����Ѿ���ɵ���Ҫ��ӡ������
	private LinkedList finishedTargets=new LinkedList();
	
	public PingClient() throws IOException{
		selector=Selector.open();
		Connector connector=new Connector();
		Printer printer=new Printer();
		connector.start();//���������߳�
		printer.start();//������ӡ�߳�
		receiveTarget();//���߳̽����û��ӿ���̨�������������Ȼ���ύTarget
		
	}
	
	public static void main(String[] args)throws IOException{
		new PingClient();
	}
	
	public void addTarget(Target target){
		//��targets�����м���һ���������̻߳���ø÷���
		SocketChannel socketChannel=null;
		try{
			socketChannel=SocketChannel.open();
			socketChannel.configureBlocking(false);
			
		}catch(Exception e){
			if(socketChannel!=null){
				try{
					socketChannel.close();
				}catch(IOException ex){
					
				}
			}
			target.failure=e;
			addFinishedTarget(target);
		}
	}
	
	public void addFinishedTarget(Target target){
		//��finishedTargets�����м���һ���������̺߳�Connector�̻߳���ø÷���
		synchronized(finishedTargets){
			finishedTargets.notify();
			finishedTargets.add(target);
		}
	}
	
	public void printFinishedTargets(){
		//��ӡfinishedTargets�����е�����Printer�̻߳���ø÷���
		try{
			for(;;){
				Target target=null;
				synchronized(finishedTargets){
					while(finishedTargets.size()==0){
						finishedTargets.wait();
					}
					target=(Target)finishedTargets.removeFirst();
					target.show();
				}
			}
		}catch(Exception e){
			return;
		}
	}
	
	
	public void receiveTarget(){
		//�����û��������������targets�����м����������̻߳���ø÷���
		try{
			BufferedReader localReader=new BufferedReader(new InputStreamReader(System.in));
			String msg=null;
			while((msg=localReader.readLine())!=null){
				if(!msg.equals("bye")){
					Target target=new Target(msg);
					addTarget(target);
				}else{
					shutdown=true;
					selector.wakeup(); //ʹConnector�̴߳�Selector��select()�������˳�
					break;
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	
	boolean shutdown=false;//���ڿ���Connector�߳�
	
	
	public class Printer extends Thread{
		public Printer(){
			setDaemon(true);//����Ϊ��̨�߳�
		}
	}
	
	public void registerTargets(){
		//ȡ��targets�����е�������Selectorע�����Ӿ����¼���Connector�̻߳���ø÷���
		synchronized(targets){
			while(targets.size()>0){
				Target target=(Target)targets.removeFirst();
				try{
					target.channel.register(selector, SelectionKey.OP_CONNECT,target);
					
				}catch(Exception e){
					try{
						target.channel.close();
					}catch(Exception ex){
						ex.printStackTrace();
					}
					target.failure=e;
					addFinishedTarget(target);
				}
			}
		}
		
	}
	
	public void processSelectedKeys() throws IOException{
		//�������Ӿ����¼���Connector�̻߳���ø÷���
		for(Iterator<?> it=selector.selectedKeys().iterator();it.hasNext();){
		    SelectionKey selectionKey=(SelectionKey)it.next();
		    it.remove();
		    
		    Target target=(Target)selectionKey.attachment();
		    SocketChannel socketChannel=(SocketChannel)selectionKey.channel();
		    
		    try{
		    	
		    	if(socketChannel.finishConnect()){
		    		selectionKey.cancel();
		    		target.connectFinish=System.currentTimeMillis();
		    		socketChannel.close();
		    		addFinishedTarget(target);
		    	}
		    	
		    }catch(Exception e){
		    	socketChannel.close();
		    	target.failure=e;
		    	addFinishedTarget(target);
		    }
		    
		}
	}
	
	
	
	
	
	public class Connector extends Thread{
		public void run(){
			while(!shutdown){
				try{
					registerTargets();
					if(selector.select()>0){
						processSelectedKeys();
					}
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			try{
				selector.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	
	
	

}

class Target{ //��ʾһ������

	InetSocketAddress address;
	SocketChannel channel;
	Exception failure;
	long connectStart;//��ʼ����ʱ��ʱ��
	long connectFinish=0;//���ӳɹ�ʱ��ʱ��
	boolean shown=false;//�������Ƿ��Ѿ���ӡ
	
	Target(String host){
		try{
			address=new InetSocketAddress(InetAddress.getByName(host),80);
			
		}catch(IOException e){
			failure=e;
		}
	}
	
	void show(){
		String result;
		if(connectFinish!=0){
			result=Long.toString(connectFinish-connectStart)+"ms";
			
				
		}else if(failure!=null){
			result=failure.toString();
		}else{
			result="Timed out";
			
		}
		System.out.println(address+":"+result);
		shown=true;
	}
}

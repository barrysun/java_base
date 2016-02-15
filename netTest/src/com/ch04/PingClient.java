package com.ch04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

/**
 * 非阻塞模式
 * @author apple
 *
 */
public class PingClient {
	
	private Selector selector;
	//存放用户新提交的任务
	private LinkedList targets=new LinkedList();
	//存放已经完成的需要打印的任务
	private LinkedList finishedTargets=new LinkedList();
	
	public PingClient() throws IOException{
		selector=Selector.open();
		Connector connector=new Connector();
		Printer printer=new Printer();
		connector.start();//启动连接线程
		printer.start();//启动打印线程
		receiveTarget();//主线程接收用户从控制台输入的主机名，然后提交Target
		
	}
	
	public static void main(String[] args)throws IOException{
		new PingClient();
	}
	
	public void addTarget(Target target){
		//向targets队列中加入一个任务，主线程会调用该方法
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
		//向finishedTargets队列中加入一个任务，主线程和Connector线程会调用该方法
		synchronized(finishedTargets){
			finishedTargets.notify();
			finishedTargets.add(target);
		}
	}
	
	
	public void receiveTarget(){
		//接收用户输入的域名，向targets队列中加入任务，主线程会调用该方法
		try{
			BufferedReader localReader=new BufferedReader(new InputStreamReader(System.in));
			String msg=null;
			while((msg=localReader.readLine())!=null){
				if(!msg.equals("bye")){
					Target target=new Target(msg);
					addTarget(target);
				}else{
					shutdown=true;
					selector.wakeup(); //使Connector线程从Selector的select()方法中退出
					break;
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	
	boolean shutdown=false;//用于控制Connector线程
	
	
	public class Printer extends Thread{
		public Printer(){
			setDaemon(true);//设置为后台线程
		}
	}
	
	public void registerTargets(){
		
	}
	
	public void processSelectedKeys() throws IOException{
		
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

class Target{ //表示一项任务

	InetSocketAddress address;
	SocketChannel channel;
	Exception failure;
	long connectStart;//开始连接时的时间
	long connectFinish=0;//连接成功时的时间
	boolean shown=false;//该任务是否已经打印
	
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

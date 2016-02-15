package com.ch04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 非阻塞模式
 * @author apple
 * 
 * 非阻塞的通信机制主要由java.nio包（新I/O包）中的类实现，主要的类包括ServerSocketChannel
 * SocketChannel、Selector、SelectionKey和ByteBuffer等。
 * 
 * 线程阻塞的原因
 * 线程执行了Thread.sleep(int n) 方法，线程放弃CPU，睡眠n毫秒，然后恢复运行。
 * 线程要执行一段同步代码，由于无法获得相关的同步锁，只好进入阻塞状态，等到获得了同步锁，才能恢复运行。
 * 线程执行了一个对象的wait()方法，进入阻塞状态，只有等到其他线程执行了该对象的notify()或notifyAll()方法，才可能将其唤醒。
 * 线程执行I/O操作或进行远程通信时，会因为等待相关的资源而进入阻塞状态。例如，
 * 当线程执行System.in.read()方法时，如果用户没有向控制台输入数据，则线程会一直等读到了用户输入数据才从read()方法 返回。
 * 
 * 进行远程通信时，在客户程序中，线程在以下情况可能进入的阻塞状态。
 * 
 * 请求与服务器建立连接时，即当线程执行Socket的带参数的构造方法，或执行Socket的connect()方法时，会进入阻塞状态，直到连接成功，此线程才从Socket的构造方法或connect()方法返回。
 * 线程从Socket的输入流读入数据时，如果没有足够的数据，就会进入阻塞状态，直到读到了足够的数据，或者到达输入流的末尾，或者出现了异常，才从输入流的read()方法返回或异常中断。输入流中有
 * 多少数据才算足够呢？这要看线程执行的read()方法的类型。
 * 》int read():只要输入流中有一个字节，就算足够。
 * 》int read(byte[] buff):只要输入流中的字节数目与参数buff数组的长度相同，就算足够。
 * 》String readLine():只要输入流
 * 
 * 
 * java.nio包中的主要类
 * java.nio包提供了支持非阻塞通信的类。
 * 
 *
 * 
 *
 */

//阻塞模式
public class EchoServer01 {

	private int port =8000;
	private ServerSocketChannel serverSocketChannel = null;
	private ExecutorService executorService; //线程池
	private static final int POOL_MULTIPLE=4;//线程中工作线程的数目
	
	
	public EchoServer01() throws IOException{
		//创建一个线程池
		executorService=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * POOL_MULTIPLE);
		//创建一个ServerSocketChannel对象
		serverSocketChannel=ServerSocketChannel.open();
		//使得在同一个主机上关闭了服务器程序，紧接着再启动该服务器程序时，
		//可以顺利绑定相同的端口。
		serverSocketChannel.socket().setReuseAddress(true);
		//把服务器进程与本地端口绑定
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		System.out.println("服务器启动....");
		
	}
	
	public void service(){
		while(true){
			SocketChannel socketChannel=null;
			try{
				socketChannel=serverSocketChannel.accept();
				executorService.execute(new Handler(socketChannel));//处理客户连接
				
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String args[])throws IOException{
		new EchoServer01().service();
	}
	
	class Handler implements Runnable{ //处理客户连接
		
		private SocketChannel socketChannel;
		

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
		
		public Handler(SocketChannel socketChannel){
			this.socketChannel=socketChannel;
		}
		
		public void handle(SocketChannel socketChannel){
			try{
				Socket socket=socketChannel.socket(); //获得与socketChannel关联的Socket对象
				System.out.println(":"+socket.getInetAddress()+":"+socket.getPort());
			
				BufferedReader br=getReader(socket);
				PrintWriter pw=getWriter(socket);
				
				String msg=null;
				while((msg=br.readLine())!=null){
					System.out.println(msg);
					pw.println(echo(msg));
					if(msg.equals("bye")){
						break;
					}
				}
				
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				try{
					if(socketChannel!=null) socketChannel.close();
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		}
		
		
		private PrintWriter getWriter(Socket socket) throws IOException{
			
			OutputStream socketOut = socket.getOutputStream();
			return new PrintWriter(socketOut,true);
		}
		
		private BufferedReader getReader(Socket socket) throws IOException{
			InputStream socketIn=socket.getInputStream();
			return new BufferedReader(new InputStreamReader(socketIn));
		}
		
		public String echo(String msg){
			return "echo:"+msg;
		}
		
	}
	
	
	
}

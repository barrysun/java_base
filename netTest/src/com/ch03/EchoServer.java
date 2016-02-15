package com.ch03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

/**
 * 使用线程池的注意事项
 * 
 * 虽然线程池能大大提高服务器的并发性能，但使用它也会存在一定风险。与所有多线程应用程序一样
 * 用线程池构建的应用程序很容易产生各种并发问答题，如对共享资源的竞争和死锁。
 * 
 * 1、死锁
 * 
 * 2、系统资源不足
 * 
 * 3、并发错误
 * 
 * 
 * 4、线程泄露
 * 
 * 
 * 5、任务过载
 * 
 * 
 * 
 * 
 * @author apple
 *
 */

public class EchoServer {

	private int port = 8000;
	private ServerSocket serverSocket;
	private ExecutorService executorService; // 线程池
	private final int POOL_SIZE = 4;// 单个cpu时线程池中工作线程的数目

	private int portForShutdown = 8001; // 用于监听关闭服务器命令的端口
	private ServerSocket serverSocketForShutdown;//
	private boolean isShutdown = false; // 服务器是否已经关闭

	private Thread shutdownThread = new Thread(new Runnable() {

		@Override
		public void run() {
			while (!isShutdown) {
				Socket socketForShutdown = null;
				try {
					socketForShutdown = serverSocketForShutdown.accept();
					BufferedReader br = new BufferedReader(new InputStreamReader(socketForShutdown.getInputStream()));
					String command = br.readLine();
					if (command.equals("shutdown")) {
						long beginTime = System.currentTimeMillis();
						socketForShutdown.getOutputStream().write("服务器正在关闭\r\n".getBytes());
						isShutdown = true;
						// 请求关闭线程池
						// 线程池不再接收新的任务，但是会继续执行完工作队列中现有的任务
						executorService.shutdown();
						// 等待关闭线程池，每次等待的超时时间为30秒
						while (!executorService.isTerminated()) {
							executorService.awaitTermination(30, TimeUnit.SECONDS);
						}
						serverSocket.close();//
						long endTime = System.currentTimeMillis();

						socketForShutdown.getOutputStream().write("".getBytes());
						socketForShutdown.close();
						serverSocketForShutdown.close();
					} else {
						socketForShutdown.getOutputStream().write("".getBytes());
						socketForShutdown.close();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	});

	public EchoServer() throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(60000); //
		serverSocketForShutdown = new ServerSocket(portForShutdown);
		//
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * POOL_SIZE);

		shutdownThread.setDaemon(true);
		shutdownThread.start();
		System.out.println("server ....");

	}

	public void service() throws IOException {
		while (!isShutdown) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				//
				socket.setSoTimeout(60000);
				executorService.execute(new Handler(socket));

			} catch (Exception e) {
				e.printStackTrace();
				if (socket != null) {
					socket.close();
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		new EchoServer().service();
	}

	class Handler implements Runnable { // 负责与单个客户的通信

		private Socket socket;

		public Handler(Socket socket) {
			this.socket = socket;
		}
		
		public String echo(String msg){
			return "echo:"+msg;
		}
		
		private PrintWriter getWriter(Socket socket)throws IOException{
			OutputStream socketOut=socket.getOutputStream();
			return new PrintWriter(socketOut,true);
		}
		
		private BufferedReader getReader(Socket socket) throws IOException{
			InputStream socketIn=socket.getInputStream();
			return new BufferedReader(new InputStreamReader(socketIn));
		}
		

		@Override
		public void run() {
			try{
				System.out.println("New connection accepted "+socket.getInetAddress()+":"+ socket.getPort());
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
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try{
					if(socket!=null){
						socket.close();
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}

		}

	}

}

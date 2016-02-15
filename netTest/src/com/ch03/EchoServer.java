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
 * ʹ���̳߳ص�ע������
 * 
 * ��Ȼ�̳߳��ܴ����߷������Ĳ������ܣ���ʹ����Ҳ�����һ�����ա������ж��߳�Ӧ�ó���һ��
 * ���̳߳ع�����Ӧ�ó�������ײ������ֲ����ʴ��⣬��Թ�����Դ�ľ�����������
 * 
 * 1������
 * 
 * 2��ϵͳ��Դ����
 * 
 * 3����������
 * 
 * 
 * 4���߳�й¶
 * 
 * 
 * 5���������
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
	private ExecutorService executorService; // �̳߳�
	private final int POOL_SIZE = 4;// ����cpuʱ�̳߳��й����̵߳���Ŀ

	private int portForShutdown = 8001; // ���ڼ����رշ���������Ķ˿�
	private ServerSocket serverSocketForShutdown;//
	private boolean isShutdown = false; // �������Ƿ��Ѿ��ر�

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
						socketForShutdown.getOutputStream().write("���������ڹر�\r\n".getBytes());
						isShutdown = true;
						// ����ر��̳߳�
						// �̳߳ز��ٽ����µ����񣬵��ǻ����ִ���깤�����������е�����
						executorService.shutdown();
						// �ȴ��ر��̳߳أ�ÿ�εȴ��ĳ�ʱʱ��Ϊ30��
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

	class Handler implements Runnable { // �����뵥���ͻ���ͨ��

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

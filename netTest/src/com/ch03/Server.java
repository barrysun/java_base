package com.ch03;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 构造ServerSocket
 * serverSocket 的构造方法y有以下几种重载形式：
 * 
 * 1、ServerSocket() throws IOException;
 * 2、ServerSocket(int port) throws IOException;
 * 3、ServerSocket(int port,int backlog) throws IOException;
 * 4、ServerSocket(int port,int backlog,InetAddress bindAddr) throws IOException;
 * 
 * 在以上构造方法中，参数port指定服务器要绑定的端口（服务器要监听的端口），
 * 参数backlog指定客户链接请求队列的长度，参数bindAddr指定服务器要绑定的IP地址。
 *  绑定端口
 *  除了第一个不带参数的构造方法以外，其他构造方法都会使服务器与特定端口绑定，该端口由参数port指定。例如，以下代码
 *  创建了一个与80端口绑定的服务器：
 *  ServerSocket serverSocket=new ServerSocket(80);
 *  如果运行时无法绑定到80 端口，以上代码会抛出IOException，更确切的说，是抛出BindException，
 *  它是IOException的子类。BindException 一般是由以下原因造成的：
 *  （1）、端口已经被其他服务器进程占用；
 *  （2）、在某些操作系统中，如果没有以超级用户的身份来运行服务器程序，那么操作系统不允许服务器绑定1-1023 之间的端口。
 *  
 *  
 *  
 *  设定客户连接请求队列的长度
 *  
 *  当服务器进程运行时，可能会同时监听到多个客户的链接请求。例如，每当一个客户进程执行以下代码：
 *  Socket socket=new Socket(www.javathinker.org,80);
 *  就意味着在远程www.javathinker.org 主机的80端口上，监听到了一个客户的连接请求。管理客户
 *  连接请求的任务是由操作系统来完成的。操作系统把这些链接请求存储在一个先进先出的队列中。许多操作
 *  系统限定了队列的最大长度，一般为50.当队列中链接请求达到了队列的最大容量时，服务器进程所在主机会拒绝
 *  新的连接请求。只有当服务器进程通过ServerSocket的accept（）方法从队列中取出连接请求，使队列腾出空位时，
 *  队列才能继续加入新的连接请求。
 *  
 *  对客户端进程，如果它发出的连接请求被加入到服务器的队列中，就意味着客户与服务器的连接建立成功，客户进程从Socket构建
 *  方法中正常返回。如果客户进程发出的连接请求被服务器拒绝，Socket构建方法就会抛出ConnectionException。
 *  
 *  ServerSocket构造方法的backlog参数用来显示设置连接请求队列的长度，他将覆盖操作系统限定的队列的最大长度，值得注意的是，在以下几种情况
 *  中，仍然会采用操作系统限定的队列的最大长度：
 *  （1）、backlog参数的值大于操作系统限定的队列的最大长度
 *  （2）、backlog参数的值小于或等于0
 *  （3）、在ServerSocket构造方法中没有设置backlog参数。
 *  
 *  
 *  
 * @author apple
 *
 */
public class Server {
	
	private int port=8000;
	private ServerSocket serverSocket;
	
	public Server() throws IOException{
		serverSocket = new ServerSocket(port,3);
		System.out.println("Server.....");
	}
	
	public void server() throws IOException{
		while(true){
			Socket socket=null;
			try{
				socket=serverSocket.accept();
				System.out.println("New connection accepted "+
				socket.getInetAddress()+" : "+socket.getPort());
				
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				try{
					if(socket!=null) socket.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception{
		Server server=new Server();
		//Thread.sleep(60000*10);
		server.server();
	}

}

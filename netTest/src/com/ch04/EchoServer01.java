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
 * ������ģʽ
 * @author apple
 * 
 * ��������ͨ�Ż�����Ҫ��java.nio������I/O�����е���ʵ�֣���Ҫ�������ServerSocketChannel
 * SocketChannel��Selector��SelectionKey��ByteBuffer�ȡ�
 * 
 * �߳�������ԭ��
 * �߳�ִ����Thread.sleep(int n) �������̷߳���CPU��˯��n���룬Ȼ��ָ����С�
 * �߳�Ҫִ��һ��ͬ�����룬�����޷������ص�ͬ������ֻ�ý�������״̬���ȵ������ͬ���������ָܻ����С�
 * �߳�ִ����һ�������wait()��������������״̬��ֻ�еȵ������߳�ִ���˸ö����notify()��notifyAll()�������ſ��ܽ��份�ѡ�
 * �߳�ִ��I/O���������Զ��ͨ��ʱ������Ϊ�ȴ���ص���Դ����������״̬�����磬
 * ���߳�ִ��System.in.read()����ʱ������û�û�������̨�������ݣ����̻߳�һֱ�ȶ������û��������ݲŴ�read()���� ���ء�
 * 
 * ����Զ��ͨ��ʱ���ڿͻ������У��߳�������������ܽ��������״̬��
 * 
 * �������������������ʱ�������߳�ִ��Socket�Ĵ������Ĺ��췽������ִ��Socket��connect()����ʱ�����������״̬��ֱ�����ӳɹ������̲߳Ŵ�Socket�Ĺ��췽����connect()�������ء�
 * �̴߳�Socket����������������ʱ�����û���㹻�����ݣ��ͻ��������״̬��ֱ���������㹻�����ݣ����ߵ�����������ĩβ�����߳������쳣���Ŵ���������read()�������ػ��쳣�жϡ�����������
 * �������ݲ����㹻�أ���Ҫ���߳�ִ�е�read()���������͡�
 * ��int read():ֻҪ����������һ���ֽڣ������㹻��
 * ��int read(byte[] buff):ֻҪ�������е��ֽ���Ŀ�����buff����ĳ�����ͬ�������㹻��
 * ��String readLine():ֻҪ������
 * 
 * 
 * java.nio���е���Ҫ��
 * java.nio���ṩ��֧�ַ�����ͨ�ŵ��ࡣ
 * 
 *
 * 
 *
 */

//����ģʽ
public class EchoServer01 {

	private int port =8000;
	private ServerSocketChannel serverSocketChannel = null;
	private ExecutorService executorService; //�̳߳�
	private static final int POOL_MULTIPLE=4;//�߳��й����̵߳���Ŀ
	
	
	public EchoServer01() throws IOException{
		//����һ���̳߳�
		executorService=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * POOL_MULTIPLE);
		//����һ��ServerSocketChannel����
		serverSocketChannel=ServerSocketChannel.open();
		//ʹ����ͬһ�������Ϲر��˷��������򣬽������������÷���������ʱ��
		//����˳������ͬ�Ķ˿ڡ�
		serverSocketChannel.socket().setReuseAddress(true);
		//�ѷ����������뱾�ض˿ڰ�
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		System.out.println("����������....");
		
	}
	
	public void service(){
		while(true){
			SocketChannel socketChannel=null;
			try{
				socketChannel=serverSocketChannel.accept();
				executorService.execute(new Handler(socketChannel));//����ͻ�����
				
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String args[])throws IOException{
		new EchoServer01().service();
	}
	
	class Handler implements Runnable{ //����ͻ�����
		
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
				Socket socket=socketChannel.socket(); //�����socketChannel������Socket����
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

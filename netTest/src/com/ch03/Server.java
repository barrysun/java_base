package com.ch03;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ����ServerSocket
 * serverSocket �Ĺ��췽��y�����¼���������ʽ��
 * 
 * 1��ServerSocket() throws IOException;
 * 2��ServerSocket(int port) throws IOException;
 * 3��ServerSocket(int port,int backlog) throws IOException;
 * 4��ServerSocket(int port,int backlog,InetAddress bindAddr) throws IOException;
 * 
 * �����Ϲ��췽���У�����portָ��������Ҫ�󶨵Ķ˿ڣ�������Ҫ�����Ķ˿ڣ���
 * ����backlogָ���ͻ�����������еĳ��ȣ�����bindAddrָ��������Ҫ�󶨵�IP��ַ��
 *  �󶨶˿�
 *  ���˵�һ�����������Ĺ��췽�����⣬�������췽������ʹ���������ض��˿ڰ󶨣��ö˿��ɲ���portָ�������磬���´���
 *  ������һ����80�˿ڰ󶨵ķ�������
 *  ServerSocket serverSocket=new ServerSocket(80);
 *  �������ʱ�޷��󶨵�80 �˿ڣ����ϴ�����׳�IOException����ȷ�е�˵�����׳�BindException��
 *  ����IOException�����ࡣBindException һ����������ԭ����ɵģ�
 *  ��1�����˿��Ѿ�����������������ռ�ã�
 *  ��2������ĳЩ����ϵͳ�У����û���Գ����û�����������з�����������ô����ϵͳ�������������1-1023 ֮��Ķ˿ڡ�
 *  
 *  
 *  
 *  �趨�ͻ�����������еĳ���
 *  
 *  ����������������ʱ�����ܻ�ͬʱ����������ͻ��������������磬ÿ��һ���ͻ�����ִ�����´��룺
 *  Socket socket=new Socket(www.javathinker.org,80);
 *  ����ζ����Զ��www.javathinker.org ������80�˿��ϣ���������һ���ͻ����������󡣹���ͻ�
 *  ����������������ɲ���ϵͳ����ɵġ�����ϵͳ����Щ��������洢��һ���Ƚ��ȳ��Ķ����С�������
 *  ϵͳ�޶��˶��е���󳤶ȣ�һ��Ϊ50.����������������ﵽ�˶��е��������ʱ����������������������ܾ�
 *  �µ���������ֻ�е�����������ͨ��ServerSocket��accept���������Ӷ�����ȡ����������ʹ�����ڳ���λʱ��
 *  ���в��ܼ��������µ���������
 *  
 *  �Կͻ��˽��̣�������������������󱻼��뵽�������Ķ����У�����ζ�ſͻ�������������ӽ����ɹ����ͻ����̴�Socket����
 *  �������������ء�����ͻ����̷������������󱻷������ܾ���Socket���������ͻ��׳�ConnectionException��
 *  
 *  ServerSocket���췽����backlog����������ʾ��������������еĳ��ȣ��������ǲ���ϵͳ�޶��Ķ��е���󳤶ȣ�ֵ��ע����ǣ������¼������
 *  �У���Ȼ����ò���ϵͳ�޶��Ķ��е���󳤶ȣ�
 *  ��1����backlog������ֵ���ڲ���ϵͳ�޶��Ķ��е���󳤶�
 *  ��2����backlog������ֵС�ڻ����0
 *  ��3������ServerSocket���췽����û������backlog������
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

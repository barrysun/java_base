package com.ch06;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Java�Կͻ������ͨ�Ź��̽����˳����ṩ��ͨ�õ�Э�鴦����
 * �����ܷ�װ��Socket����Ҫ�������¼����ࡣ
 * URL�ࣺͳһ��Դ��λ����Uniform Resource Locator������ʾ�ͻ�����Ҫ���ʵ�Զ����Դ
 * URLConnection�ࣺ��ʾ�ͻ�������Զ�̷����������ӡ��ͻ�������Դ�URLConnection���л���������������������
 * URLStreamHandler�ࣺЭ�鴦��������Ҫ���𴴽���Э����ص�URLConnection����
 * ContentHandler�ࣺ���ݴ�����������������������͵����ݣ�����ת��Ϊ��Ӧ��java����
 * 
 * �����඼λ��java.net���У�����URL��Ϊ���������⣬����3���඼�ǳ����࣬����һ�־����Э�飬��Ҫ
 * ������Ӧ��URLConnection��URIStreamHandler��ContentHandler�������ࡣ
 * 
 * @author apple
 *
 */

public class HttpClient1 {
	
	public static void main(String[] args)throws IOException{
		URL url=new URL("http://www.javathinker.org//hello.html");//http Э�����
		//������Ӧ���
		InputStream in=url.openStream();
		ByteArrayOutputStream buffer=new ByteArrayOutputStream();
		byte[] buff=new byte[1024];
		int len=-1;
		
		while((len=in.read(buff))!=-1){
			buffer.write(buff,0,len);
		}
		
		System.out.println(new String(buffer.toByteArray()));//���ֽ�����ת��Ϊ�ַ���
	}

}

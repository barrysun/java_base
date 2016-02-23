package com.ch05;

import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {
	
	static class Action{
		private String name;
		private Action(String name){this.name=name;}
		public String toString(){return name;}
		
		static Action GET=new Action("GET");
		static Action PUT=new Action("PUT");
		static Action POST=new Action("POST");
		static Action HEAD=new Action("HEAD");
		
		public static Action parse(String s){
			if(s.equals("GET")){
				return GET;
			}
			if(s.equals("PUT")){
				return PUT;
			}
			if(s.equals("POST")){
				return POST;
			}
			if(s.equals("HEAD")){
				return HEAD;
			}
			throw new IllegalArgumentException(s);
		}
		
	}
	
	private Action action;
	private String version;
	private URI uri;
	
	public Action action(){return action;}
	public String version(){return version;}
	public URI uri(){return uri;}
	
	private Request(Action a,String v,URI u){
		action=a;
		version=v;
		uri=u;
	}
	
	public String toString(){
		return (action+" "+version+" "+uri);
	}
	
	private static Charset requestCharset=Charset.forName("GBK");
	
	/**
	 * �ж�ByteBuffer�Ƿ������HTTP�������������
	 * @param bb
	 * @return
	 */
	public static boolean isComplete(ByteBuffer bb){
		return false;
	}
	
	/**
	 * ɾ���������ģ�������֧��GET��HEAD����ʽ��������HTTP���������Ĳ���
	 * @param bb
	 * @return
	 */
	private static ByteBuffer deleteContent(ByteBuffer bb){
		ByteBuffer temp=bb.asReadOnlyBuffer();
		String data=requestCharset.decode(temp).toString();
		if(data.indexOf("\r\n\r\n")!=-1){
			data=data.substring(0, data.indexOf("\r\n\r\n")+4);
			return requestCharset.encode(data);
		}
		return bb;
		
	}
	
	/**
	 * �趨���ڽ���HTTP������ַ���ƥ��ģʽ������������ʽ��HTTP����
	 * GET /dir/file HTTP/1.1
	 * Host:hostname
	 * 
	 * ���������ɣ�
	 * group[1]="GET"
	 * group[2]="dir/file"
	 * group[3]="1.1"
	 * group[4]="hostname"
	 */
	private static Pattern requestPattern = Pattern.compile("\\A([A-Z]+)+([^]+)+HTTP/([0-9\\.]+)$"
			+".*^Host:([^]+)$.*\r\n\r\n\\z",Pattern.MULTILINE|Pattern.DOTALL);
	
	public static Request parse(ByteBuffer bb) throws Exception{
		bb=deleteContent(bb);//ɾ����������
		CharBuffer cb=requestCharset.decode(bb);//����
		Matcher m=requestPattern.matcher(cb);//�����ַ���ƥ��
		//���HTTP������ָ�����ַ���ģʽ��ƥ�䣬˵���������ݲ���ȷ
		if(!m.matches()){
			throw new Exception();
		}
		Action a;
		try{
			a=Action.parse(m.group(1));
		}catch(Exception e){
			throw e;
		}
		URI u=null;
		try{
			u=new URI("http://"+m.group(4)+m.group(2));
			
		}catch(Exception e){
			throw e;
		}
		//����һ��Request���󣬲����䷵��
		return new Request(a,m.group(3),u);
	}

}

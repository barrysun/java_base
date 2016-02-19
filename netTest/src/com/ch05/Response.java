package com.ch05;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class Response implements Sendable {
	
	static class Code{
		private int number;
		private String reason;
		private Code(int i,String r){number = i;reason=r;}
		public String toString(){return number+" "+reason;}
		
		static Code OK=new Code(200,"OK");
		static Code BAD_REQUEST=new Code(400,"Bad Request");
		static Code NOT_FOUND=new Code(404,"Not Found");
		static Code METHOD_NOT_ALLOWED=new Code(405,"Method Not Allowed");
		
	}
	
	private Code code;//状态代码
	private Content content;//响应正文
	private boolean headersOnly;//表示HTTP响应中是否仅包含响应头
	private ByteBuffer headerBuffer=null;//响应头
	
	
	public Response(Code rc,Content c){
		this(rc,c,null);
	}
	
	public Response(Code rc,Content c,Request.Action head){
		code=rc;
		content=c;
		headersOnly=(head==Request.Action.HEAD);
		
	}
	
	
	private static String CRLF="\r\n";
	private static Charset responseCharset=Charset.forName("GBK");
	
	
	/**
	 * 创建响应头的内容，把它存放到一个ByteBuffer中
	 * @return
	 */
	private ByteBuffer headers(){
		CharBuffer cb=CharBuffer.allocate(1024);
		for(;;){
			try{
				cb.put("HTTP/1.1").put(code.toString()).put(CRLF);
				cb.put("Server:nio/1.1").put(CRLF);
				cb.put("Content-type:").put(content.type()).put(CRLF);
				cb.put("Content-length:").put(Long.toString(content.length())).put(CRLF);
				cb.put(CRLF);
				break;
			}catch(Exception e){
				e.printStackTrace();
				assert(cb.capacity()<(1<<16));
				cb=CharBuffer.allocate(cb.capacity()*2);
				continue;
			}
		}
		cb.flip();
		return responseCharset.encode(cb);
	}
	

	/**
	 * 准备HTTP响应中的正文，以及响应头的内容
	 * @throws IOException
	 */
	@Override
	public void prepare() throws IOException {
		content.prepare();
		headerBuffer=headers();
		
	}
	
	

	/**
	 * 发送HTTP响应，如果全部发送完毕，返回false，否则返回ture
	 * @param cio
	 * @return
	 * @throws IOException
	 */
	@Override
	public boolean send(ChannelIO cio) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 释放响应正文占用的资源
	 * @throws IOException
	 */
	@Override
	public void release() throws IOException {
		content.release();
		
	}

}

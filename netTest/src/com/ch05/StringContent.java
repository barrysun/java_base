package com.ch05;

import java.io.IOException;
import java.nio.charset.Charset;

public class StringContent implements Content {
	
	private String cxt="";
	
	private long length=-1;
	
	
	public StringContent(String str){
		cxt=str;
	}
	
	public StringContent(Exception e){
	    cxt=e.getMessage();
	}

	@Override
	public void prepare() throws IOException {
		length=cxt.length();
		
	}

	@Override
	public boolean send(ChannelIO channelIO) throws IOException {
		StringBuffer sb=new StringBuffer("");
		channelIO.getSocketChannel().write(Charset.forName("GBK").encode(cxt));
		return false;
	}

	@Override
	public void release() throws IOException {
		
		
	}

	@Override
	public String type() {
		// TODO Auto-generated method stub
		return "text/html;charset=ios-8859-1";
	}

	@Override
	public long length() {
		// TODO Auto-generated method stub
		return length;
	}

}

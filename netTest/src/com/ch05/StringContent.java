package com.ch05;

import java.io.IOException;

public class StringContent implements Content {
	
	
	public StringContent(String str){
		
	}
	
	public StringContent(Exception e){
		
	}

	@Override
	public void prepare() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean send(ChannelIO cio) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void release() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String type() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long length() {
		// TODO Auto-generated method stub
		return 0;
	}

}

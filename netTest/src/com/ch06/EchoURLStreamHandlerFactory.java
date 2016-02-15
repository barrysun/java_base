package com.ch06;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

public class EchoURLStreamHandlerFactory implements URLStreamHandlerFactory{

	@Override
	public URLStreamHandler createURLStreamHandler(String protocol) {
		// TODO Auto-generated method stub
		if(protocol.equals("echo")){
			return new EchoURLStreamHandler();
		}else{
			return null;
		}
	}

}

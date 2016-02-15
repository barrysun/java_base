package com.ch09;

import java.io.IOException;
import java.io.Serializable;

public class SimpleServer {
	
	public void send(Object object) throws IOException{
		
	}

}

class Customer implements Serializable{
	
	private transient String fullName;
}

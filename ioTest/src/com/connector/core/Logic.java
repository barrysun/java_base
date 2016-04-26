package com.connector.core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;

public interface Logic {
	
	public void processClientData(DataInputStream dis,DataOutputStream dos,InetAddress add) throws IOException;

}

package com.ch11;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {

	private String name;
	
	protected HelloServiceImpl(String name) throws RemoteException {
		this.name=name;
	}

	@Override
	public String echo(String msg) throws RemoteException {
		System.out.println(name+":调用echo()方法");
		return "echo:"+msg+" from "+name;
	}

	@Override
	public Date getTime() throws RemoteException {
		System.out.println(name+" :调用getTime()方法");
		return new Date();
	}

}

package com.ch11;

import javax.naming.Context;
import javax.naming.InitialContext;

public class SimpleServer {
	
	public static void main(String[] args){
		try{
			
			HelloService service1=new HelloServiceImpl("service1");
			HelloService service2=new HelloServiceImpl("service2");
			
			Context namingContext=new InitialContext();
			namingContext.rebind("rmi://localhost:8080/HelloService1", service1);
			namingContext.rebind("rmi://localhost:8080/HelloService2", service2);
			
			System.out.println("服务器注册了两个HelloService对象");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}

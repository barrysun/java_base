package com.ch09;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author apple
 * 
 * 把java对象转换为字节序列，才能在网络上发送；接收方则需要把字节序列再恢复成
 * java对象。把Java对象转换为字节序列的过程称为对象的序列化；把字节序列恢复为Java
 * 对象的过程称为对象的反序列化。
 * 
 * 
 *
 */

public class ObjectServer {
	
	public static void main(String[] args) throws Exception{
		
		ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(""));
		String obj1="hello";
		Date obj2=new Date();
		
		
	}

}

class Customer1 implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int age;
	
	public Customer1(String name,int age){
		
	}
}

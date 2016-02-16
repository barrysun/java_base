package com.ch09;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
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
		
		ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream("objectFile.obj"));
		String obj1="hello";
		Date obj2=new Date();
		Customer1 obj3=new Customer1("Tom",20);
		
		//序列化对象
		out.writeObject(obj1);
		out.writeObject(obj2);
		out.writeObject(obj3);
		out.writeInt(123);
		out.close();
		
		//反序列化对象
		ObjectInputStream in=new ObjectInputStream(new FileInputStream("objectFile.obj"));
		
		String obj11=(String)in.readObject();
		System.out.println("obj11:"+obj11);
		System.out.println("obj1==obj11:"+(obj11==obj1));
		
		Date obj22=(Date)in.readObject();
		System.out.println("obj22:"+obj22);
		System.out.println("obj22==obj2:"+(obj22==obj2));
		
		Customer1 obj33=(Customer1)in.readObject();
		System.out.println("obj33:"+obj33);
		System.out.println("obj33==obj3:"+(obj33==obj3));
		
		int var = in.readInt();
		System.out.println("var:"+var);
		in.close();
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
		
		this.name=name;
		this.age=age;
		
		
	}
	
	public String toString(){
		return "name="+name+",age="+age;
		
	}
}

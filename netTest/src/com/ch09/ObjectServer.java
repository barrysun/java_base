package com.ch09;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author apple
 * 
 * ��java����ת��Ϊ�ֽ����У������������Ϸ��ͣ����շ�����Ҫ���ֽ������ٻָ���
 * java���󡣰�Java����ת��Ϊ�ֽ����еĹ��̳�Ϊ��������л������ֽ����лָ�ΪJava
 * ����Ĺ��̳�Ϊ����ķ����л���
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

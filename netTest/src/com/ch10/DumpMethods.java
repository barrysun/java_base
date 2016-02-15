package com.ch10;

import java.lang.reflect.Method;

/**
 * 
 * 这种动态获取类的信息，以及动态调用对象的方法的功能来自与
 * java语言的反射（Reflection）机制。Java反射机制主要提供了
 * 以下功能：
 * 在运行时判断任意一个对象所属的类。
 * 在运行时构造任意一个类的对象
 * 在运行时判断任意一个类所具有的成员变量和方法。
 * 在运行时调用任意一个对象的方法
 * 生成动态代理。
 * @author apple
 *
 */

public class DumpMethods {
	
	public static void main(String args[]) throws Exception{
		//加载并初始化命令行参数指定的类
		Class classType=Class.forName(args[0]);
		//获得类的所有方法
		Method methods[]=classType.getDeclaredMethods();
		for(int i=0;i<methods.length;i++){
			System.out.println(methods[i].toString());
		}
		
	}

}

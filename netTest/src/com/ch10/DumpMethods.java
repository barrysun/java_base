package com.ch10;

import java.lang.reflect.Method;

/**
 * 
 * ���ֶ�̬��ȡ�����Ϣ���Լ���̬���ö���ķ����Ĺ���������
 * java���Եķ��䣨Reflection�����ơ�Java���������Ҫ�ṩ��
 * ���¹��ܣ�
 * ������ʱ�ж�����һ�������������ࡣ
 * ������ʱ��������һ����Ķ���
 * ������ʱ�ж�����һ���������еĳ�Ա�����ͷ�����
 * ������ʱ��������һ������ķ���
 * ���ɶ�̬����
 * @author apple
 *
 */

public class DumpMethods {
	
	public static void main(String args[]) throws Exception{
		//���ز���ʼ�������в���ָ������
		Class classType=Class.forName(args[0]);
		//���������з���
		Method methods[]=classType.getDeclaredMethods();
		for(int i=0;i<methods.length;i++){
			System.out.println(methods[i].toString());
		}
		
	}

}

package com.ch05;

public interface Content extends Sendable {

	//���ĵ�����
	String type();
	
	//�������ĵĳ���
	//�����Ļ�û��׼��֮ǰ������û�е���prepare()����֮ǰ��length()��������-1
	long length();
}

package com.ch05;

import java.io.IOException;

public interface Sendable {

	//׼�����͵�����
	public void prepare() throws IOException;
	
	//����ͨ�����Ͳ������ݣ�����������ݷ�����ϣ��ͷ���false
	//�����������δ���ͣ��ͷ���true
	//������ݻ�û��׼���ã����׳�IllegalStateException
	public boolean send(ChannelIO cio)throws IOException;
	
	//������������������ϣ��͵��ô˷������ͷ�����ռ�õ���Դ
	public void release() throws IOException;
}

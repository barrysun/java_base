package com.ch03;

import java.util.LinkedList;

/**
 * 
 * 
 * �����̳߳�
 * 
 * ��ÿ���ͻ�������һ���µĹ����̡߳��������̺߳�
 * �ͻ�ͨ�Ž���������߳̾ͱ����١�����ʵ�ַ�ʽ������
 * ����֮����
 * ��1�������������������ٹ����̵߳Ŀ��������������ѵ�ʱ���ϵͳ��Դ���Ӵ�
 * �����������Ҫ�����ͻ�ͨ�ţ�������ÿ���ͻ���ͨ��ʱ�䶼�̣ܶ���ô�п��ܷ�����Ϊ�ͻ�����
 * ���̵߳Ŀ�����ʵ����ͻ�ͨ�ŵĿ�����Ҫ��
 * ��2�������˴����������̵߳Ŀ���֮�⣬����߳�Ҳ����ϵͳ��Դ��ÿ���̱߳�����ռ��һ�����ڴ�
 * ��ÿ���߳���Ҫ��Լ1MB�ڴ棩�����ͬʱ�д����ͻ����ӷ��������ͱ��봴�����������̣߳����������˴����ڴ�
 * ���ܻᵼ��ϵͳ���ڴ�ռ䲻�㡣
 * ��3��������߳���Ŀ�̶�������ÿ���̶߳��кܳ����������ڣ���ô�߳��л�Ҳ����Թ̶��ġ���ͬ����ϵͳ�в�ͬ���л����ڣ�
 * һ����20�������ҡ�������˵���߳��л�ʱָ��java��������Լ��ײ����ϵͳ�ĵ����£��߳�֮��ת��CPU��ʹ��Ȩ�����
 * Ƶ�������������̣߳���ô������Ƶ�����л��̣߳���Ϊһ���̱߳����ٺ󣬱�ȻҪ��CPUת�ø���һ���Ѿ��������̣߳�ʹ���̻߳�����л��ᡣ��
 * ��������£��߳�֮����л�������ѭϵͳ�Ĺ̶��л����ڣ��л��̵߳Ŀ��������ȴ����������̵߳Ŀ�������
 * 
 * �̳߳�Ϊ�߳��������ڿ��������ϵͳ��Դ���������ṩ�˽���������̳߳���Ԥ�ȴ�����һЩ�����̣߳����ǻ�ӹ���������ȡ������Ȼ��
 * ִ�и����񡣵������߳�ִ����һ������ʱ���ͻ����ִ�й��������е���һ�������̳߳ؾ��������ŵ㣺
 * ��1���������˴����������̵߳Ĵ�����ÿ�������̶߳�����һֱ�����ã���ִ�ж������
 * ��2�������Ը���ϵͳ�ĳ�����������������̳߳����̵߳���Ŀ����ֹ��Ϊ���Ĺ���ϵͳ��Դ������ϵͳ������
 * 
 * 
 * 
 * @author apple
 *
 */

public class ThreadPool extends ThreadGroup{
	
	public ThreadPool(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	private boolean isClosed=false;//�̳߳��Ƿ�ر�
	private LinkedList<Runnable> workQueue;//��ʾ��������
	private static int threadPoolID;//��ʾ�̳߳�ID
	private int threadID;//��ʾ�����߳�ID
	
	
	public ThreadPool(int poolSize){//poolSizeָ���̳߳��еĹ����߳���Ŀ
		super("ThreadPool-"+(threadPoolID++));
		setDaemon(true);
		workQueue=new LinkedList<Runnable>();//������������
		for(int i=0;i<poolSize;i++){
			new WorkThread().start();//���������������߳�
		}
	}
	
	/*���������м���һ���������ɹ����߳�ִ�и�����*/
	public synchronized void execute(Runnable task){
		//�̳߳ر��ر����׳�IllegalStateException�쳣
		if(isClosed){
			throw new IllegalStateException();
		}
		if(task!=null){
			workQueue.add(task);
			notify();//��������getTask()�����еȴ�����Ĺ����߳�
		}
	}
	
	/**
	 * �ӹ���������ȡ��һ�����񣬹����̻߳���ô˷���
	 * @return
	 * @throws Exception
	 */
	protected synchronized Runnable getTask() throws Exception{
		while(workQueue.size()==0){
			if(isClosed)return null;
			wait();
		}
		return workQueue.removeFirst();
	}
	
	/**
	 * �ر��̳߳�
	 */
	public synchronized void close(){
		if(!isClosed){
			isClosed=true;
			workQueue.clear(); //��չ�������
			interrupt();//�ж����еĹ����̣߳��÷����̳���ThreadGroup��
		}
	}
	
	/**
	 * �ȴ������̰߳���������ִ����
	 */
	public void join(){
		synchronized(this){
			isClosed=true;
			notifyAll();//���ѻ���getTask�����еȴ�����Ĺ����߳�
			
		}
		
		Thread[] threads=new Thread[activeCount()];
		//enumerate()�����̳���ThreadGroup�࣬����߳����е�ǰ���л��ŵĹ����߳�
		int count=enumerate(threads);
		for(int i=0;i<count;i++){//�ȴ����й����߳����н���
			try{
				threads[i].join();//�ȴ������߳����н���
			}catch(InterruptedException ex){}
		}
	}
	
	/**
	 * �ڲ��࣬�����߳�
	 * @author apple
	 *
	 */
	private class WorkThread extends Thread{
		public WorkThread(){
			
			//���뵽��ǰThreadPool�߳�����
			super(ThreadPool.this,"WorkThread-"+(threadID++));
		}
		
		public void run(){
			while(!isInterrupted()){//isInterrupted()�����̳���Thread�࣬�ж��߳��Ƿ��жϡ�
				Runnable task=null;
				try{
					task=getTask();
				}catch(Exception ex){
					//
					ex.printStackTrace();
				}
				//���getTask����null�����߳�ִ��getTaskʱ���жϣ���������߳�
				if(task==null)return;
				try{
					//���������쳣��catch������в���
					task.run();
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
			}
		}
		
	}
	
	

}

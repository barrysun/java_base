package com.thread.zxx.ch09;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadTest {
	
	public static void main(String[] args){
		
		//ExecutorService threadPool=Executors.newFixedThreadPool(3);//�����̶���С���̳߳�
		//ExecutorService threadPool=Executors.newCachedThreadPool();//�����̳߳�
		ExecutorService threadPool=Executors.newSingleThreadExecutor();//�߳����� ��������һ���µ��߳�
		for(int i=0;i<10;i++){
			final int task=i;
			threadPool.execute(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					for(int j=1;j<=10;j++){
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName()+" is loop i="+task+",j="+j);
					}
				}
				
			});
		}
		System.out.println("all of 10 tasks have committed!");
		threadPool.shutdown();
		//�����̳߳�
		Executors.newScheduledThreadPool(3).schedule(new Runnable(){

			@Override
			public void run() {
				System.out.println("bombing!");
			}
		}, 10, TimeUnit.SECONDS);
		//�̶�Ƶ�ʵ����̳߳�
		Executors.newScheduledThreadPool(3).scheduleAtFixedRate(new Runnable(){

			@Override
			public void run() {
				System.out.println("bombing!");
			}
		}, 6,2, TimeUnit.SECONDS);
	}

}

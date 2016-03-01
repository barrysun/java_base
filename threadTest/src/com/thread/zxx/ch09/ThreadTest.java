package com.thread.zxx.ch09;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadTest {
	
	public static void main(String[] args){
		
		//ExecutorService threadPool=Executors.newFixedThreadPool(3);//创建固定大小的线程池
		//ExecutorService threadPool=Executors.newCachedThreadPool();//缓存线程池
		ExecutorService threadPool=Executors.newSingleThreadExecutor();//线程死掉 重新启动一个新的线程
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
		//调度线程池
		Executors.newScheduledThreadPool(3).schedule(new Runnable(){

			@Override
			public void run() {
				System.out.println("bombing!");
			}
		}, 10, TimeUnit.SECONDS);
		//固定频率调度线程池
		Executors.newScheduledThreadPool(3).scheduleAtFixedRate(new Runnable(){

			@Override
			public void run() {
				System.out.println("bombing!");
			}
		}, 6,2, TimeUnit.SECONDS);
	}

}

package com.thread.zxx.ch05;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 线程范围内数据共享
 * @author apple
 *
 */
public class ThreadScopShareData {
	
	static ThreadLocal<Integer> threadLocal=new ThreadLocal<Integer>();
	public static void main(String[] args){
		for(int i=0;i<2;i++){
			new Thread(new Runnable(){

				@Override
				public void run() {
					int data=new Random().nextInt();
					System.out.println(Thread.currentThread().getName()+" get data :"+data);
					threadLocal.set(data);
					new A().get();
					new B().get();
				}
				
			}).start();
		}
		
	}
	
	static class A{

		public void get() {
			
			System.out.println("A from "+Thread.currentThread().getName()+" get  data :"+threadLocal.get());
			
			
		}
		
	}
	static class B{
		public void get() {
			System.out.println("B from "+Thread.currentThread().getName()+" has put data :"+threadLocal.get());
			
			
		}
	}

}

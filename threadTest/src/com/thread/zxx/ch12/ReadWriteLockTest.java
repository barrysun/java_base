package com.thread.zxx.ch12;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {
	
	public static void main(String[] args){
		
		
		
		
		
	}

}
class Queue3{
	private Object data=null;//¹²ÏíÊý¾Ý
	private ReentrantReadWriteLock rwl=new ReentrantReadWriteLock();
	
	public void get(){
		rwl.readLock().lock();
		System.out.print(Thread.currentThread().getName()+" by read start");
		try{
			Thread.sleep((long)(Math.random()*1000));
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+"by read "+data+" end ");
		rwl.readLock().unlock();
	}
	
	public void put(Object data){
		rwl.writeLock().lock();
		System.out.println(Thread.currentThread().getName()+" by write start");
		try{
			Thread.sleep((long)(Math.random()*1000));
		}catch(Exception e){
			e.printStackTrace();
		}
		this.data=data;
		System.out.println(Thread.currentThread().getName()+" by write end");
		
	}
	
	
}

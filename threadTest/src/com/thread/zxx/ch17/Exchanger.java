package com.thread.zxx.ch17;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * �����߳�֮������ݽ���
 * @author apple
 *
 */
public class Exchanger {
	
	
	public static void main(String[] args){
		ExecutorService service=Executors.newCachedThreadPool();
		
		final Exchanger exchanger=new Exchanger();
		service.execute(new Runnable(){

			@Override
			public void run() {
				
				try{
					
					
				}catch(Exception e){
					
				}
				
			}
			
		});
		
		service.execute(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}

}

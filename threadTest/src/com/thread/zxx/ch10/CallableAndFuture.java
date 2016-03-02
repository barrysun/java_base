package com.thread.zxx.ch10;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

public class CallableAndFuture {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException{
		ExecutorService threadPool=Executors.newSingleThreadExecutor();
		
		Future<String> future=threadPool.submit(new Callable<String>(){

			@Override
			public String call() throws Exception {
				Thread.sleep(2000);
				return "hello";
			}
		});
		System.out.println("等待结果");
		System.out.println("结果："+future.get());
		
		ExecutorService threadPool2=Executors.newFixedThreadPool(10);
		CompletionService<Integer> completionService=new ExecutorCompletionService<Integer>(threadPool2);
		
		for(int i=1;i<=10;i++){
			final int seq=i;
			completionService.submit(new Callable<Integer>(){

				@Override
				public Integer call() throws Exception {
					// TODO Auto-generated method stub
					Thread.sleep(new Random().nextInt(5000));
					return seq;
				}
				
			});
		}
		
		for(int i=0;i<10;i++){
			System.out.println(completionService.take().get());
		}
		
		
	}

}

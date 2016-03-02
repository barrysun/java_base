package com.thread.zxx.ch07;

public class MultiThreadShareData {
	
	
	public static void main(String[] args){
		ShareData shareData=new ShareData();
		for(int i=0;i<2;i++){
			
			new Thread(new Runnable(){

				@Override
				public void run() {
					while(true){
						shareData.increment();
					}
					
				}
				
			}).start();
			
			new Thread(new Runnable(){

				@Override
				public void run() {
					while(true){
						shareData.decrement();
					}
					
				}
				
			}).start();
			
		}
		
		
	}
	
	
	static class ShareData{
		
		 int j=0;
		 
		 public synchronized void increment(){
			 j++;
			 System.out.println(j);
		 }
		 
		 public synchronized void decrement(){
			 j--;
			 System.out.println(j);
		 }
		
		
		
	}

}

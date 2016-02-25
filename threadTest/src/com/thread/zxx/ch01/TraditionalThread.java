package com.thread.zxx.ch01;

public class TraditionalThread {
	
	public static void main(String[] args){
		Thread thread=new Thread(){
		    @Override
		    public void run(){
		    	while(true){
		    		try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		    		System.out.println("Thread id="+Thread.currentThread().getName());
		    	}
		    }
		};
		
		thread.start(); 
		
		
		
		//==========
		
		Thread thread2=new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
			
		});
		thread2.start();
		
		
	}
	
	

}

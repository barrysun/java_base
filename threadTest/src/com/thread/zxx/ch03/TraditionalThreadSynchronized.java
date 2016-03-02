package com.thread.zxx.ch03;

public class TraditionalThreadSynchronized {

	public static void main(String[] args){
		final Outputer outputer=new Outputer();
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					try{
						Thread.sleep(10);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
					outputer.output("zhangxiaoxiang");
				}
				
			}
			
		}).start();
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					try{
						Thread.sleep(10);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
					outputer.output("lihuoming");
				}
				
			}
			
		}).start();
		
	}
	
	static class Outputer{
		public synchronized void  output(String name){
			int len=name.length();
			for(int i=0;i<len;i++){
				System.out.print(name.charAt(i));
			}
			System.out.println();
		}
	}
}

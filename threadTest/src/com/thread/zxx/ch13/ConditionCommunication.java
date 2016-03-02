package com.thread.zxx.ch13;

public class ConditionCommunication {
	
	
	
	static class Business{
		private boolean bShouldSub=true;
		
		public synchronized void sub(int i){
			while(!bShouldSub){
				try{
					this.wait();
				}catch(InterruptedException e){
					
				}
			}
			for( int j=1;j<=10;j++){
				System.out.println("sub thread sequece of "+"j"+",loop of "+i);
			}
			bShouldSub=false;
			this.notify();
		}
		
		public synchronized void main(int i){
			while(bShouldSub){
				try{
					this.wait();
				}catch(InterruptedException e){
					
				}
			}
			for(int j=1;j<=100;j++){
				System.out.println("main thread sequece of "+j+",loop of "+i);
			}
			bShouldSub = true;
			this.notify();
		}
	}

}

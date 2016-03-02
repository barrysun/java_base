package com.thread.zxx.ch04;

public class TraditionalThreadCommunication {

	public static void main(String[] args) {
		final Business business=new Business();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				for(int i=1;i<=50;i++){
					business.sub(i);
				}

			}

		}).start();
		
		for(int i=1;i<=50;i++){
			business.main(i);
		}

	}

}

class Business {
	private boolean isSub=true;
	public synchronized void sub(int i) {
		while(!isSub){
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int j = 1; j <= 100; j++) {
			System.out.println("sub thread sequece of " + j + " ,i " + i);
		}
		isSub=false;
		this.notify();
	}

	public synchronized void main(int i) {
		while(isSub){
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int j = 1; j <= 10; j++) {
			System.out.println("main thread sequece of " + j + " ,i " + i);
		}
		isSub=true;
		this.notify();
	}
}

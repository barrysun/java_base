package com.thread.zxx.ch02;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TraditionalTimerTest {
	
	public static void main(String[] args){
		new Timer().schedule(new TimerTask(){

			@Override
			public void run() {
				System.out.println("bombing!");
				
			}
			
		}, 10);
		
		while(true){
			System.out.println(new Date());
		}
	}

}

package com.nio;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.util.LinkedList;
import java.util.List;

import sun.nio.ch.ThreadPool;

/**
 * 使用线程池来为通道提供服务
 * @author Administrator
 *
 */
public class SelectSocketsThreadPool extends SelectSockets {
	
	private static final int MAX_THREADS=5;
	private ThreadPool pool=new ThreadPool( MAX_THREADS);
	
	public static void main(String[] argv) throws Exception{
		new SelectSocketsThreadPool().go(argv);
	}
	
	/**
	 * 
	 */
	protected void readDataFromSocket(SelectionKey key) throws Exception{
		WorkerThread worker=pool.getWorker();
		            
	}
	
	private class ThreadPool{
		
		List idle=new LinkedList();
		ThreadPool(int poolSize){
			//Fill up the pool with worker threads
			for(int i=0;i<poolSize;i++){
				WorkerThread thread=new WorkerThread(this);
				//Set thread name for deugging. Start it
				thread.setName("Worker"+(i+1));
				thread.start();
				idle.add(thread);
			}
		}
		/**
		 * 
		 */
		WorkerThread getWorker(){
			WorkerThread worker=null;
			synchronized(idle){
				if(idle.size()>0){
					worker=(WorkerThread)idle.remove(0);
				}
			}
			return (worker);
		}
	}
	
	/**
	 * A worker thread class which can drain
	 * @author Administrator
	 *
	 */
	private class WorkerThread extends Thread{
		private ByteBuffer buffer=ByteBuffer.allocate(1024);
		private ThreadPool pool;
		private SelectionKey key;
		WorkerThread(ThreadPool pool){
			this.pool=pool;
		}
		//Loop forever waiting for work to do
		public synchronized void run(){
			
		}
	}
	

}

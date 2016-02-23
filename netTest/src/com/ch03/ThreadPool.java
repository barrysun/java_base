package com.ch03;

import java.util.LinkedList;

/**
 * 
 * 
 * 创建线程池
 * 
 * 对每个客户都分配一个新的工作线程。当工作线程和
 * 客户通信结束，这个线程就被销毁。这种实现方式有以下
 * 不足之处：
 * （1）、服务器创建和销毁工作线程的开销（包括所花费的时间和系统资源）河大。
 * 如果服务器需要与许多客户通信，并且与每个客户的通信时间都很短，那么有可能服务器为客户创建
 * 新线程的开销比实际与客户通信的开销还要大。
 * （2）、除了创建和销毁线程的开销之外，活动的线程也消耗系统资源。每个线程本身都会占用一定的内存
 * （每个线程需要大约1MB内存），如果同时有大量客户连接服务器，就必须创建大量工作线程，他们消耗了大量内存
 * 可能会导致系统的内存空间不足。
 * （3）、如果线程数目固定，并且每个线程都有很长的生命周期，那么线程切换也是相对固定的。不同操作系统有不同的切换周期，
 * 一般在20毫秒左右。这里所说的线程切换时指在java虚拟机，以及底层操作系统的调度下，线程之间转让CPU的使用权。如果
 * 频繁创建和销毁线程，那么将导致频繁地切换线程，因为一个线程被销毁后，必然要把CPU转让给另一个已经就绪的线程，使该线程获得运行机会。在
 * 这种情况下，线程之间的切换不再遵循系统的固定切换周期，切换线程的开销甚至比创建及销毁线程的开销还大。
 * 
 * 线程池为线程生命周期开销问题和系统资源不足问题提供了解决方案。线程池中预先创建了一些工作线程，他们会从工作队列中取出任务，然后
 * 执行该任务。当工程线程执行完一个任务时，就会继续执行工作队列中的下一个任务。线程池具有以下优点：
 * （1）、减少了创建和销毁线程的次数，每个工作线程都可以一直被重用，能执行多个任务。
 * （2）、可以根据系统的承载能力，方便调整线程池中线程的数目，防止因为消耗过量系统资源而导致系统崩溃。
 * 
 * 
 * 
 * @author apple
 *
 */

public class ThreadPool extends ThreadGroup{
	
	public ThreadPool(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	private boolean isClosed=false;//线程池是否关闭
	private LinkedList<Runnable> workQueue;//表示工作队列
	private static int threadPoolID;//表示线程池ID
	private int threadID;//表示工作线程ID
	
	
	public ThreadPool(int poolSize){//poolSize指定线程池中的工作线程数目
		super("ThreadPool-"+(threadPoolID++));
		setDaemon(true);
		workQueue=new LinkedList<Runnable>();//创建工作队列
		for(int i=0;i<poolSize;i++){
			new WorkThread().start();//创建并启动工作线程
		}
	}
	
	/*向工作队列中加入一个新任务，由工作线程执行该任务*/
	public synchronized void execute(Runnable task){
		//线程池被关闭则抛出IllegalStateException异常
		if(isClosed){
			throw new IllegalStateException();
		}
		if(task!=null){
			workQueue.add(task);
			notify();//唤醒正在getTask()方法中等待任务的工作线程
		}
	}
	
	/**
	 * 从工作队列中取出一个任务，工作线程会调用此方法
	 * @return
	 * @throws Exception
	 */
	protected synchronized Runnable getTask() throws Exception{
		while(workQueue.size()==0){
			if(isClosed)return null;
			wait();
		}
		return workQueue.removeFirst();
	}
	
	/**
	 * 关闭线程池
	 */
	public synchronized void close(){
		if(!isClosed){
			isClosed=true;
			workQueue.clear(); //清空工作队列
			interrupt();//中断所有的工作线程，该方法继承自ThreadGroup类
		}
	}
	
	/**
	 * 等待工作线程把所有任务执行完
	 */
	public void join(){
		synchronized(this){
			isClosed=true;
			notifyAll();//唤醒还在getTask方法中等待任务的工作线程
			
		}
		
		Thread[] threads=new Thread[activeCount()];
		//enumerate()方法继承自ThreadGroup类，获得线程组中当前所有活着的工作线程
		int count=enumerate(threads);
		for(int i=0;i<count;i++){//等待所有工作线程运行结束
			try{
				threads[i].join();//等待工作线程运行结束
			}catch(InterruptedException ex){}
		}
	}
	
	/**
	 * 内部类，工作线程
	 * @author apple
	 *
	 */
	private class WorkThread extends Thread{
		public WorkThread(){
			
			//加入到当前ThreadPool线程组中
			super(ThreadPool.this,"WorkThread-"+(threadID++));
		}
		
		public void run(){
			while(!isInterrupted()){//isInterrupted()方法继承自Thread类，判断线程是否被中断。
				Runnable task=null;
				try{
					task=getTask();
				}catch(Exception ex){
					//
					ex.printStackTrace();
				}
				//如果getTask返回null或者线程执行getTask时被中断，则结束此线程
				if(task==null)return;
				try{
					//运行任务，异常再catch代码块中捕获
					task.run();
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
			}
		}
		
	}
	
	

}

package com.nio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Random;

/**
 * 
 * @author Administrator
 *
 *         使用共享锁实现了reader进程，使用独占锁实现了writer进程
 */
public class LockTest {

	private static final int SIZEOF_INT = 4;
	private static final int INDEX_START = 0;
	private static final int INDEX_COUNT = 10;
	private static final int INDEX_SIZE = INDEX_COUNT * SIZEOF_INT;
	
	private ByteBuffer buffer=ByteBuffer.allocate(INDEX_SIZE);
	private IntBuffer indexBuffer=buffer.asIntBuffer();
	
	private Random rand=new Random();
	
	public static void main(String[] argv) throws Exception{
		
	}
	/**
	 * Simulate a series of read-only queries while
	 * holding a shared lock on the index area
	 * @param fc
	 * @throws Exception
	 */
	void doQueries(FileChannel fc) throws Exception{
		while(true){
			System.out.println("trying for shared lock...");
			FileLock lock=fc.lock(INDEX_START,INDEX_SIZE,true);
			int reps=rand.nextInt(60)+20;
			for(int i=0;i<reps;i++){
				int n=rand.nextInt(INDEX_COUNT);
				int position=INDEX_START+(n*SIZEOF_INT);
				buffer.clear();
				fc.read(buffer,position);
				int value=indexBuffer.get(n);
				System.out.println("Index entry "+n+"="+value);
				Thread.sleep(100);
			}
			lock.release();
			System.out.print("<sleeping>");
			Thread.sleep(rand.nextInt(3000)+500);
		}
	}
	
	void doUpdates(FileChannel fc){
		
	}
	

}

package com.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
/**
 * 文件空洞
 * @author Administrator
 * 当磁盘上一个文件的分配空间小于它的文件大小时会出现“文件空洞”.对于内容稀疏的文件，大多数现代文件系统只为实际写入的数据分配
 * 磁盘空间（更准确的说，只为那些写入数据的文件系统页分配空间）。假如数据被写入到文件中非连续的位置上，这就导致文件出现的逻辑上不
 * 包含数据的区域（即“空洞”）。例如，下面的代码可能产生一个如图所示的文件
 *
 */
public class FileHole {
	
	public static void main(String[] args) throws IOException{
		File temp=File.createTempFile("holy", null);
		RandomAccessFile file=new RandomAccessFile(temp,"rw");
		FileChannel channel=file.getChannel();
		//create a working buffer;
		ByteBuffer byteBuffer=ByteBuffer.allocateDirect(100);
		putData(0,byteBuffer,channel);
		putData(5000000,byteBuffer,channel);
		putData(50000,byteBuffer,channel);
		System.out.println("Wrote temp file "+temp.getPath()+",size="+channel.size());
		channel.close();
		file.close();
		
		
	}
	
	private static void putData(int position,ByteBuffer buffer,FileChannel channel)throws IOException{
		String string="*<--- location"+position;
		buffer.clear();
		buffer.put(string.getBytes("US-ASCII"));
		buffer.flip();
		channel.position(position);
		channel.write(buffer);
	}
	
	

}

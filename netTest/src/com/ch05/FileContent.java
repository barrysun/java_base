package com.ch05;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.channels.FileChannel;

public class FileContent implements Content {

	//假定文件的根目录为“root”,该目录应该为与classpath下
	private static File ROOT=new File("root");
	private File file;
	
	
	private FileChannel fileChannel=null;
	private long length=-1;
	private long position=-1;
	
	private String type=null;
	
	
	public FileContent(URI uri){
		
		file=new File(ROOT,uri.getPath().replace('/', File.separatorChar));
	}
	
	/**
	 * 创建FileChannel对象
	 * @throws IOException
	 */
	@Override
	public void prepare() throws IOException {
		if(fileChannel==null){
			fileChannel=new RandomAccessFile(file,"r").getChannel();
		}
		length=fileChannel.size();
		position=0;
		
	}

	/**
	 * 发送正文，如果发送完毕就返回false，否则返回true
	 * @param cio
	 * @return
	 * @throws IOException
	 */
	@Override
	public boolean send(ChannelIO channelIO) throws IOException {
		if(fileChannel==null){
			throw new IllegalStateException();
		}
		if(position<0){
			throw new IllegalStateException();
		}
		
		if(position>=length){
			return false; //如果发送完毕，及狯false
		}
		position+=channelIO.transferTo(fileChannel,position,length-position);
		
		return (position<length);
	}

	@Override
	public void release() throws IOException {
		if(fileChannel!=null){
			fileChannel.close();
			fileChannel=null;
		}
		
	}

	/**
	 * 确定文件类型
	 */
	@Override
	public String type() {
		if(type!=null)return type;
		String nm=file.getName();
		if(nm.endsWith(".html")|| nm.endsWith(".htm")){
			type="text/html;charset=ios-8859-1";
		}else if(nm.indexOf(".")<0 || nm.endsWith(".txt")){
			type="text/plain;charset=ios8859-1";
		}else {
			type="application/octet-stream";//二进制文件
		}
		return type;
	}

	@Override
	public long length() {
		// TODO Auto-generated method stub
		return length;
	}

}

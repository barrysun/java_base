package com.ch05;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.channels.FileChannel;

public class FileContent implements Content {

	//�ٶ��ļ��ĸ�Ŀ¼Ϊ��root��,��Ŀ¼Ӧ��Ϊ��classpath��
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
	 * ����FileChannel����
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
	 * �������ģ����������Ͼͷ���false�����򷵻�true
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
			return false; //���������ϣ�����false
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
	 * ȷ���ļ�����
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
			type="application/octet-stream";//�������ļ�
		}
		return type;
	}

	@Override
	public long length() {
		// TODO Auto-generated method stub
		return length;
	}

}

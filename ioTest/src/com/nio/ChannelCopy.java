package com.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class ChannelCopy {
	
	/**
	 * This code copies data from stdin to stdout.Like the 'cat'
	 * command ,but without any useful options
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException{
		ReadableByteChannel source=Channels.newChannel(System.in);
		WritableByteChannel dest=Channels.newChannel(System.out);
		channelCopy1(source,dest);
		//
		source.close();
		dest.close();
	}
	
	/**
	 * Channel copy method 1. This method copies data from the src
	 * channel and writes it to the dest channel until EOF on src.
	 * This implementation 
	 * 
	 * 
	 * @param src
	 * @param dest
	 * @throws IOException
	 */
	public static void channelCopy1(ReadableByteChannel src,WritableByteChannel dest) throws IOException{
		ByteBuffer buffer=ByteBuffer.allocateDirect(16 * 1024);
		while(src.read(buffer)!=-1){
			//Prepare the buffer to be drained
			buffer.flip();
			//Write to the channel; may block
			dest.write(buffer);
			//if partial transfer,shift remainder down
			//
			buffer.compact();
		}
		//EOF will leave buffer in fill state
		buffer.flip();
		//
		while(buffer.hasRemaining()){
			dest.write(buffer);
		}
	}
	/**
	 * 
	 * @param src
	 * @param dest
	 * @throws IOException
	 */
	private static void channelCopy2(ReadableByteChannel src,WritableByteChannel dest) throws IOException{
		
		ByteBuffer buffer=ByteBuffer.allocateDirect(16 * 1024);
		while(src.read(buffer) !=-1){
			//Prepare the buffer to be drained
			buffer.flip();
			//Make sure that the buffer was fully drained
			while(buffer.hasRemaining()){
				dest.write(buffer);
			}
			//Make the buffer empty,ready for filling
			buffer.clear();
		}
	}

}

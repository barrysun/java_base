package com.nio;

import java.nio.CharBuffer;

/**
 * 填充和释放缓冲区
 * @author Administrator
 *
 */
public class BufferFillDrain {
	
	
	
	
	public static void main(String[] argv) throws Exception{
		CharBuffer buffer=CharBuffer.allocate(100);
		while(fillBuffer(buffer)){
			
		}
	}
	
    String string=strings[index++];
	
	private static boolean fillBuffer(CharBuffer buffer) throws Exception{
		
		if(index>=strings.length){
			return false;
		}
		String string=strings[index++];
		return false;
	}

      private  static int index=0;
      
      private static String[] strings={};


}

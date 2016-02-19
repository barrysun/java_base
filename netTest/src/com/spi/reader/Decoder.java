package com.spi.reader;

public interface Decoder {
	
	boolean isEncodingSupported(String encodingName);
	String[] getContent(String input);

}

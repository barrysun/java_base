package com.spi.csv;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import com.spi.reader.Decoder;

public class CSVDecoder implements Decoder {

	@Override
	public boolean isEncodingSupported(String encodingName) {
		return encodingName.equalsIgnoreCase("text/csv");
	}

	@Override
	public String[] getContent(String input) {
		List<String> values=new LinkedList<String>();
		StringTokenizer parser=new StringTokenizer(input,",");
		
		while(parser.hasMoreTokens()){
			values.add(parser.nextToken());
		}
		 return  (String[])values.toArray(new String[values.size()]);  
	}
	
	

}

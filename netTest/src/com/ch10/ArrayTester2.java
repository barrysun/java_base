package com.ch10;

import java.lang.reflect.Array;

public class ArrayTester2 {
	
	public static void main(String args[]){
		int dims[] = new int[]{5,10,15};
		Object array=Array.newInstance(Integer.TYPE, dims);
		
	}

}

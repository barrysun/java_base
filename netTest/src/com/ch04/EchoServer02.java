package com.ch04;

import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.charset.Charset;

/**
 * ����������ģʽ
 * @author apple
 *
 */
public class EchoServer02 {

	private Selector selector = null;
	private ServerSocketChannel serverSocketChannel=null;
	
	private int port=8000;
	
	private Charset charset=Charset.forName("GBK");
	
}

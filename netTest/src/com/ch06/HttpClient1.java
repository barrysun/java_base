package com.ch06;

import java.io.IOException;
import java.net.URL;

/**
 * Java对客户程序的通信过程进行了抽象，提供了通用的协议处理框架
 * 这个框架封装了Socket，主要包括以下几个类。
 * URL类：统一资源定位器（Uniform Resource Locator），表示客户程序要访问的远程资源
 * URLConnection类：表示客户程序与远程服务器的连接。客户程序可以从URLConnection类中获得数据输入流和输出流。
 * URLStreamHandler类：协议处理器，主要负责创建与协议相关的URLConnection对象。
 * ContentHandler类：内容处理器，负责解析服务器发送的数据，把它转换为相应的java对象。
 * 
 * 以上类都位于java.net包中，除了URL类为具体类以外，其余3个类都是抽象类，对于一种具体的协议，需要
 * 创建相应的URLConnection、URIStreamHandler和ContentHandler具体子类。
 * 
 * @author apple
 *
 */

public class HttpClient1 {
	
	public static void main(String[] args)throws IOException{
		URL url=new URL("");
	}

}

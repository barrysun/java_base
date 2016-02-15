package com.ch11;

public class info {
	
	/**
	 * 一般说来，远程对象都分布在服务器端，提供各种通用的服务。客户端则
	 * 访问服务器端的远程对象，请求特定的服务。如何实现这种分布式的对象模型呢？显然，
	 * 不管采取何种方式，对象模型的实现系统都应该具备以下功能：
	 * （1）、把分布在不同节点上的对象之间发送的消息转换为字节序列，这一过程称为编组（marshalling）。
	 * （2）、通过套接字建立连接并且发送编组后的消息，即字节序列。
	 * （3）、处理网络连接或传输消息时出现的各种故障。
	 * （4）、为分布在不同节点上的对象提供分布式垃圾收集机制。
	 * （5）、为远程方法调用提供安全检查机制。
	 * （6）、服务器端运用多线程或非阻塞通信机制，确保远程对象具有很好的并发性能，能同时被多个客户访问。
	 * （7）、创建与特定问题领域相关的各种本地对象和远程对象。
	 * 
	 * 由此可见，从头开发一个完善的分布式的软件系统很复杂，既要处理套接字连接、编组、分布式垃圾收集、安全检查和并发性等问题，
	 * 还要开发与实际问题领域相关的各种本地对象和远程对象。幸运的是，目前已经有一些现成的、成熟的分布式对象模型的框架：
	 * （1） RMI
	 * （2）CORBA
	 * （3）SOAP
	 * 
	 * 
	 * RMI框架封装了用Socket通信的细节，使得应用程序可以像调用本地方法一样，
	 * 去调用远程对象的方法。RMI框架在实现中运用了一下机制。
	 * （1）、分布式垃圾收集机制：对于一个远程对象，只有当它没有受到任何本地引用及远程引用时，才会结束生命周期。
	 * （2）、用远程接口来公布远程对象提供的服务。客户程序通过远程接口来调用远程对象的方法，这符合面向对象开发中的“公开接口，封装实现”的思想。
	 * （3）、客户端持有远程对象的引用，实际上是持有远程对象的存根对象的引用，存根对象充当远程对象的代理，两者具有同样的远程接口。
	 * （4）、运用动态类加载机制，当客户端访问一个远程对象时，如果客户端不存在与远程对象相关的类，RMI框架会从java.rmi.server.codebase系统属性指定
	 * 的位置加载他们。
	 * （5）、
	 * 
	 * 
	 * 
	 */

}

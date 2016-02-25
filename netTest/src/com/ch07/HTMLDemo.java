package com.ch07;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class HTMLDemo extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel jLabel;
	private JButton jButton;
	
	public HTMLDemo(String title){
		super(title);
		jLabel=new JLabel("<html></html>");
		//设定go.jpg文件与HTMLDemo.class文件位置同一个目录下
		jButton=new JButton("");
		//设置鼠标移动到该Button时的提示信息
		
		
	}
	
	
	
	
	

}

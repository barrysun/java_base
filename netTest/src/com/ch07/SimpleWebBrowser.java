package com.ch07;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.EditorKit;
import javax.swing.text.html.FormSubmitEvent;
import javax.swing.text.html.HTMLEditorKit;

public class SimpleWebBrowser extends JFrame implements HyperlinkListener,ActionListener {

	private JTextField jtf=new JTextField(40);
	private JEditorPane jep=new JEditorPane();
	private String initialPage="http://javathinker.org/helloapp/index.html";
	
	public SimpleWebBrowser(String title){
		super(title);
		
		jtf.setText(initialPage);
		jtf.addActionListener(this);
		jep.setEditable(false);
		jep.addHyperlinkListener(this);
		
		//����editorKit���Ա��������õ��¼�
		jep.addPropertyChangeListener("editorKit", new PropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				System.out.println("set editorKit");
				
				EditorKit kit=jep.getEditorKit();
				if(kit.getClass()==HTMLEditorKit.class){
					((HTMLEditorKit)kit).setAutoFormSubmission(false);//���ֹ���ʽ�ύ��
				}
			}
			
		});
		
		try{
			jep.setPage(initialPage);
		}catch(IOException e){
			showError(initialPage);
		}
		
		JScrollPane scrollPane=new JScrollPane(jep);
		Container container=getContentPane();
		container.add(jtf, BorderLayout.NORTH);
		container.add(scrollPane,BorderLayout.CENTER);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		
	}
	
	public void showError(String url){
		jep.setContentType("text/html");
		jep.setText("");
	}
	
	public static void main(String[] args){
		SimpleWebBrowser browser=new SimpleWebBrowser("");
	}
	
	
			
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			jep.setPage(jtf.getText());
		}catch(Exception ex){
			showError(jtf.getText());
		}
		
	}
	
	/**
	 * �����û�ѡ�񳬼����ӻ����ύ���¼�
	 */

	@Override
	public void hyperlinkUpdate(HyperlinkEvent evt) {
		try{
			
			if(evt.getClass()==FormSubmitEvent.class){
				//�����ύ���¼�
				FormSubmitEvent fevt=(FormSubmitEvent)evt;
				URL url=fevt.getURL();//���URL
				String method=fevt.getMethod().toString();//��ȡ����ʽ
				String data=fevt.getData();//��ñ�����
				
				if(method.equals("GET")){
					//���ΪGET����ʽ
					jep.setPage(url.toString()+"?"+data);
					jtf.setText(url.toString()+"?"+data);//���ı�����Ϊ�û�ѡ��ĳ�������
				}else if(method.equals("POST")){
					//���ΪPOST����ʽ
					URLConnection uc=url.openConnection();
					//����HTTP��Ӧ����
					uc.setDoOutput(true);
					OutputStreamWriter out=new OutputStreamWriter(uc.getOutputStream());
					out.write(data);
					out.close();
					
					//����HTTP��Ӧ����
					InputStream in=uc.getInputStream();
					ByteArrayOutputStream buffer=new ByteArrayOutputStream();
					byte[] buff=new byte[1024];
					int len=-1;
					
					while((len=in.read(buff))!=-1){
						buffer.write(buff,0,len);
					}
					in.close();
					
					jep.setText(new String(buffer.toByteArray()));
					jtf.setText(url.toString());//���ı�����Ϊ�û�ѡ��ĳ�������
					
				}
				System.out.println(fevt.getData()+"|"+fevt.getMethod()+"|"+fevt.getURL());
			}else if(evt.getEventType()==HyperlinkEvent.EventType.ACTIVATED){
				//�����û�ѡ�񳬼������¼�
				jep.setPage(evt.getURL());
				jtf.setText(evt.getURL().toString());//���ı�����Ϊ�û�ѡ��ĳ�������
			}
			
		}catch(Exception e){
			showError(evt.getURL().toString());
		}
		
	}
	
	

}

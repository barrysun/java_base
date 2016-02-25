package com.ch06;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HttpClient3 {
	
	public static void main(String[] args){
		JFrame frame=new PostTestFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}

class PostTestFrame extends JFrame{
	//������HTTP�������ģ��Լ�����HTTP��Ӧ����
	public static String doPost(String urlString,Map<String,String> nameValuePairs) throws IOException{
		URL url=new URL(urlString);
		URLConnection connection=url.openConnection();
		connection.setDoOutput(true);//������������
		
		//����HTTP��������
		PrintWriter out=new PrintWriter(connection.getOutputStream());
		boolean first=true;
		for(Map.Entry<String, String> pair:nameValuePairs.entrySet()){
			if(first)first=true;
			else out.print('&');
			String name=pair.getKey();
			String value=pair.getValue();
			
			out.print(name);
			out.print('=');
			out.print(URLEncoder.encode(value,"GV2312")); //�������Ĳ���GB2312����
		}
		out.close();
		
		//����HTTP��Ӧ����
		InputStream in=connection.getInputStream();//��ȡ��Ӧ����
		ByteArrayOutputStream buffer=new ByteArrayOutputStream();
		byte[] buff=new byte[1024];
		int len=-1;
		
		while((len=in.read(buff))!=-1){
			buffer.write(buff,0,len);
		}
		in.close();
		return new String(buffer.toByteArray());//���ֽ�����ת��Ϊ�ַ���
	}
	
	
	public PostTestFrame(){
		//setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		setTitle("�鼮ϵ��");
		JPanel northPanel=new JPanel();
		add(northPanel,BorderLayout.NORTH);
		
		final JComboBox combo=new JComboBox();
		
		for(int i=0;i<books.length;i++){
			combo.addItem(books[i]);
		}
		northPanel.add(combo);
		
		final JTextArea result=new JTextArea();
		add(new JScrollPane(result));
		
		JButton getButton=new JButton("�鿴");
		northPanel.add(getButton);
		getButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) {


				new Thread(new Runnable(){

					@Override
					public void run() {
						final String SERVER_URL="http://www.javathinker.org/aboutBook.jsp";
						result.setText("");
						Map<String,String> post=new HashMap<String,String>();
						post.put("title", books[combo.getSelectedIndex()]);
						try{
							result.setText(doPost(SERVER_URL,post));
						}catch(Exception e){
							result.setText(""+e);
						}
					}
				}).start();
				
			}
			
		});
		
		
	}
	
	
	private static String[] books={"Java���������","Tomcat��JavaWeb�����������","��ͨStruts:����MVC��JavaWeb����뿪��"
			,"��ͨHibernate:Java����־û��������","Java2��֤����ָ�����������"};
	
	public static final int DEFAULT_WIDTH=400;
	public static final int DEFAULT_HEIGHT=300;
	
}

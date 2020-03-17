package Server;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ServerChat extends Thread{
	
	private Socket withClient=null; 
	private InputStream reMsg= null;
	private OutputStream sendMsg= null;
	private String id= null;
	
	
	ServerChat(Socket c){
		this.withClient=c;
		//streamSet();
	}
	
	@Override
	public void run() {
		//ServerChat(Socket c){ �� �ִ�  streamSet();��  ��Ƽ������ �������  override ���� ������
		streamSet();
		receive();
		send();
		
	}
	
	private void receive() {
		// recevie() �� ������ ������ ó��
		new Thread(new Runnable() {
			
			@Override
			public void run() { // �������� ������ �޴±�ɸ�
				// TODO Auto-generated method stub
				try {
					System.out.println("receive start!");
					while(true) {
					reMsg= withClient.getInputStream();
					byte[] reBuffer= new byte[100];
					reMsg.read(reBuffer);
					String msg= new String(reBuffer);
					msg= msg.trim();
					if(msg.indexOf("/bye")==0) {
						endChat();
						break;
					}else {
						System.out.println("["+id+"]"+msg);
					}
						
					}
				
				}catch(Exception e) {
					System.out.println("receive end");
					return;
				
				}
				
			}

		
		}).start();
	
		
	}
	private void endChat() {
		// TODO Auto-generated method stub
		try {
			System.out.println("��!");
			withClient.close();
			reMsg.close();
			sendMsg.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void send() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					System.out.println("send start!");
					Scanner in= new Scanner(System.in);
					while(true){
					String reMsg=in.nextLine();
					sendMsg= withClient.getOutputStream();
					sendMsg.write(reMsg.getBytes());
					System.out.println("����");
					}
				}catch(Exception e) {
					System.out.println("send end");
					return;
				}
			}
		}).start();
		
		
	}



	private void streamSet() {
		try {
			reMsg= withClient.getInputStream();
			byte[] reBuffer= new byte[100];
			reMsg.read(reBuffer);
			id= new String(reBuffer);
			id= id.trim(); //  Ʈ�� ���� ����
			
			InetAddress c_ip= withClient.getInetAddress();
			//withClient ��� ��Ĺ���� getInetAddress �� �޾Ƽ� c_ip�� ����
			String ip=c_ip.getHostAddress();
			//c_ip�� string���� ����
			System.out.println(id+"�� �α��� �ϼ̽��ϴ�");
			
			
			
	// server ���� client ���� ���� ���� ��ٰ�  ������
			String reMsg="�������� �Ǿ����ϴ�";
			sendMsg= withClient.getOutputStream();
			sendMsg.write(reMsg.getBytes());
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}

}
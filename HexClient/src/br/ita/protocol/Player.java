package br.ita.protocol;

import static br.ita.tools.Shortcut.println;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Player implements Runnable {

	private int port;
	
	private String name;
	
	private String host;
	
	private int games = 15;

	public Player(String host, int port, String name) {
		this.port = port;
		this.name = name;
		this.host = host;
	}
	
	public void run() {
	
		Socket client        = null;
		DataInputStream bis  = null;
		DataOutputStream bos = null;
		
		try {
		
			do {
				try {
					client = new Socket(this.host, this.port);
				} catch(Exception e) {
					e.printStackTrace();
				}
				
			} while (client.isClosed());
			
			bis = new DataInputStream(client.getInputStream());
			bos = new DataOutputStream(client.getOutputStream());
	
			communication(bis, bos);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			/* Close connections */
			try {
				bis.close();
				bos.close();
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void communication(DataInputStream bis, DataOutputStream bos) throws IOException {

		/*
		 * Sent HELLO
		 */
		bos.writeUTF("HELLO" + name);
		println("CLI> HELLO" + name);

		/*
		 * Receive OK
		 */
		while (bis.available() < 0);
		println("SER> " + bis.readUTF());
		
		for (int i = 0; i < games; i++) {
		
			while (true) {
	
				String message;
				/*
				 * Waiting for GO or END
				 */
				while (bis.available() < 0);
				println("SER> " + (message = bis.readUTF()));
	
				if (message.matches("END.*"))					
					break;
					
				if (message.matches("GO.*")) {
	
					/*
					 * Send OK
					 */
					bos.writeUTF("OK");
					println("CLI> OK");
	
					/*
					 * Send move
					 */
					bos.writeUTF("AA");
					println("CLI> AA");
	
					/*
					 * Receive move
					 */
					while (bis.available() < 0);
					println("SER> " + bis.readUTF());
	
					/*
					 * Send move
					 */
					bos.writeUTF("OK");
					println("CLI> OK");
				}
			}
		}
	}
}

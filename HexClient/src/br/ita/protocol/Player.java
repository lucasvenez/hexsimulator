package br.ita.protocol;

import static br.ita.tools.Shortcut.println;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Player implements Runnable {

	private static final ArrayList<String> moves = new ArrayList<String>();
	private int port;
	private String name;

	public Player(int port, String name) {
		this.port = port;
		this.name = name;
	}
	
	public void run() {
	
		Socket client        = null;
		DataInputStream bis  = null;
		DataOutputStream bos = null;
		
		try {
		
			do {
				try {
					client = new Socket("localhost", this.port);
				} catch(Exception e) {
					e.printStackTrace();
				}
				
			} while (client.isClosed());
			
			bis = new DataInputStream(client.getInputStream());
			bos = new DataOutputStream(client.getOutputStream());
	
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
	
			while (true) {
	
				String message;
				/*
				 * Waiting for GO or END
				 */
				while (bis.available() < 0);
				println("SER> " + (message = bis.readUTF()));
				
				if (!moves.contains(message))
					moves.add(message);
				else {
					System.out.println("<<<<"+message+">>>>>");
					System.exit(19);
				}
	
				if (message.matches("END.")) {
	
					if (message.charAt(message.length()-1) == 'L')
						println("\nSER> YOU LOSE");
					else 
						println("\nSER> YOU WIN");
					
					break;
					
	
				} else if (message.matches("GO.*")) {
	
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
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			/* Close connections */
			
		}
	}
}

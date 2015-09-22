package br.ita.protocol;

import static br.ita.tools.Shortcut.println;
import static br.ita.tools.Tools.intHexMoveToString;
import static br.ita.tools.Tools.stringHexMoveToInt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import br.ita.mcts.HexTreeSearch;
import br.ita.tools.SaveObject;
import br.ita.tools.Tools;

public class Player implements Runnable {

	private int    port;
	
	private String name;
	
	private String host;
	
	private int    games = 15;
	
	private int    initialPlayer;
	
	private HexTreeSearch tree;
	
	public Player(int initialPlayer, String host, int port, String name, int games) {
		this.initialPlayer = initialPlayer;
		this.port          = port;
		this.name          = name;
		this.host          = host;
		this.games         = games;
		
		try {
			this.tree = SaveObject.serializeDataIn();
		} catch (Exception e) {
			System.err.println("New tree");
			this.tree = new HexTreeSearch();
		}
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
			
			/* 
			 * Close connections 
			 */
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
		sendMessage("HELLO" + name, bos);

		/*
		 * Receive OK
		 */
		receiveMessage(bis);
		
		/*
		 * For each game
		 */
		for (int i = 0; i < games; i++) {
		
			/*
			 * (i + initialPlayer) % 2 == 0 means player 1 and
			 * (i + initialPlayer) % 2 == 1 means player 2.
			 */
			tree.setPlayer((i + initialPlayer) % 2 == 0);
			
			while (true) {
	
				/*
				 * Waiting for GO or END
				 */
				String message = receiveMessage(bis);
	
				/*
				 * Game is end?
				 * If yes restart decision tree, otherwise keep playing.
				 */
				if (message.matches("END.*")) {
					
					char w = message.charAt(3);
					
					tree.restart(w == 'W' ? null : Tools.stringHexMoveToInt(message.substring(4,6), 11));
					
					break;
					
				} else if (message.matches("GO.*")) {
	
					Integer reply = null;
					
					/*
					 * Getting enemy move
					 */
					if (message.length() > 2)
						reply = stringHexMoveToInt(message.substring(2, 4), 11);
					
					/*
					 * Send OK
					 */
					sendMessage("OK", bos);
	
					/*
					 * Sending move
					 */
					String move = intHexMoveToString(tree.getNextMove(reply), 11);
					
					sendMessage(move, bos);
	
					/*
					 * Receiving move
					 */
					receiveMessage(bis);
	
					/*
					 * Sending OK
					 */
					sendMessage("OK", bos);
					
				}
			}
		}
		
		if (this.initialPlayer == 1)
			SaveObject.serializeDataOut(tree);
	}
	
	public void sendMessage(String message, DataOutputStream channel) {
		
		try {
			
			channel.writeUTF(message);
			
			println("CLI> " + message);
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
	}
	
	public String receiveMessage(DataInputStream channel) {
		
		String response = "";
		
		try {
		
			while (channel.available() < 0);
			
			response = channel.readUTF();
			
			println("SER> " + response);
		
		} catch(Exception e) {
			
			e.printStackTrace();
		
		}
		
		return response;
	}
}

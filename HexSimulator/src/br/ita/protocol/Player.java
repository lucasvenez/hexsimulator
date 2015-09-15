package br.ita.protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import br.ita.hexgame.HexCommand;
import br.ita.hexgame.HexStatus;

import static br.ita.tools.Shortcut.println;

public class Player implements Runnable {

	private int playerNumber;
	
	private int port;
	
	public Player(int playerNumber, int port) {
		this.playerNumber = playerNumber;
		this.port = port;
	}
	
	@Override
	public void run() {
		
		ServerSocket server = null;
		Socket client = null;
		
		try {
			/*
			 * Instance server at a specific port
			 */
			server = new ServerSocket(port);
				
			/*
			 * Accepting connection
			 */
			client = server.accept();
			
			/*
			 * Getting communication channels
			 */
			DataInputStream bis = 
					new DataInputStream(client.getInputStream());
			
			DataOutputStream bos = 
					new DataOutputStream(client.getOutputStream());
			
			/*
			 * Expecting HELLO 
			 */
			while (!bis.readUTF().equals("HELLO"));
			
			/*
			 * Answering OK
			 */
			bos.writeUTF("OK");
			
			while (true) {
			
				while (playerNumber != HexCommand.getCurrentPlayer());
				
				if (HexStatus.getWinner() > 0) {
					bos.writeUTF("ENDL");
					break;
				} else {
					
					/*
					 * Sending GO + last move 
					 */
					bos.writeUTF("GO" + HexCommand.getLastMove());
					
					/*
					 * Expecting OK 
					 */
					while (!bis.readUTF().equals("OK"));
					
					/*
					 * Expecting next move with a time of 5 seconds and 200 milliseconds
					 */
					boolean timeout = false; {
					
						long itime = System.currentTimeMillis();
					
						while (bis.available() < 0) {
							
							if (System.currentTimeMillis() - itime >= 5200) {
								timeout = true;
								break;
							}
						}
					}
					
					/*
					 * Executing move
					 */
					boolean winner = false;
					
					if (timeout)
						winner = HexCommand.randomMove();
					else {
						
						String message = bis.readUTF();
						winner = HexCommand.play(this.playerNumber, message);
					}
					
					/* confirm move */ {
						String message = (timeout ? "TO" : "") + HexCommand.getLastMove();
						bos.writeUTF(message);
					}
					
					println(HexCommand.getLastMove());
					
					/*
					 * Expecting OK
					 */
					while (!bis.readUTF().equals("OK"));
					
					if (winner) {
						bos.writeUTF("ENDW");
						HexStatus.setWinner(this.playerNumber);
						HexCommand.changePlayer();
						break;
					} else {
						HexCommand.changePlayer();
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				client.close();
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

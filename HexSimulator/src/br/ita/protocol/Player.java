package br.ita.protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import br.ita.hexgame.HexCommand;
import br.ita.hexgame.HexStatus;

public class Player implements Runnable {

	private int playerNumber;
	
	private int port;
	
	private ServerSocket server = null;
	
	private int numberOfGames = 0;
	
	public Player(int playerNumber, int port, int numberOfGames) {
		this.playerNumber  = playerNumber;
		this.port          = port;
		this.numberOfGames = numberOfGames;
	}
	
	@Override
	public void run() {
		
		Socket client        = null;
		DataInputStream bis  = null;
		DataOutputStream bos = null;

		/*
		 * Instance server at a specific port
		 */
		try {
			server = new ServerSocket(port);
			
				
				try {
				
					/*
					 * Accepting connection
					 */
					client = server.accept();
					
					/*
					 * Getting communication channels
					 */
					bis = new DataInputStream(client.getInputStream());
					bos = new DataOutputStream(client.getOutputStream());
					
					/*
					 * Communication process
					 */
					communication(bis, bos);
				
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						bis.close();
						bos.close();
						client.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void communication(DataInputStream bis, DataOutputStream bos) throws IOException {
		
		/*
		 * Expecting HELLO 
		 */
		while (!bis.readUTF().equals("HELLO"));
		
		/*
		 * Answering OK
		 */ 
		bos.writeUTF("OK");
		
		for (int i = 0; i < numberOfGames; i++) {
		
			while (true) {
			
				while (playerNumber != HexCommand.getCurrentPlayer());
				
				if (HexStatus.getWinner() > 0) {
					
					bos.writeUTF("ENDL" + HexCommand.getLastMove());
					
					HexStatus.setWinner(0);
					
					HexCommand.clearLastMove();
					
					HexCommand.changeStartPlayer();
					
					break;
					
				} else {
					
					/*
					 * Sending GO + last move 
					 */
					bos.writeUTF("GO" + HexCommand.getLastMove());

					boolean timeout = false; {
						
						long itime = System.currentTimeMillis();
					
						/*
						 * Expecting OK 
						 */
						while (!bis.readUTF().equals("OK"));
						
						/*
						 * Expecting next move with a time of 5 seconds and 200 milliseconds
						 */
					
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
						winner = HexCommand.randomMove(this.playerNumber);
					else {
						
						String message = bis.readUTF();
						winner = HexCommand.play(this.playerNumber, message);
						
					}
					
					/* confirm move */ {
						String message = (timeout ? "TO" : "") + HexCommand.getLastMove();
						bos.writeUTF(message);
					}
					
					/*
					 * Expecting OK
					 */
					while (!bis.readUTF().equals("OK"));
					
					if (winner) {

						bos.writeUTF("ENDW");

						HexStatus.setWinner(this.playerNumber);

						HexStatus.board[playerNumber-1]++;
						
						HexStatus.clearMadeMoves();
						
						HexStatus.popMove(- 1 - playerNumber);
						
						HexCommand.changePlayer();

						break;
						
					} else {
						
						HexCommand.changePlayer();
						
					}
				}
			}
		}
	}
}

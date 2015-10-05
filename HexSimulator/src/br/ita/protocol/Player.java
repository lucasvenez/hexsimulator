package br.ita.protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import br.ita.hexgame.HexCommand;
import br.ita.hexgame.HexStatus;

public class Player implements Runnable {

	/**
	 * 
	 */
	private int playerNumber;
	
	/**
	 * 
	 */
	private int port;
	
	/**
	 * 
	 */
	private ServerSocket server = null;
	
	/**
	 * 
	 */
	private int numberOfGames = 0;
	
	/**
	 * 
	 * @param playerNumber
	 * @param port
	 * @param numberOfGames
	 */
	public Player(int playerNumber, int port, int numberOfGames) {
		this.playerNumber  = playerNumber;
		this.port          = port;
		this.numberOfGames = numberOfGames;
	}
	
	/**
	 * 
	 */
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
	
	/**
	 * 
	 * @param bis
	 * @param bos
	 * @throws IOException
	 */
	public void communication(DataInputStream bis, DataOutputStream bos) throws IOException {
		
		/*
		 * Expecting HELLO 
		 */
		receiveMessage("HELLO.*", bis);
		
		/*
		 * Answering OK
		 */ 
		sendMessage("OK", bos);
		
		/*
		 * For each game play
		 */
		for (int i = 0; i < numberOfGames; i++) {
		
			while (true) {
			
				/*
				 * Waiting your time
				 */
				while (playerNumber != HexCommand.getCurrentPlayer());
				
				/*
				 * Game is end?
				 */
				if (HexStatus.getWinner() > 0) {
					
					sendMessage("ENDL" + HexCommand.getLastMove(), bos);
					
					HexStatus.setWinner(0);
					
					HexCommand.clearLastMove();
					
					HexCommand.changeStartPlayer();
					
					break;
					
				} else {
					
					/*
					 * Sending GO + last move 
					 */
					sendMessage("GO" + HexCommand.getLastMove(), bos);

					/*
					 * 
					 */
					boolean winner = receiveMoveWithTimeOut(bis, bos, 5200);
					
					/*
					 * Expecting OK
					 */
					receiveMessage("OK", bis);
					
					if (winner) {

						sendMessage("ENDW", bos);

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
	
	private boolean receiveMoveWithTimeOut(DataInputStream bis, DataOutputStream bos, long time) {
		
		/*
		 * Executing move
		 */
		boolean winner = false;
		
		try {

			/*
			 * Counting time for receiving a answer.
			 */
			boolean timeout = false;
			
			long itime = System.currentTimeMillis();
		
			/*
			 * Expecting OK 
			 */
			receiveMessage("OK", bis);
			
			/*
			 * Expecting next move with a time of 5 seconds and 200 milliseconds
			 */
			while (bis.available() < 0) {
				
				if (System.currentTimeMillis() - itime >= time) {
					timeout = true;
					break;
				}
			}
			
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
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return winner;
	}

	/**
	 * 
	 * @param message
	 * @param channel
	 */
	public void sendMessage(String message, DataOutputStream channel) {
		try {
			channel.writeUTF(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param message
	 * @param channel
	 */
	public void receiveMessage(String message, DataInputStream channel) {
		try {
			while (!channel.readUTF().matches(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

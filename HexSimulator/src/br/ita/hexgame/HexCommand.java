package br.ita.hexgame;

import java.util.Random;

public class HexCommand {

	private static int currentPlayer = 1;
	
	private static String lastMove = "";
	
	private static final int[] PLAYERS = new int[] {2, 1};
	
	/**
	 * It makes a movement for a player.
	 * @param player is the player code for validation the move.
	 * @param position is the position of the move.
	 * @return true if the move is valid and false otherwise.
	 */
	public static synchronized boolean play(int player, String position) {
		
		boolean isWinner = false;
		
		if (player == currentPlayer)
			isWinner = play(position);
		
		return isWinner;
	}
	
	private static synchronized boolean play(String position) {

		boolean isWinner = false;
		
		if (Move.list.contains(position)) {
			
			Move.list.remove(position);
			
			isWinner = confirmMove(position);			
			
		} else {
			
			isWinner = randomMove();
			
		}
		
		return isWinner;
	}
	
	private static boolean confirmMove(String position) {
		
		int i = Integer.parseInt(String.valueOf(position.charAt(0)), 16),
			j = Integer.parseInt(String.valueOf(position.charAt(1)), 16);
			
		/*
		 * Adding last movement to the table
		 */
		HexStatus.addMove(
			HexCommand.currentPlayer, 
			HexStatus.getTableSize() * j + i); // converting to unique unity
			
		lastMove = position;

		boolean hasWinner = HexWinner.hasWinner(
			currentPlayer, 
			HexStatus.getTableSize(), 
			HexStatus.getNumberOfMoves(), 
			HexStatus.getMadeMove(currentPlayer));
		
		return hasWinner;
	}
	
	public static synchronized boolean randomMove() {
		
		boolean isWinner = false;
		
		int m = (new Random()).nextInt(Move.list.size());
	
		isWinner = confirmMove(Move.list.get(m));
		
		return isWinner;
	}
	
	/**
	 * It returns the last move of the game.
	 * @return the last move of the game.
	 */
	public static synchronized String getLastMove() {
		return lastMove;
	}
	
	public static synchronized int getCurrentPlayer() {
		return currentPlayer;
	}
	
	public static synchronized void changePlayer() {
		/*
		 * Changing player
		 * when there is not winner
		 */
		currentPlayer = PLAYERS[HexCommand.currentPlayer - 1];
	}
}

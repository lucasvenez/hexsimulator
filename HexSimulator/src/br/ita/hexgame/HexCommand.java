package br.ita.hexgame;

import java.util.Random;

import br.ita.tools.DateManagement;
import br.ita.tools.FileManagement;

import static java.lang.Math.abs;

public class HexCommand {

	private static StringBuilder log = new StringBuilder();
	
	private static int startPlayer = 1;
	
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
			isWinner = checkMoveExistence(player, position);
		
		return isWinner;
	}
	
	private static synchronized boolean checkMoveExistence(int player, String position) {

		boolean isWinner = false;
		
		if (Move.list.contains(position)) {
			
			Move.list.remove(position);
			
			isWinner = confirmMove(player, position);			
			
		} else {
			isWinner = randomMove(player);
		}
		
		return isWinner;
	}
	
	private static synchronized boolean confirmMove(int player, String position) {
		
		int i = Integer.parseInt(String.valueOf(position.charAt(0)), 16),
			j = Integer.parseInt(String.valueOf(position.charAt(1)), 16);
		
		HexStatus.popMove(HexStatus.getTableSize() * i + j);
		
		/*
		 * Adding last movement to the table
		 */
		HexStatus.addMove(
			abs(startPlayer - player) + 1, 
			HexStatus.getTableSize() * i + j); // converting to unique unity
			
		lastMove = position;

		boolean hasWinner = HexWinner.hasWinner(
			abs(startPlayer - player) + 1, 
			HexStatus.getTableSize(),
			HexStatus.getMadeMoves(abs(startPlayer - player) + 1));
		
		return hasWinner;
	}
	
	public static synchronized boolean randomMove(int player) {
		
		boolean isWinner = false;
		
		int m = (new Random()).nextInt(Move.list.size());
	
		isWinner = play(player, Move.list.get(m));
		
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
	
	public static synchronized void changeStartPlayer() {
		HexCommand.startPlayer = PLAYERS[HexCommand.startPlayer - 1];
		HexCommand.currentPlayer = HexCommand.startPlayer;
	}
	
	public static synchronized void setCurrentPlayer(int i) {
		currentPlayer = i;
	}
	
	public static synchronized int getCurrentStartPlayer() {
		return startPlayer;
	}

	public static void clearLastMove() {
		lastMove = "";
	}

	public static void log(int winner, int gameNumber) {
		
		log.append("GAME NUMBER: " + gameNumber + "\n");
		log.append("NUMBER OF MOVES: " + HexStatus.getNumberOfMoves() + "\n");
		log.append("WINNER: " + winner + "\n");
		log.append("INITIAL PLAYER: " + HexCommand.getCurrentStartPlayer() + "\n");
		log.append("=========================" + "\n");
		
		if (gameNumber == HexStatus.getNumberOfGames()) {
			log.insert(0,
					"FINAL BOARD: P1 " +
					HexStatus.board[0] + 
					" X P2 " + HexStatus.board[1]
					+ "\n" +
					"========================="
					+ "\n");
			
			FileManagement.write(
				log.toString(),
				DateManagement.getCurrentTimeStamp("yyyyMMdd-HHmmss") +
				"-HEX_LOG.txt");
			
		}
	}
}

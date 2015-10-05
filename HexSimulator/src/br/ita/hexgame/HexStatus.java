package br.ita.hexgame;

import java.util.ArrayList;
import java.util.List;

public class HexStatus {

	private static List<Integer> moves = new ArrayList<Integer>(); 
	
	public static int[] board = new int[] {0, 0};
	
	private static int numberOfGames = 15;
	
	private static int tableSize = 0;

	private static int winner = 0;

	public static Thread view;
	
	private static final List<Integer> whiteMoves = new ArrayList<Integer>();
	
	private static final List<Integer> blackMoves = new ArrayList<Integer>();

	public static int getNumberOfMoves() {
		return whiteMoves.size() + blackMoves.size();
	}
	
	public static List<Integer> getWhiteMoves() {
		return whiteMoves;
	}
	
	public static List<Integer> getBlackMoves() {
		return blackMoves;
	}
	
	public static List<Integer> getMadeMoves(int player) {
		if (player == 1)
			return whiteMoves;
		else if (player == 2)
			return blackMoves;
		else
			return null;
	}

	public static void setTableSize(int n) {
		tableSize = n;
	}
	
	public static int getTableSize() {
		return tableSize;
	}
	
	public static void addMove(int player, int move) {
		if (player == 1)
			whiteMoves.add(move);
		else if (player == 2)
			blackMoves.add(move);
		else
			try {
				throw new Exception("Invalid player.");
			} catch(Exception e) {
				e.printStackTrace();
			}
	}

	public static synchronized void setWinner(int player) {
		HexStatus.winner  = player;		
	}
	
	public static synchronized int getWinner() {
		return HexStatus.winner;
	}
	
	public static void clearMadeMoves() {
		whiteMoves.clear();
		blackMoves.clear();
		Move.list.clear();
		Move.list.addAll(Move.validMoves);
	}

	public static void setNumberOfGames(int games) {
		numberOfGames = games;
	}
	
	public static int getNumberOfGames() {
		return numberOfGames;
	}
	
	public static synchronized void popMove(Integer move) {
		moves.add(move);
	}
	
	/**
	 * -1 HAS NOTHING
	 * -2 PLAYER ONE WINS
	 * -3 PLAYER TWO WINS
	 * -4 GAME END
	 * @return
	 */
	public static synchronized List<Integer> getMoves() {
		return HexStatus.moves;
	}

	public synchronized static void clearMoves() {
		HexStatus.moves.clear();		
	}
}

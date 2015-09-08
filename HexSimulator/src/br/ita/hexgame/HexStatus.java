package br.ita.hexgame;

import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.max;

public class HexStatus {

	private static int tableSize = 0;

	private static int winner = 0;
	
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

	public static List<Integer> getMadeMoves() {
		
		List<Integer> moves = new ArrayList<Integer>();
		
		for (int i = 0; i < max(whiteMoves.size(), blackMoves.size()); i++) {
			
			if (i < whiteMoves.size())
				moves.add(whiteMoves.get(i));
			
			if (i < blackMoves.size())
				moves.add(blackMoves.get(i));
		}
		
		return moves;
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
	
	public static void addMove(int currentPlayer, int move) {
		if (currentPlayer == 1)
			whiteMoves.add(move);
		else
			blackMoves.add(move);
	}

	public static List<Integer> getMadeMove(int player) {
		if (player == 1)
			return whiteMoves;
		else
			return blackMoves;
	}

	public static synchronized void setWinner(int player) {
		HexStatus.winner  = player;		
	}
	
	public static synchronized int getWinner() {
		return HexStatus.winner;
	}
}

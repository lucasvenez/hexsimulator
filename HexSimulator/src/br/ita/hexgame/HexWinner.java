package br.ita.hexgame;

import java.util.ArrayList;
import java.util.List;

public class HexWinner {

	/**
	 * <b>Algorithm</b><br/>
	 * Variables:<br/>
	 * <ul>
	 * 	<li><i>n</i> is the number of lines and columns of the table;</li>
	 * 	<li><i>m</i> is the number of moves.</li>
	 * </ul>
	 * 		
	 * @return the game winner, player 1 or 2, or 0 when the game has no winner.
	 */
	public static synchronized boolean hasWinner(int player, int n, List<Integer> madeMoves) {
		
		boolean winner = false;

		/*
		 * The minimum number of movements 
		 * for having a winner is (2 * n - 1).
		 */
		if (hasWinnerRequeriments(player, n, madeMoves))
			winner = calculateWinner(player, n, madeMoves);
		
		return winner;
	}
	
	public static synchronized boolean calculateWinner(int player, int n, List<Integer> madeMoves) {
		
		boolean hasWinner = false;
		
		List<Integer> visited = new ArrayList<Integer>();
			
		for (int i = 0; i < n && !hasWinner; i++) {
			int tmp = i;
			i += (player - 1) * i * (n - 1);
			
			if (madeMoves.contains(i)) {
				visited.add(i);
				hasWinner = winner(player, i, n, madeMoves, visited);
			}
			i = tmp;
		}			
			
		return hasWinner;
	}
	
	/**
	 * @param player is the player number (1 or 2)
	 * @param i is the position to be tested;
	 * @param n is the table size n x n
	 * @param visited the list of visited positions.
	 * @return true if there is a winner or false otherwise.
	 */
	public static boolean winner(
			int player, int i, int n, List<Integer> madeMoves, List<Integer> visitedMoves) {
		
		boolean result = false;
		
		/*
		 * Checking if was a winner
		 */
		for (int k = (n * n - 1); k >= n * n - n && !result; k--) {
			int tmp = k;
			k -= (player - 1) * 10 * (120 % k);
			result |= (i == k);
			k = tmp;
		}
		
		if (!result && madeMoves.contains(i)) {
			
			int neighbour[] = new int[] {
				i - n,       //TOP LEFT
				i - (n - 1), //TOP RIGHT
				i - 1,       //MIDDLE LEFT
				i + 1,       //MIDDLE RIGHT
				i + (n - 1), //BOTTON LEFT
				i + n        //BOTTON RIGHT
			};
			
			boolean condition[] = new boolean[] {
				i >= n,                     //TOP LEFT
				i >= n && i % n < n - 1,    //TOP RIGHT
				i % n > 0,                  //MIDDLE LEFT
				i % n < n - 1,              //MIDDLE RIGHT
				i < n * n - n && i % n > 0, //BOTTON LEFT
				i < n * n - n               //BOTTON RIGHT	
			};
			
			for (int l = 0; l < neighbour.length && !result; l++)
				if (!result && condition[l])
					if (!visitedMoves.contains(neighbour[l])) {
						visitedMoves.add(neighbour[l]);
						result |= winner(player, neighbour[l], n, madeMoves, visitedMoves);
					 }
		 }
		
		 return result;
	}

	/**
	 * @param player is the player number (1 or 2).
	 * @param n is the table size n x n.
	 * @param m is the number of moves (player 1 + player 2).
	 * @param madeMoves is the moves made by player under analysis.
	 * @return true whether exists a winner and false otherwise.
	 */
	public static synchronized boolean hasWinnerRequeriments(
			int player, int n, List<Integer> madeMoves) {
		
		boolean contains[] = new boolean[] {false, false};
		
		/*
		 * Checking if extremities contain valid moves for having a winner.
		 */
		if (player == 1) {
			
			for (Integer i = 0; i < n && (!contains[0] || !contains[1]); i++) {
				
				if (!contains[0])
					contains[0] |= madeMoves.contains(i);
				
				if (!contains[1])
					contains[1] |= madeMoves.contains((n * n - 1) - i);
			}
			
		} else if (player == 2) {
			
			for (Integer i = 0; i < n * n && (!contains[0] || !contains[1]); i+= n) {
				if (!contains[0]) 
					contains[0] |= madeMoves.contains(i);
				
				if (!contains[1])
					contains[1] |= madeMoves.contains(i + (n - 1));
			}
		}
		
		return contains[0] && contains[1];
	}
}

package br.ita.hexgame;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class HexWinnerTest {

		private int n = 11,
		            m = 30;
		
		private static List<Integer> movesPlayerOne = new ArrayList<Integer>();
		private static List<Integer> movesPlayerTwo = new ArrayList<Integer>();
		
		static {
			movesPlayerOne.addAll(
				Arrays.asList(
					new Integer[] {6, 16, 15, 17, 18, 28, 38, 
							39, 40, 41, 31, 51, 50, 40, 49,
							59, 70, 69, 80, 90, 91, 92, 
							93, 101, 112}));
			
			movesPlayerTwo.addAll(
					Arrays.asList(
						new Integer[] {44, 34, 45, 56, 46, 35, 36, 
								   37, 48, 59, 60, 71, 82, 93,
								  104, 115, 116, 106, 117, 107,
								   97, 87}));
		}
		
	@Test
	public void hasWinnerRequerimentsPlayerOne() {
		assertTrue(HexWinner.hasWinnerRequeriments(1, n, m, movesPlayerOne));
	}
	
	@Test
	public void hasWinnerRequerimentsPlayerTwo() {
		assertTrue(HexWinner.hasWinnerRequeriments(2, n, m, movesPlayerTwo));
	}
	
	@Test
	public void hasWinnerPlayerOne() {
		assertTrue(HexWinner.hasWinner(1, n, m, movesPlayerOne));
	}
	
	@Test
	public void hasWinnerPlayerTwo() {
		assertTrue(HexWinner.hasWinner(2, n, m, movesPlayerTwo));
	}
}

package br.ita.hexgame;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class HexWinnerTest {

		private int n = 11;
		
		private static List<Integer> movesPlayerOne1 = new ArrayList<Integer>();
		private static List<Integer> movesPlayerOne2 = new ArrayList<Integer>();
		private static List<Integer> movesPlayerTwo1 = new ArrayList<Integer>();
		private static List<Integer> movesPlayerTwo2 = new ArrayList<Integer>();
		
		static {
			movesPlayerOne1.addAll(
				Arrays.asList(
					new Integer[] {6, 16, 15, 17, 18, 28, 38, 
							39, 40, 41, 31, 51, 50, 40, 49,
							59, 70, 69, 80, 90, 91, 92, 
							93, 101, 112}));
			
			movesPlayerOne2.addAll(
					Arrays.asList(
						new Integer[] {60,50,24,77,79,49,36,82,92,9,57,66,37,73,40,18,34,38,16,13,108,83,95,48,119,29,105,59,1,8,45,97,17,103,101,89,3,93,39,71,21,5,47,96,106,68,31,113,7,51,26,44,115,76,4}
					));
			
			movesPlayerTwo1.addAll(
					Arrays.asList(
						new Integer[] {44, 34, 45, 56, 46, 35, 36, 
								   37, 48, 59, 60, 71, 82, 93,
								  104, 115, 116, 106, 117, 107,
								   97, 87}));
			
			movesPlayerTwo2.addAll(
					Arrays.asList(
						new Integer[] {120,116,23,109,46,94,6,30,104,14,52,107,41,86,54,58,72,28,88,55,110,69,12,35,90,10,117,11,63,20,61,15,0,75,53,42,2,19,78,74,91,100,33,67,99,111,102,112,27,64,87,25,80,70}
					));
		}
		
	@Test
	public void hasWinnerRequerimentsPlayerOne() {
		assertTrue(HexWinner.hasWinnerRequeriments(1, n, movesPlayerOne1));
		assertTrue(HexWinner.hasWinnerRequeriments(1, n, movesPlayerOne2));
	}
	
	@Test
	public void hasWinnerRequerimentsPlayerTwo() {
		assertTrue(HexWinner.hasWinnerRequeriments(2, n, movesPlayerTwo1));
		assertTrue(HexWinner.hasWinnerRequeriments(2, n, movesPlayerTwo2));
	}
	
	@Test
	public void hasWinnerPlayerOne() {
		assertTrue(HexWinner.hasWinner(1, n, movesPlayerOne1));
		assertTrue(HexWinner.hasWinner(1, n, movesPlayerOne2));
	}
	
	@Test
	public void hasWinnerPlayerTwo() {
		assertTrue(HexWinner.hasWinner(2, n, movesPlayerTwo1));
		assertFalse(HexWinner.hasWinner(2, n, movesPlayerTwo2));
	}
}


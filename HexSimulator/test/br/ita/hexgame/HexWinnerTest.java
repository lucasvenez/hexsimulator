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
		private static List<Integer> movesPlayerTwo3 = new ArrayList<Integer>();
		
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
			
			movesPlayerTwo3.addAll(
					Arrays.asList(
						new Integer[] {120,60,92,82,54,10,111,99,57,112,25,42,102,11,96,47,71,100,83,91,98,50,33,21,38,61,5,93,18,22,88,113,39,118,63,52,9,76,104,28,19,116,86,81,26,80,14,85,74,49,55,69,56,73,23,84,103,46,1,94,87,115,12,24,117,78,48,16,64,68,15,77,66,67,31,36,58,110,89,27,13,59,53,72,109,7,62,106,108,20,43,40,70,51} 
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
		
		int j = 0;
		int a[] = new int[47];
		for (int i = 1; i < movesPlayerTwo3.size(); i += 2) {
			a[j] = movesPlayerTwo3.get(i);
			j++;
		}
		
		System.out.println(HexWinner.hasWinnerRequeriments(2, a.length * 2, movesPlayerTwo3));
		System.out.println(HexWinner.hasWinner(2, a.length * 2, movesPlayerTwo3));
	}
}


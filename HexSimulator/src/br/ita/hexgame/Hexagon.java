package br.ita.hexgame;

public class Hexagon {

	/*
	 * 0 means position enabled
	 * 1 means position made by player 1
	 * 2 means position made by player 2
	 */
	private int player = 0;

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		if (player == 1 || player == 2)
			this.player = player;
	}	
}

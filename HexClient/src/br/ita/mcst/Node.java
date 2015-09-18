package br.ita.mcst;

import java.io.Serializable;

public class Node implements Serializable {

	private final int movement;
	
	private int wins = 0;
	
	private int games = 0;
	
	private Tree parent;
	
	public Node(int movement, Tree parent) {
		this.movement = movement;
		this.parent = parent;
	}
	
	public void addWin() {
		wins++;
		games++;
	}
	
	public void addGame() {
		games++;
	}
	
	public int getWins() {
		return this.wins;
	}
	
	public int getGames() {
		return this.games;
	}
	
	public float getProportion() {
		return this.wins / this.games;
	}
	
	public int getMovement() {
		return this.movement;
	}
	
	public Tree getParent() {
		return this.parent;
	}
}

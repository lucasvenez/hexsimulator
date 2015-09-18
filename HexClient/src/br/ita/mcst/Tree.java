package br.ita.mcst;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Tree implements Serializable {
	
	private ArrayList<Node> leaves = new ArrayList<Node>();
	
	private final Set<Integer> invalidMoves = new HashSet<Integer>();
}

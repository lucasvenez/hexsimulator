package br.ita.mcst;

import java.util.Comparator;

public class NodeCompartor implements Comparator<Node> {

	@Override
	public int compare(Node n1, Node n2) {
		
		if (n1.getProportion() == n2.getProportion())
			return 0;
		else if (n1.getProportion() < n2.getProportion())
			return -1;
		else
			return 1;
	}
}

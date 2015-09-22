package br.ita.mcts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import br.ita.mcts.algorithm.HexWinner;

/**
 * 
 * @author Lucas Venezian Povoa
 *
 */
public class HexTreeNode implements Serializable {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 6110371456108666526L;

	/**
	 * Moving position ranging from o to n * n, where n is the table size.
	 */
	private int move;
	
	/**
	 * True means Player 1 and false Player 2.
	 */
	private boolean player = true;
	
	/**
	 * 
	 */
	private HexTreeSearch tree;
	
	/**
	 * 
	 */
	private final List<HexTreeNode> children = new ArrayList<HexTreeNode>();

	/**
	 * Number of visited times.
	 */
	protected int visitedTimes = 0;
	
	/**
	 * Value defining how good is this move.
	 */
	protected double weight;
	
	/**
	 * Parent node
	 */
	protected HexTreeNode parent = null;
	
	/**
	 * 
	 * @param move
	 * @param player
	 * @param tree
	 * @param parent
	 */
	public HexTreeNode(int move, boolean player, HexTreeSearch tree, HexTreeNode parent) {
		this.move     = move;
		this.player   = player;
		this.tree     = tree;
		this.parent   = parent;
		this.weight    = 0;
		
		if (parent != null)
			parent.addOrUpdateChildren(this);
	}
	
	/**
	 * 
	 * @param move 
	 * @param player
	 * @param tree
	 * @param parent
	 * @param weight 
	 */
	public HexTreeNode(int move, boolean player, HexTreeSearch tree, HexTreeNode parent, Double weight) {
		this(move, player, tree, parent);
		
		if (weight != null)
			this.weight = weight;
	}

	/**
	 * 
	 */
	protected void selectAction() {
		
		List<HexTreeNode> visited = new ArrayList<HexTreeNode>();
         
		HexTreeNode cur = this;
        
		visited.add(this);
        
		while (!cur.isLeaf()) {
        
			cur = cur.select();
            
			visited.add(cur);
        }
        
		cur.expand();
        
		HexTreeNode newNode = cur.select();
        
		visited.add(newNode);
        
		double value = rollOut(newNode);
        
		for (HexTreeNode node : visited)
            node.update(value);
	}

	/**
	 * 
	 */
	protected void expand() {
		// TODO Auto-generated method stub
	}

	/**
	 * 
	 * @return
	 */
	protected HexTreeNode select() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @return
	 */
	protected boolean isLeaf() {
		
		List<Integer> moves = getMadeMoves();
		
		return HexWinner.hasWinner(tree.getPlayer() ? 1 : 2, 11, moves);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean getPlayer() {
		return player;
	}

	/**
	 * 
	 * @param player
	 */
	public void setPlayer(boolean player) {
		this.player = player;
	}

	/**
	 * 
	 * @param newNode
	 * @return
	 */
	public double rollOut(HexTreeNode newNode) {
		// TODO Auto-generated method stub
		return 0.0;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getMove() {
		return move;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Integer> getMadeMoves() {

		List<Integer> madeMoves = new LinkedList<Integer>();
		
		HexTreeNode current = this;
		
		while (current != null) {
			
			if (current.getPlayer() == this.player)
				madeMoves.add(0, current.getMove());
			
			current = current.getParent();
		}
		
		return madeMoves;
	}
	
	/**
	 * 
	 * @return
	 */
	@Override
	public int hashCode() {
	
		final int prime = 31;
		
		int result = 1;
		
		result = prime * result + (player ? 1231 : 1237);
		
		result = prime * result + move;
		
		return result;
	}

	/**
	 * 
	 * @param move
	 */
	public void setMove(int move) {
		this.move =  move;
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 */
	public HexTreeNode addOrUpdateChildren(HexTreeNode node) {
		
		HexTreeNode result = null;
		
		if (this.children.contains(node)) {
			
			int i = this.children.indexOf(node);
			
			this.children.get(i).update(0.0);
			
			result = this.children.get(i);

		} else {
			
			node.update(0.0);
			
			this.children.add(node);
			
			result = node;
		}
		
		return result;
	}
	
	/**
	 * 
	 * @return
	 */
	public Set<HexTreeNode> getChildren() {
		return null;
	}
	
	/**
	 * 
	 * @param value
	 */
	public void update(double value) {
		this.visitedTimes++;
        this.weight += value;
    }
	
	/**
	 * 
	 * @return
	 */
	public int getDepth() {
		int result = 0;
		
		HexTreeNode current = this;
		
		while (current != null) {
			current = current.getParent();
			result++;
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public HexTreeNode getNode(int index) {
		return children.get(index);
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public HexTreeNode removeNode(int index) {
		return children.remove(index);
	}
	
	/**
	 * 
	 * @param node
	 */
	public void addTreeNode(HexTreeNode node) {
		this.children.add(node);
	}
	
	/**
	 * 
	 * @return
	 */
	public HexTreeNode getParent() {
		return this.parent;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getArity() {
        return children.size();
    }

	/**
	 * 
	 * @return
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * 
	 * @param value
	 */
	public void setValue(long value) {
		this.weight = value;
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		HexTreeNode other = (HexTreeNode) obj;
		
		if (player != other.player)
			return false;
		
		if (move != other.move)
			return false;
		
		if (this.getDepth() != other.getDepth())
			return false;

		return true;
	}
	
	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("{\"node\":{");
		sb.append("\"p\":").append(player).append(",");
		sb.append("\"vt\":").append(visitedTimes).append(",");
		sb.append("\"m\":").append(move).append(",");
		sb.append("\"w\":").append(weight).append(",");
		sb.append("\"c\":[");
		
		for (HexTreeNode i : children)
			sb.append(i.toString());
		
		sb.append("]");
		sb.append("}}");
		
		return sb.toString();
	}
}

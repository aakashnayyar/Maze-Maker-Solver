package cs146F19.Nayyar.project3;
import java.util.*;

public class CellNode {
	public int color;
	public int distance;
	public CellNode parent;
	public int val;
	public int visited;
	public CellNode next;
	public ArrayList<CellNode> neighbors;
	public LinkedList<CellNode> connections;
	
	/**
	 * Create a cell node (represents a cell in a maze)
	 * @param val value of the cell node
	 */
	public CellNode(int val) {
		this.val = val;
		this.next = null;
		this.visited = -1;
		this.neighbors = new ArrayList<CellNode>();
		this.connections = new LinkedList<CellNode>();
	}
}
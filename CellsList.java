package cs146F19.Nayyar.project3;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CellsList {
	
	public static int WHITE = 0;
	public static int GRAY = 1;
	public static int BLACK = 2;
	public static int time;
	public static int visited;
	public static boolean reachedLast = false;
	public static File file; // = new File("/Users/preranasunilkumar/Desktop/cs146/Projects/src/finaltesting/output");
	//public static File file; //= new File("/Users/aakash/Desktop","NayyarAakashGridDFS.txt");
	public static FileWriter fw;
	public static BufferedWriter bw;
	public static int BFSTotalVisited;
	public static int DFSTotalVisited;
	public static String path = "";
	public static int mazeDimension;
	public static int length=0;
	public static boolean bfsEnd; 

	/**
	 * Creates a Cell List full of Cell Nodes
	 * @param r r*r cell list will be created
	 * @param f output file
	 * @return list of cell nodes
	 */
	public ArrayList<CellNode> createCellsList(int r, File f){
		file = f;
		mazeDimension = r;
		ArrayList<CellNode> list = new ArrayList<CellNode>();
		//creating and adding the required number of cells into the list
		for(int i=1; i<=(r*r); i++) {
			CellNode node = new CellNode(i);
			list.add(node);
		}
		for(CellNode cell: list) {
			int cellNumber = list.indexOf(cell)+1;
			//add the left side neighbor
			if((cellNumber)%r!=1) {
				cell.neighbors.add(list.get(cellNumber-2));
			}
			//add the neighbor above
			if(cellNumber>r)
				cell.neighbors.add(list.get(cellNumber-r-1));
			//add neighbor below
			if(cellNumber<=((r*r)-r)) {
				cell.neighbors.add(list.get(cellNumber+r-1));
			}
			//add right side neighbor
			if(cellNumber%r!=0)
				cell.neighbors.add(list.get(cellNumber));
		}
		return list;
	}
	
	/**
	 * Generates a single solution
	 * @param list arraylist of cellNodes
	 */
	public void generatePath(ArrayList<CellNode> list) {
		Stack<CellNode> cellStack = new Stack<CellNode>();
		int totalCells = list.size();
		CellNode currentCell = list.get(0);
		int visitedCells = 1;
		Random r = new Random();
		r.setSeed(99);
		//continue until all cells have been explored to guarantee a path from start to any cell
		while(visitedCells<totalCells) {
			
			//if the current cell has unexplored neighbors, randomly select one to create a connection to
			if(currentCell.neighbors.size()>=1) {
				int index = r.nextInt(currentCell.neighbors.size());
				CellNode nextCell = currentCell.neighbors.get(index);
				currentCell.connections.add(nextCell);
				nextCell.connections.add(currentCell);
				currentCell.neighbors.remove(nextCell);
				//remove the newly connected cell from the neighbors list of all other cells
				for(CellNode c: list) {
					if(c.neighbors.contains(nextCell))
						c.neighbors.remove(nextCell);
				}
				//remove the current cell from the neighbors list of the newly connected cell
				nextCell.neighbors.remove(currentCell);
				cellStack.push(currentCell);
				currentCell = nextCell;
				//increment number of visited cells
				visitedCells = visitedCells+1;
			}
			else {
				currentCell = cellStack.pop();
			}
		}
	}
	
	/**
	 * Prints the empty maze
	 * @param r maze dimensions will be r*r
	 * @param maze array list which will be turned into a maze
	 * @throws IOException
	 */
	public void printMaze(int r, ArrayList<CellNode> maze) throws IOException {
		fw = new FileWriter(file);
		bw = new BufferedWriter(fw);
		bw.write(r+" "+r);
		bw.newLine();
		String walls = "+";
		String interior = "|";
		//printing horizontal wall in the beginning
		for(int i=0;i<r;i++) {
			if(i==0)
				walls += " +";
			else
				walls += "-+";
		}
		bw.write(walls);
		bw.newLine();
		for(int i=1;i<=(r*r);i++) {
			//at the last cell, break after printing the right side wall
			if(i==(r*r)) {
				bw.write(" "+"|");
				bw.newLine();
				break;
			}
			//start with "|" at the beginning of each line
			if(i%r==1) {
				bw.write(interior);
			}
			//leave a space when 2 adjacent cells are connected
			if((maze.get(i-1).connections.contains(maze.get(i)))||(maze.get(i).connections.contains(maze.get(i-1)))) {
				bw.write(" " + " ");
			}
			else {
				bw.write(" "+"|");
			}
			//printing horizontal walls leaving spaces where a cell is connected to the one below it
			if(i%r==0 && i!=(r*r)) {
				bw.newLine();
				bw.write("+");
				for(int j=0; j<r; j++) {
					if((maze.get(i+j).connections.contains(maze.get(i+j-r)))||(maze.get(i+j-r).connections.contains(maze.get(i+j))))
						bw.write(" "+"+");
					else
						bw.write("-+");
				}
				bw.newLine();
			}
		}
		String end = "+";
		//printing horizontal wall at the end of the maze
		for(int i=0;i<r;i++) {
			if(i==r-1)
				end = end+" +";
			else
				end += "-+";
		}
		bw.write(end);
		bw.newLine();
	}
	
	/**
	 * Prints the path for DFS
	 * @param r maze dimensions
	 * @param maze array list of cell nodes
	 * @throws IOException
	 */
	public void printDFSPath(int r, ArrayList<CellNode> maze) throws IOException {
		bw.newLine();
		bw.write("DFS:");
		bw.newLine();
		String walls = "+";
		String interior = "|";
		//printing horizontal wall in the beginning
		for(int i=0;i<r;i++) {
			if(i==0)
				walls += " +";
			else
				walls += "-+";
		}
		bw.write(walls);
		bw.newLine();

		for(int i=1;i<=(r*r);i++) {
			
			//at the last cell, break after printing right side wall
			if(i==(r*r)) {
				bw.write(maze.get(i-1).visited%10+"|");
				bw.newLine();
				break;
			}

			//start with "|" at the beginning of each line
			if(i%r==1) {
				bw.write(interior); 
			}
			
			//if the cell has been visited in dfs, print its visit number
			if(maze.get(i-1).visited!=-1) {
				bw.write(""+maze.get(i-1).visited%10);
			}
			else
				bw.write(" ");
			
			//print space when adjacent cells are connected
			if((maze.get(i-1).connections.contains(maze.get(i)))||(maze.get(i).connections.contains(maze.get(i-1)))) {
					bw.write(" ");
			}
			else {
					bw.write("|");
			}
			
			//printing horizontal walls leaving spaces where a cell is connected to the one below it 
			if(i%r==0 && i!=(r*r)) {
				bw.newLine();
				bw.write("+");
				for(int j=0; j<r; j++) {
					if((maze.get(i+j).connections.contains(maze.get(i+j-r)))||(maze.get(i+j-r).connections.contains(maze.get(i+j))))
						bw.write(" "+"+");
					else
						bw.write("-+");
				}
				bw.newLine();
			}
		}
		String end = "+";
		//printing horizontal wall at the end of the maze
		for(int i=0;i<r;i++) {
			if(i==r-1)
				end = end+" +";
			else
				end += "-+";
		}
		bw.write(end);
	}
	
	/**
	 * Prints the path for BFS
	 * @param r maze dimension is r*r
	 * @param maze array list of cell nodes
	 * @throws IOException
	 */
	public void printBFSPath(int r, ArrayList<CellNode> maze) throws IOException {
		bw.newLine();
		bw.write("BFS:");
		bw.newLine();
		String walls = "+";
		String interior = "|";
		//printing horizontal wall in the beginning
		for(int i=0;i<r;i++) {
			if(i==0)
				walls += " +";
			else
				walls += "-+";
		}
		bw.write(walls);
		bw.newLine();

		for(int i=1;i<=(r*r);i++) {
			//at the last cell, break after printing right side wall
			if(i==(r*r)) {
				bw.write(maze.get(i-1).visited%10+"|");
				bw.newLine();
				break;
			}

			//start with "|" at the beginning of each line
			if(i%r==1) {
				bw.write(interior); 
			}
			
			//if the cell has been visited in bfs, print its visit number
			if(maze.get(i-1).visited!=-1) {
				bw.write(""+maze.get(i-1).visited%10);
			}
			else
				bw.write(" ");
			
			//print space when adjacent cells are connected
			if((maze.get(i-1).connections.contains(maze.get(i)))||(maze.get(i).connections.contains(maze.get(i-1)))) {
				bw.write(" ");
			}
			else {
				bw.write("|");
			}
			
			//printing horizontal walls leaving spaces where a cell is connected to the one below it 
			if(i%r==0 && i!=(r*r)) {
				bw.newLine();
				bw.write("+");
				for(int j=0; j<r; j++) {
					if((maze.get(i+j).connections.contains(maze.get(i+j-r)))||(maze.get(i+j-r).connections.contains(maze.get(i+j))))
						bw.write(" "+"+");
					else
						bw.write("-+");
				}
				bw.newLine();
			}
		}
		String end = "+";
		//printing horizontal wall at the end of the maze
		for(int i=0;i<r;i++) {
			if(i==r-1)
				end = end+" +";
			else
				end += "-+";
		}
		bw.write(end);
	}
	
	/**
	 * BFS method
	 * @param list array list of cellNodes
	 * @param s root node
	 */
	public void BFS(ArrayList<CellNode> list, CellNode s) {
		for(CellNode u: list) { // initialize each cell
			u.color = WHITE;
			u.distance = Integer.MAX_VALUE;
			u.parent = null;
			u.visited = -1;
		}
		bfsEnd = false;
		s.color = GRAY;
		s.distance = 0;
		s.parent = null;
		s.visited = 0;
		Queue<CellNode> cellQueue = new LinkedList<CellNode>();
		cellQueue.add(s);
		visited = 0;
		while (!cellQueue.isEmpty() && !bfsEnd){
			CellNode u = cellQueue.remove();
			for (CellNode v: u.connections) { //for each cell connected to cell u
				if (v.color == WHITE) { // if v is unvisited 
					visited++; 
					v.visited = visited;
					v.color = GRAY; // color v gray
					v.distance = u.distance + 1;
					v.parent = u; // parent of v = u
					if(v.val==list.size()) {
						bfsEnd = true;
						break;
					}
					cellQueue.add(v);
				}
			}
			u.color = BLACK;
			if(u.val==list.size()) {  
				bfsEnd = true;
				break;
			}
		}
		BFSTotalVisited = visited+1;
		
	}
	
	/**
	 * Finds the shortest path
	 * @param list array list of nodes
	 * @param s source node
	 * @param v vertex
	 */
	public void printWay(ArrayList<CellNode> list, CellNode s, CellNode v) {
		if(v.val==s.val) { // if source is reached add (0,0) to the path
			path= "(0,0) " + path;
			length+=1; // length of path = length + 1
			return;
		}
		else if (v.parent == null) { // source is unreachable
			path = "no path exists"; 
		}
		else {
			path="("+ (v.val-1)/mazeDimension + "," + (v.val-1)%mazeDimension + ") "+ path; // path=( cell x, cell y) + path
			length+=1; // length = length+1
			printWay(list,s,v.parent);	 //recursive
		}
		
	}
	
	/**
	 * DFS method
	 * @param list array list of cell nodes
	 */
	public void DFS(ArrayList<CellNode> list) {
		for(CellNode u: list) { // initialize cells
			u.color = WHITE;
			u.parent = null;
			u.visited = -1;
		}
		time = 0;
		visited = -1;
		reachedLast = false;
		for(CellNode u: list) {
			if(u.color == WHITE) { //if cell is unvisited
				DFSVisit(list,u);
			}
		}
		DFSTotalVisited = visited+2;
	}
	
	/**
	 * sub method of DFS method
	 * @param list array list of cell nodes
	 * @param u cell node
	 */
	public void DFSVisit(ArrayList<CellNode> list, CellNode u) {
		if(reachedLast==true)
			return;
		u.color = GRAY; // u is visited
		u.visited = visited+1;
		visited++;
		u.distance = time;
		for(CellNode v: u.connections) { // for all cells connected to u
			if(v.val==list.size()) {
				v.parent = u; // v's parent = u
				reachedLast = true;
				v.visited = visited+1;
				return;
			}
			if(v.color == WHITE && !reachedLast) {
				v.parent = u;
				DFSVisit(list,v);
				if(v.val==list.size()) {
					reachedLast = true;
					v.visited = visited+1;
					return;
				}
			}
		}
		u.color = BLACK;
		time++;
		
	}
	
	/**
	 * Prints hashed path, the shortest path 
	 * @param r maze dimension is r*r
	 * @param maze array list of nodes
	 * @throws IOException
	 */
	public void printDFSHash(int r, ArrayList<CellNode> maze) throws IOException {
		//initializing path and length
		path = "";
		length = 0;
		
		//call printWay to get the path from start to end
		printWay(maze,maze.get(0),maze.get(r*r-1));
		bw.newLine();

		bw.newLine();
		String walls = "+";
		String interior = "|";
		
		//print the horizontal wall in the beginning
		for(int i=0;i<r;i++) {
			if(i==0)
				walls += " +";
			else
				walls += "-+";
		}
		bw.write(walls);
		bw.newLine();

		
		for(int i=1;i<=(r*r);i++) {
			
			//at the last cell, break after printing the right side wall
			if(i==(r*r)) {
				bw.write("#"+"|");
				bw.newLine();
				break;
			}

			//start with "|" at the beginning of each line
			if(i%r==1) {
				bw.write(interior); 
			}
			
			//if the cell was visited and the path contains the coordinates of the given cell, print a # sign
			if(maze.get(i-1).visited!=-1) {
				if(path.contains("("+(i-1)/r+","+(i-1)%r+")"))
					bw.write(""+"#");
				else
					bw.write(""+" ");
			}
			else
				bw.write(" ");
			
			//print a space if adjacent cells are connected
			if((maze.get(i-1).connections.contains(maze.get(i)))||(maze.get(i).connections.contains(maze.get(i-1)))) {
					bw.write(" ");
			}
			else {
					bw.write("|");
			}
			
			//printing horizontal walls leaving spaces where a cell is connected to the one below it 
			if(i%r==0 && i!=(r*r)) {
				bw.newLine();
				bw.write("+");
				for(int j=0; j<r; j++) {
					if((maze.get(i+j).connections.contains(maze.get(i+j-r)))||(maze.get(i+j-r).connections.contains(maze.get(i+j)))) {
							bw.write(" "+"+");
					}	
					else
						bw.write("-+");
				}
				bw.newLine();
			}
		}
		String end = "+";
		//print the horizontal wall at the end of the maze
		for(int i=0;i<r;i++) {
			if(i==r-1)
				end = end+" +";
			else
				end += "-+";
		}
		bw.write(end);
		bw.newLine(); bw.newLine();
		//print the coordinates
		bw.write("Path: " + path);
		bw.newLine(); bw.newLine();
		//print the length of the path
		bw.write("Length of path: " + length);
		bw.newLine(); bw.newLine();
		//print the total number of cells visited in bfs
		bw.write("Visited Cells: " + DFSTotalVisited);
		bw.newLine();

	}
	
	/**
	 * Prints hashed path, the shortest path 
	 * @param r maze dimension is r*r
	 * @param maze array list of nodes
	 * @throws IOException
	 */
	public void printBFSHash(int r, ArrayList<CellNode> maze) throws IOException {
		bw.newLine();
		bw.newLine();
		String walls = "+";
		String interior = "|";
		//at the last cell, break after printing the right side wall
		for(int i=0;i<r;i++) {
			if(i==0)
				walls += " +";
			else
				walls += "-+";
		}
		bw.write(walls);
		bw.newLine();

		for(int i=1;i<=(r*r);i++) {
			if(i==(r*r)) {
				bw.write("#"+"|");
				bw.newLine();
				break;
			}

			//start with "|" at the beginning of each line
			if(i%r==1) {
				bw.write(interior); 
			}
			
			//if the cell was visited and the path contains the coordinates of the given cell, print a # sign
			if(maze.get(i-1).visited!=-1) {
				if(path.contains("("+(i-1)/r+","+(i-1)%r+")"))
					bw.write(""+"#");
				else
					bw.write(""+" ");
			}
			else
				bw.write(" ");
			
			//print a space if adjacent cells are connected
			if((maze.get(i-1).connections.contains(maze.get(i)))||(maze.get(i).connections.contains(maze.get(i-1)))) {
					bw.write(" ");
			}
			else {
					bw.write("|");
			}
			
			//printing horizontal walls leaving spaces where a cell is connected to the one below it 
			if(i%r==0 && i!=(r*r)) {
				bw.newLine();
				bw.write("+");
				for(int j=0; j<r; j++) {
					if((maze.get(i+j).connections.contains(maze.get(i+j-r)))||(maze.get(i+j-r).connections.contains(maze.get(i+j)))) {
							bw.write(" "+"+");
					}	
					else
						bw.write("-+");
				}
				bw.newLine();
			}
		}
		String end = "+";
		//print the horizontal wall at the end of the maze
		for(int i=0;i<r;i++) {
			if(i==r-1)
				end = end+" +";
			else
				end += "-+";
		}
		bw.write(end);
		bw.newLine(); bw.newLine();
		//print the coordinates
		bw.write("Path: " + path);
		bw.newLine(); bw.newLine();
		//print the length of the path
		bw.write("Length of path: " + length);
		bw.newLine(); bw.newLine();
		//print the total number of cells visited in bfs 
		bw.write("Visited Cells: " + BFSTotalVisited);
		bw.newLine();
		bw.close();
		fw.close();
	}

}


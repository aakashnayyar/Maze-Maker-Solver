package cs146F19.Nayyar.project3;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class CellsListTest{
	
	@Test
	public void testSize4() throws IOException {
		//tests grid of size 4x4
		CellsList cells = new CellsList();
		File file = new File("/Users/aakash/Desktop","output.txt");
		ArrayList<CellNode> arr = cells.createCellsList(4,file);
		cells.generatePath(arr);
		cells.printMaze(4,arr);			
		cells.DFS(arr);
		cells.printDFSPath(4,arr);
		cells.printDFSHash(4, arr);
		cells.BFS(arr, arr.get(0));
		cells.printBFSPath(4, arr);
		cells.printBFSHash(4, arr);
	}
	
	
	@Test
	public void testSize5() throws IOException {
		//tests grid of size 5x5
		CellsList cells = new CellsList();
		File file = new File("/Users/aakash/Desktop","output2.txt");
		ArrayList<CellNode> arr = cells.createCellsList(5,file);
		cells.generatePath(arr);
		cells.printMaze(5,arr);			
		cells.DFS(arr);
		cells.printDFSPath(5,arr);
		cells.printDFSHash(5, arr);
		cells.BFS(arr, arr.get(0));
		cells.printBFSPath(5, arr);
		cells.printBFSHash(5, arr);
	}
	
	@Test
	public void testSize6() throws IOException {
		//tests grid of size 7x7
		CellsList cells = new CellsList();
		File file = new File("/Users/aakash/Desktop","output3.txt");
		ArrayList<CellNode> arr = cells.createCellsList(7,file);
		cells.generatePath(arr);
		cells.printMaze(7,arr);			
		cells.DFS(arr);
		cells.printDFSPath(7,arr);
		cells.printDFSHash(7, arr);
		cells.BFS(arr, arr.get(0));
		cells.printBFSPath(7, arr);
		cells.printBFSHash(7, arr);
	}
	
	@Test
	public void testSize7() throws IOException {
		//tests grid of size 10x10
		CellsList cells = new CellsList();
		File file = new File("/Users/aakash/Desktop","output4.txt");
		ArrayList<CellNode> arr = cells.createCellsList(10,file);
		cells.generatePath(arr);
		cells.printMaze(10,arr);			
		cells.DFS(arr);
		cells.printDFSPath(10,arr);
		cells.printDFSHash(10, arr);
		cells.BFS(arr, arr.get(0));
		cells.printBFSPath(10, arr);
		cells.printBFSHash(10, arr);
	}
	
	@Test
	public void testSize8() throws IOException {
		//tests grid of size 12x12
		CellsList cells = new CellsList();
		File file = new File("/Users/aakash/Desktop","output5.txt");
		ArrayList<CellNode> arr = cells.createCellsList(12,file);
		cells.generatePath(arr);
		cells.printMaze(12,arr);			
		cells.DFS(arr);
		cells.printDFSPath(12,arr);
		CellNode last = arr.get(arr.size()-1);
		cells.printDFSHash(12, arr);
		cells.BFS(arr, arr.get(0));
		cells.printBFSPath(12, arr);
		cells.printBFSHash(12, arr);
	}

}

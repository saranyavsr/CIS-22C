import java.util.ArrayList;

public class DisjointSets {

	private int[] array;
	public int[] path;
	private int lastroom;
	ArrayList<Integer> bfspath = new ArrayList<Integer>();
	
	public DisjointSets(int rows, int columns) {
		array = new int [rows * columns];
		lastroom = rows*columns-1;
		path = new int [rows * columns];
		for (int i = 0; i < array.length; i++) {
			array[i] = -1;
			path[i] = -1;
		}
	}

	public void union(int root1, int root2) {

		if (array[root1] >= 0) {
			return;
		}
		if (array[root2] >= 0) {
			return;
		}
		if (root1 == root2) {
			return;
		}

		if (array[root2] < array[root1]) {       
			array[root2] += array[root1];        
			array[root1] = root2;               
		} else {                              
			array[root1] += array[root2];        
			array[root2] = root1;                
		}
	}

	public int find(int x) {
		if (array[x] < 0) {
			return x;                         
		} else {
			array[x] = find(array[x]);
			return array[x];                                       
		}
	}
	
	public void path(int i, int j) {
		path[i] = j;
	}
	
	public void pathfinding() {
		while(path[lastroom] != -1) {
			bfspath.add(lastroom);
			lastroom = path[lastroom];
		}
		
		for (int i = 0; i < bfspath.size(); i++) {
			System.out.print(bfspath.get(i));
			System.out.print(" ");
		}
		System.out.print("0\n");
	}
}

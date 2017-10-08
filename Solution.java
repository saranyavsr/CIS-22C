import java.util.Stack;

public class Solution {
	
	int rows, columns;
	private int[][] roomnumber;
	public int[] arr;
	private Queue queue = new Queue();
	private static final int STARTPOINT = 0;
	private Stack<Integer> stack = new Stack<Integer>();
	boolean[][] Sdoor;
	boolean[][] Edoor;
	private static final int WEST = 1;
	private static final int EAST = 2;
	private static final int NORTH = 3;
	private static final int SOUTH = 4;
	
	public Solution() {
		rows = 0;
		columns = 0;
	}
	
	public Solution(int row, int column, boolean[][] SWall, boolean[][] EWall) {
		rows = row;
		columns = column;
		Sdoor = SWall;
		Edoor = EWall;
		solve();
	}

	protected void solve() {
		if ((rows < 1) || (rows == 1)) {
			return;                                 
		}
		
		System.out.printf("%d x %d Maze:\n", rows, rows);
		System.out.println();
		System.out.println(printMaze());
		
		int counter = 0;
		
		roomnumber = new int[rows][columns];    // 0 to N-1
		for (int j = 0; j < columns; j++) {
			for (int i = 0; i < rows; i++) {
				roomnumber[i][j] = counter;
				counter++;	
			}
		}
		
		boolean[][] cellVisited = new boolean[rows][columns];

		stack.push(0);
		System.out.println();
		System.out.print("Rooms visited by DFS: 0 ");
		dfs(0, 0, STARTPOINT, cellVisited);
		printdfs();
		System.out.println("This is the path.\n");
		
		
		System.out.println(mazeSolved(arr));
		
		boolean[][] roomVisited = new boolean[rows][columns];

		System.out.println();
		System.out.print("Rooms visited by BFS: 0 ");
		queue.enqueue(0);
		roomVisited[0][0] = true;

		bfs(roomVisited);
	}
	
	public void dfs(int x, int y, int startpoint, boolean[][] cellVisited) {

		cellVisited[x][y] = true;

		if ((startpoint != EAST) && !eastDoor(x, y) && (stack.peek() != (rows*columns-1))) {
			if (!cellVisited[x + 1][y]) {
				System.out.print(roomnumber[x+1][y]);
				System.out.print(" ");
				stack.push(roomnumber[x+1][y]);
				dfs(x + 1, y, WEST, cellVisited);
			}
		}

		if ((startpoint != SOUTH) && !southDoor(x, y) && (stack.peek() != (rows*columns-1))) {
			if (!cellVisited[x][y + 1]) {
				System.out.print(roomnumber[x][y+1]);
				System.out.print(" ");
				stack.push(roomnumber[x][y+1]);
				dfs(x, y + 1, NORTH, cellVisited);
			}
		}

		if ((startpoint != WEST) && !eastDoor(x - 1, y) && (stack.peek() != (rows*columns-1))) {
			if (!cellVisited[x - 1][y]) {
				System.out.print(roomnumber[x-1][y]);
				System.out.print(" ");
				stack.push(roomnumber[x-1][y]);
				dfs(x - 1, y, EAST, cellVisited);
			}
		}

		if ((startpoint != NORTH) && !southDoor(x, y - 1) && (stack.peek() != (rows*columns-1))) {
			if (!cellVisited[x][y - 1]) {
				System.out.print(roomnumber[x][y-1]);
				System.out.print(" ");
				stack.push(roomnumber[x][y-1]);
				dfs(x, y - 1, SOUTH, cellVisited);
			}
		}

		if(cellVisited[x][y] && !stack.isEmpty() && !cellVisited[rows-1][columns-1]) {
			stack.pop();
		}
	}

	private void bfs(boolean[][] roomVisited) {

		int lastItem = rows*columns -1;
		DisjointSets path = new DisjointSets(rows, columns);
		
		while (!queue.isEmpty() && queue.last() != (rows*columns-1)) {
			int i = queue.dequeue();
			int x = i % rows;
			int y = (i - x)/rows;

			if (i == lastItem) {
				break;
			}
			else {
				if(!southDoor(x, y - 1) && !roomVisited[x][y - 1]) { // check North
					System.out.print(roomnumber[x][y-1]);
					System.out.print(" ");
					roomVisited[x][y - 1] = true;
					queue.enqueue(roomnumber[x][y-1]);
					path.path(roomnumber[x][y-1], i);
				}

				if(!southDoor(x, y) && !roomVisited[x][y + 1]) {  // Check South
					System.out.print(roomnumber[x][y+1]);
					System.out.print(" ");
					roomVisited[x][y + 1] = true;
					queue.enqueue(roomnumber[x][y+1]);
					path.path(roomnumber[x][y+1], i);
				}

				if (!eastDoor(x - 1, y) && !roomVisited[x - 1][y]) {  // Check West
					System.out.print(roomnumber[x-1][y]);
					System.out.print(" ");
					roomVisited[x-1][y] = true;
					queue.enqueue(roomnumber[x-1][y]); 
					path.path(roomnumber[x-1][y], i);
				}

				if(!eastDoor(x, y) && !roomVisited[x + 1][y]) {	// Check East
					System.out.print(roomnumber[x+1][y]);
					System.out.print(" ");
					roomVisited[x + 1][y] = true;
					queue.enqueue(roomnumber[x+1][y]);
					path.path(roomnumber[x+1][y], i);
				}
			}
		}
		System.out.println();
		System.out.print("This is the path (in reverse): ");
		path.pathfinding();
		System.out.println("This is the path.\n");
		System.out.println(mazeSolved(arr));
		System.out.println("Maze solved successfully");
	}


	private void printdfs() {
		System.out.println();
		arr = new int[stack.size()];
		System.out.print("This is the path (in reverse): ");
		for(int j = 0; j < arr.length; j++) {
			System.out.print(stack.peek());
			arr[j] = stack.peek();
			System.out.print(" ");
			stack.pop();
		}
		System.out.println(); 
	}
	
	public String printMaze() {
		int i, j;
		String s = "";

		for (i = 0; i < rows; i++) {
			if(i == 0) {
				s = s + "X   ";
			} else {
				s = s + "XXXX"; }
		}
		s = s + "X\nX";

		for (j = 0; j < columns; j++) {
			for (i = 0; i < rows - 1; i++) {
				if (Edoor[i][j]) {
					s = s + "   X";
				} else {
					s = s + "    ";
				}
			}
			s = s + "   X\nX";

			if (j < columns - 1) {
				for (i = 0; i < rows; i++) {
					if (Sdoor[i][j]) {
						s = s + "XXXX";
					} else {
						s = s + "   X";
					}
				}
				s = s + "\nX";
			}
		}

		for (i = 0; i < rows; i++) {
			if (i == rows-1) {
				s = s + "   X";
			} else {
				s = s + "XXXX";}
		}
		return s + "\n";
	}
	
	public String mazeSolved(int[] arr) {
		int counter = 0;
		
		int[][] roomnumber = new int[rows][columns];    // 0 to N-1
		for (int j = 0; j < columns; j++) {
			for (int i = 0; i < rows; i++) {
				roomnumber[i][j] = counter;
				counter++;
			}
		}
		
		int i,j;
		String s = "";
		
		for (j = 0; j < rows; j++) {
			for(i = 0; i < columns; i++) {
				if(search(arr, roomnumber[i][j])) {
					s = s + "  X ";
				} else {
					s = s + "    ";
				}
				
			}
			s = s + "\n";
		}
		return s;
	}
	
	public boolean search(int [] numbers, int key)
	{	
	      for (int index = 0; index < numbers.length; index++)
	      {
	           if ( numbers[index] == key ) 
	                 return true; 
	      }
	     return false;
	}
	
	public boolean southDoor(int x, int y) {
		if ((x < 0) || (y < 0) || (x > rows - 1) || (y > columns - 2)) {
			return true;
		}
		return Sdoor[x][y];
	}

	public boolean eastDoor(int x, int y) {
		if ((x < 0) || (y < 0) || (x > rows - 2) || (y > columns - 1)) {
			return true;
		}
		return Edoor[x][y];
	}
}

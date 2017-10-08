import java.util.Random;
import java.util.ArrayList;
import java.awt.*;

public class MazeGeneration {

	private static Random random;
	boolean[][] Sdoor;
	boolean[][] Edoor;
	int rows, columns;
	int[][] roomnumber;
	private static final int ROOM_SIZE = 60;
	private static final int ROOM_MARGIN = 70;
	private static final int PATH_MARGIN = 30;
	private static final int PATH_SIZE = 10;
	private ArrayList<Integer> path;
	
	public MazeGeneration(int i) {
		this(i, i);
		int[] lPath = new Solution(rows, columns, Sdoor, Edoor).arr;
		path = new ArrayList<Integer>();
		for (int j = 0; j < lPath.length; j++) {
			path.add(lPath[j]);
		}
	}

	public MazeGeneration(int r, int c) {
		rows = r;
		columns = c;
		roomnumber = new int[rows*columns][4];

		if ((rows < 1) || (rows == 1)) {	
			System.out.printf("\nCannot print %dx%d maze", rows, rows);	
			return;
		}

		if (rows > 1) {
			Sdoor = new boolean[rows][columns - 1]; 
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns - 1; j++) {
					Sdoor[i][j] = true; }
			}
			Edoor = new boolean[rows - 1][columns]; 
			for (int i = 0; i < rows - 1; i++) {
				for (int j = 0; j < columns; j++) {
					Edoor[i][j] = true;}
			}
		}

		DisjointSets room = new DisjointSets(rows, columns);

		int roomcount = 2 * ((rows - 1) * columns);
		int[][] Walls = new int[roomcount][];

		if (rows > 1 && columns > 1) {

			int index = 0;

			for (int j = 0; j < columns - 1; j++) {
				for (int i = 0; i < rows; i++) {
					Walls[index] = new int[]{i, j, 0};
					index++;
				}
			}

			for (int j = 0; j < columns; j++) {
				for (int i = 0; i < rows - 1; i++) {
					Walls[index] = new int[]{i, j, 1};
					index++;
				}
			}

			int a = Walls.length;
			while (a > 1) { 
				int x = randomgeneration(a);
				int[] temp = Walls[x];
				Walls[x] = Walls[a - 1];
				Walls[a - 1] = temp;
				a--;
			}
			for (int[] door : Walls) { 
				int[] room1;
				int[] room2;

				boolean HorizontalWall = false;
				if (door[2] == 0) {
					HorizontalWall = true;
				}

				if (HorizontalWall) {
					room1 = new int[]{door[0], door[1]};
					room2 = new int[]{door[0], door[1] + 1};
				} else { 
					room1 = new int[]{door[0], door[1]};
					room2 = new int[]{door[0] + 1, door[1]};
				}
				int room1position = room1[1] * rows + room1[0];
				int room2position = room2[1] * rows + room2[0];
				int room1set = room.find(room1position);
				int room2set = room.find(room2position);

				if (room1set != room2set) {
					if (HorizontalWall) {
						Sdoor[door[0]][door[1]] = false;
					} else {
						Edoor[door[0]][door[1]] = false;
					}
					room.union(room1set, room2set);
				}

			}
		}
		
		for(int i = 0; i < rows; i++) {
			roomnumber[i][0] = 1;
		}
		for(int i = 0; i < (rows*columns); i=i+rows) {
			roomnumber[i][3] = 1;
		}
		for(int i = rows-1; i < (rows*columns); i=i+rows) {
			roomnumber[i][2] = 1;
		}
		for(int i = (rows*columns)-rows; i < (rows*columns); i++) {
			roomnumber[i][1] = 1;
		}
		int counter = 0;
		for(int j = 0; j < rows; j++) {
			for(int i = 0; i < rows-1; i++) {
			roomnumber[counter][2] = (Edoor[i][j]) ? 1 : 0; 
			roomnumber[counter+1][3] = (Edoor[i][j]) ? 1 : 0; 
			counter++;
			}
			counter++;
		}
		counter = 0;
		for(int j = 0; j < rows-1; j++) {
			for(int i = 0; i < rows; i++) {
			roomnumber[counter][1] = (Sdoor[i][j]) ? 1 : 0; 
			roomnumber[counter+rows][0] = (Sdoor[i][j]) ? 1 : 0; 
			counter++;
			}
		}
		roomnumber[0][0] = 0;
		roomnumber[rows*columns-1][1] = 0;
	}

	private static int randomgeneration(int i) {
		if (random == null) {       
			random = new Random();      
		}
		int rand = random.nextInt() % i;      
		if (rand < 0) {
			rand = -rand;                                      
		}
		return rand;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		int counter = 0;
		
		for (int j = 0; j < columns ; j++)
			{
			for (int i = 0; i < rows; i++)
				{
				if (roomnumber[counter][0] == 1) { //N
					g.drawLine(i * ROOM_SIZE + ROOM_MARGIN, j * ROOM_SIZE + ROOM_MARGIN, (i + 1) * ROOM_SIZE + ROOM_MARGIN, j * ROOM_SIZE + ROOM_MARGIN);
				}

				if (roomnumber[counter][1] == 1) { //S
					g.drawLine(i * ROOM_SIZE + ROOM_MARGIN, (j + 1) * ROOM_SIZE + ROOM_MARGIN, (i + 1) * ROOM_SIZE + ROOM_MARGIN, (j + 1) * ROOM_SIZE + ROOM_MARGIN);
				}

				if (roomnumber[counter][2] == 1) { //E
					g.drawLine((i + 1) * ROOM_SIZE + ROOM_MARGIN, j * ROOM_SIZE + ROOM_MARGIN, (i + 1) * ROOM_SIZE + ROOM_MARGIN, (j + 1) * ROOM_SIZE + ROOM_MARGIN);
				}

				if (roomnumber[counter][3] == 1) { //W
					g.drawLine(i * ROOM_SIZE + ROOM_MARGIN, j * ROOM_SIZE + ROOM_MARGIN, i * ROOM_SIZE + ROOM_MARGIN, (j + 1) * ROOM_SIZE + ROOM_MARGIN);
				}
				
				counter++;
			}
		}
		
		//Now draw the path using DOTS
		counter = 0;
		g.setColor(Color.DARK_GRAY);
		for (int j = 0; j < columns; j++){
			for (int i = 0; i < rows; i++){
				if(path.contains(counter)) {
					g.fill3DRect(i * ROOM_SIZE + ROOM_MARGIN + PATH_MARGIN, j * ROOM_SIZE + ROOM_MARGIN + PATH_MARGIN, PATH_SIZE, PATH_SIZE, true);
				}
				counter++;
			}
		}
	}
	
	public Dimension windowSize() {
		return new Dimension(rows * ROOM_SIZE + ROOM_MARGIN * 2, rows * ROOM_SIZE + ROOM_MARGIN * 2);
	}
}


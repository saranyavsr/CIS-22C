import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.*;

public class ReadFromFile {

	private static final int ROOM_SIZE = 60;
	private static final int ROOM_MARGIN = 70;
	private static final int PATH_MARGIN = 30;
	private static final int PATH_SIZE = 10;
	
	private int n = 0;
	private boolean[][] roomnumber;
	private boolean[][] Sdoor;
	private boolean[][] Edoor;	
	
	private ArrayList<Integer> path;
	
	public ReadFromFile(){
		createRooms("src/maze1.txt");
	}
	
	public ReadFromFile(String fileName) {
		createRooms(fileName);
	}
	
	public void createRooms() {
		int[] lPath = new Solution(n, n, Sdoor, Edoor).arr;
		
		path = new ArrayList<Integer>();
		for (int i = 0; i < lPath.length; i++) {
			path.add(lPath[i]);
		}
	}
	
	public boolean[][] getRooms() {
		return roomnumber;
	}
	
	public void createRooms(String fileName){
		
	    File file = new File(fileName);
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = " ";

			n = Integer.parseInt(br.readLine());
			line = br.readLine();
			
			this.roomnumber = new boolean[n*n][4];
			this.Sdoor = new boolean[n][n-1];
			this.Edoor = new boolean[n-1][n];	
			
			int counter = 0;
			
			for(int i = 0; i < n*n; i++) {
				String[] columns = line.split(" ");
				for(int j = 0; j < 4; j++) {
					roomnumber[i][j] = (columns[j].charAt(0)=='0')?false:true;
				}
				line = br.readLine();
			}

			counter = 0;
			for(int j = 0; j < n-1; j++) {
				for(int i = 0; i < n; i++) {
					Sdoor[i][j] = roomnumber[counter][1];
					counter++;
				}
			}
			counter = 0;
			
			for(int j = 0; j < n; j++) {
				for(int i = 0; i < n-1; i++) {
					Edoor[i][j] = roomnumber[counter][2];
					counter++;
				}
				counter++;
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		int counter = 0;
		
		for (int j = 0; j < n ; j++)
			{
			for (int i = 0; i < n; i++)
				{
				if (roomnumber[counter][0]) { //N
					g.drawLine(i * ROOM_SIZE + ROOM_MARGIN, j * ROOM_SIZE + ROOM_MARGIN, (i + 1) * ROOM_SIZE + ROOM_MARGIN, j * ROOM_SIZE + ROOM_MARGIN);
				}

				if (roomnumber[counter][1]) { //S
					g.drawLine(i * ROOM_SIZE + ROOM_MARGIN, (j + 1) * ROOM_SIZE + ROOM_MARGIN, (i + 1) * ROOM_SIZE + ROOM_MARGIN, (j + 1) * ROOM_SIZE + ROOM_MARGIN);
				}

				if (roomnumber[counter][2]) { //E
					g.drawLine((i + 1) * ROOM_SIZE + ROOM_MARGIN, j * ROOM_SIZE + ROOM_MARGIN, (i + 1) * ROOM_SIZE + ROOM_MARGIN, (j + 1) * ROOM_SIZE + ROOM_MARGIN);
				}

				if (roomnumber[counter][3]) { //W
					g.drawLine(i * ROOM_SIZE + ROOM_MARGIN, j * ROOM_SIZE + ROOM_MARGIN, i * ROOM_SIZE + ROOM_MARGIN, (j + 1) * ROOM_SIZE + ROOM_MARGIN);
				}
				
				counter++;
			}
		}
		
		//Now draw the path using DOTS
		counter = 0;
		g.setColor(Color.DARK_GRAY);
		for (int j = 0; j < n; j++){
			for (int i = 0; i < n; i++) {
				if(path.contains(counter)) {
					g.fill3DRect(i * ROOM_SIZE + ROOM_MARGIN + PATH_MARGIN, j * ROOM_SIZE + ROOM_MARGIN + PATH_MARGIN, PATH_SIZE, PATH_SIZE, true);
				}
				counter++;
			}
		}
	}
	
	public Dimension windowSize() {
		return new Dimension(n * ROOM_SIZE + ROOM_MARGIN * 2, n * ROOM_SIZE + ROOM_MARGIN * 2);
	}
}



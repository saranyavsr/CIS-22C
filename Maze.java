import java.util.*;
import java.awt.*;
import javax.swing.*;


public class Maze {
	
	private static boolean[][] maze;
	private static ReadFromFile fileMaze;
	
	private static int[][] mazeAutoGen;
	private static MazeGeneration mazeGeneration;
	
	public static void main(String[] args) {
						
		if (args.length == 1){
			//take arg, validate and send it to ReadFromFile constructor
			String path = args[0];
			String[] fileNameParts = path.split("\\.");
			
			if(fileNameParts[1].equals("txt")) {
				fileMaze = new ReadFromFile(path);
				maze = fileMaze.getRooms();
				createJFrame();
				fileMaze.createRooms();
			}
			else {
				System.out.println("File format not supported.");
			}
		}
		else {
			Scanner input = new Scanner(System.in);
			System.out.print("Enter any value for a nxn maze: ");
			int x = input.nextInt();
			mazeGeneration = new MazeGeneration(x);
			mazeAutoGen = mazeGeneration.roomnumber;
			createMazeAutoJFrame();
		}
	}
	
	private static void createMazeAutoJFrame() {
		JFrame frame = new JFrame("Maze Auto Gen");
		MazeAutoGenPanel panel = new MazeAutoGenPanel(mazeAutoGen, mazeGeneration); 
		JScrollPane scrollPane = new JScrollPane(panel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	
	private static void createJFrame() {
		JFrame frame = new JFrame("Maze");
		MazePanel panel = new MazePanel(maze, fileMaze);
		JScrollPane scrollPane = new JScrollPane(panel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}

class MazeAutoGenPanel extends JPanel {
	
	private int[][] mazeAuto;
	private MazeGeneration mazeGeneration;

	public MazeAutoGenPanel(int[][] m, MazeGeneration mg) {
		this.mazeAuto = m;
		this.mazeGeneration = mg;
	}
	
	public void paintComponent(Graphics page) {
		super.paintComponent(page);
		setBackground(Color.white);
		this.setPreferredSize(mazeGeneration.windowSize()); //helps with scrolling for large n values.
		mazeGeneration.draw(page);
	}
}

class MazePanel extends JPanel {
	
	private boolean[][] maze;
	private ReadFromFile fileMaze;
	
	public MazePanel(boolean[][] m, ReadFromFile fm) {
		this.maze = m;
		this.fileMaze = fm;
	}
	
	public void paintComponent(Graphics page) {
		super.paintComponent(page);
		setBackground(Color.white); 
		this.setPreferredSize(fileMaze.windowSize()); //helps with scrolling for large n values.
		fileMaze.draw(page);
	}
}


import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.*;
import javax.swing.*;

/**
 * @author Ian Steiner and Thomas Rosebrough
 * Map Editor for final project game
 */
@SuppressWarnings("serial")
public class MapEditor extends JFrame {

	static Level currentLevel;
	static Point lastPoint;
	static MapEditor GameFrame;
	
	static JMenuBar menuBar;
	static JMenu fileMenu;
	static BufferedImage tileSheet = null;
	
	public static void main(String[] args) {
		
		/**
		 * Initializes the JFrame Frame with size 800 x 600
		 * Creates Menu Bar with one menu and two items
		 */
		GameFrame = new MapEditor();
		
		currentLevel = new Level();
		
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		try {
			tileSheet = ImageIO.read(new File("walls_sprite_map.png"));
		} catch (IOException i) {
			i.printStackTrace();
		}
		
		/**
		 * Creates instances of nested action listener classes for
		 * saving and loading files
		 */
		JMenuItem newItem = new JMenuItem("New");
		JMenuItem saveItem = new JMenuItem("Save");
		JMenuItem loadItem = new JMenuItem("Open");
		newItem.addActionListener(new NewClass());
		saveItem.addActionListener(new SaveClass());
		loadItem.addActionListener(new LoadClass());
		GameFrame.addMouseListener(new mouseClass());
		
		fileMenu.add(newItem);
		fileMenu.add(saveItem);
		fileMenu.add(loadItem);
		GameFrame.setJMenuBar(menuBar);
		
		GameFrame.setSize(600,580);
		GameFrame.setLocationRelativeTo(null);
		GameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameFrame.setVisible(true);
		GameFrame.setResizable(false);
		
		GameFrame.repaint();

	}
	/**
	 * Constructor for MapEditor that passes the title to the superclass's constructor
	 * Creates Mouse ActionListener which calls changeBlock on event
	 */
	public MapEditor(){
		
		super("Gravity Prison Map Maker");
		
	}
	
	/**
	 * @param g - Graphics passed by a call in the superclass 
	 */
	public void paint(Graphics g) {
		
		Graphics2D page = (Graphics2D) g;
		
		BufferedImage tile = null;
		Image offscreen = createImage(getWidth(), getHeight());
		Graphics offPage = offscreen.getGraphics();
		
		/**
		 * Draws map array from current level object
		 * Steiner fixed the 2D array switch
		 */
		for (int y = 0; y < 10; y++){
			for (int x = 0; x < 10; x++){
				
				int tileValue = currentLevel.map[y][x];
				tile = tileSheet.getSubimage((tileValue % 3) * 25, (tileValue / 3) * 25, 25, 25);

				offPage.drawImage(tile, (50 * x) + 50, (50 * y) + 50, 50, 50, null);
				
			}
		}
		
		page.drawImage(offscreen, 0, 0, null);
	
	}
	
	/**
	 * Increments the value at an x and y index by one,
	 * limiting the value at 4 and wrapping to zero if necessary
	 * 
	 * precon - x and y are within the frame
	 * 
	 * @param x - the x location within JFrame of the block to change
	 * @param y - the y location
	 */
	public static void changeBlock(int x, int y, Boolean leftClick) {
		int i = tileSheet.getHeight() / 25;
		
		x = (x - 50) / 50;
		y = (y - 50) / 50;
		if( leftClick == true){
			currentLevel.map[y][x] ++;
		
			if (currentLevel.map[y][x] > (3 * i) - 1)
				currentLevel.map[y][x] = 0;
		} else {
			currentLevel.map[y][x] --;
			
			if (currentLevel.map[y][x] < 0)
				currentLevel.map[y][x] = (3 * i) - 1;
		}
		
	}
	
	/**
	 * ActionListener's for menuBar to save, open, and create new .lvl files.
	 * Opens a dialog box to receive file name s
	 * If s == null or s has a length of zero then the saving and loading is bypassed
	 */
	private static class NewClass implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			currentLevel = new Level();
			GameFrame.repaint();
			
		}
	}
	private static class SaveClass implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			String s =
					(String)JOptionPane.showInputDialog( GameFrame, "File name?", "Save",
							JOptionPane.PLAIN_MESSAGE, null, null, "Untitled");
			
			if ((s != null) && (s.length() > 0))
				Utils.saveLevel(currentLevel, s);
			
		}
	}
	
	private static class LoadClass implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			String s =
					(String)JOptionPane.showInputDialog( GameFrame, "File name?", "Load",
							JOptionPane.PLAIN_MESSAGE, null, null, "Untitled");
			
			if ((s != null) && (s.length() > 0))
				currentLevel = Utils.loadLevel(s);
			GameFrame.repaint();
			
		}
	}
	
	/**
	 * MouseListener for clicking on squares
	 * calls changeBlock with current x and y
	 */
	private static class mouseClass implements MouseListener {
		public void mousePressed(MouseEvent e) {
			int mouseButton = e.getButton();
			
			Graphics g = GameFrame.getGraphics();
			
            lastPoint = new Point(e.getX(), e.getY());
            changeBlock( lastPoint.x, lastPoint.y, ( mouseButton == 1 ) );
            GameFrame.repaint();
			
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			
		}
	}
	
	

}

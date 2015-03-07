package gameutil.level;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;

import finalgame.Level;
import gameutil.Utils;

/**
 * @author Ian Steiner and Thomas Rosebrough
 * Map Editor for final project game
 */
@SuppressWarnings("serial")
public class MapEditor extends JFrame {

	static Level currentLevel;
	static Point lastPoint;
	static MapEditor EditorFrame;
	
	static JMenuBar menuBar;
	static JMenu fileMenu, specialMenu;
	static public enum ListenerType {
	    NEW, SAVE, LOAD, TILE, SHUF
	}
	
	static BufferedImage tileSheet = null;
	
	static final int TILE_WIDTH = 25;
	static final int TILE_HEIGHT = 25;
	static int tileUnitWidth;
	
	public static void main(String[] args) {
		
		/**
		 * Initializes the JFrame Frame with size 800 x 600
		 * Creates Menu Bar with one menu and two items
		 */
		EditorFrame = new MapEditor();
		
		currentLevel = new Level();
				
		try {
			tileSheet = ImageIO.read(new File("assets/PipeMap.png"));
		} catch (IOException i) {
			i.printStackTrace();
		}
		
		tileUnitWidth = tileSheet.getWidth() / TILE_WIDTH;
				
		EditorFrame.setSize(600,580);
		EditorFrame.setLocationRelativeTo(null);
		EditorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		EditorFrame.setResizable(false);
		

		menuBar = new JMenuBar();
		EditorFrame.setJMenuBar(menuBar);
		fileMenu = new JMenu("File");
		specialMenu = new JMenu("Specials");
		
		EditorFrame.repaint();
		
		JMenuItem newItem = new JMenuItem("New");
		JMenuItem saveItem = new JMenuItem("Save");
		JMenuItem loadItem = new JMenuItem("Open");
		JMenuItem tileItem = new JMenuItem("Graphics");
		JMenuItem shuffleItem = new JMenuItem("Random Map");
		newItem.addActionListener(new ListenerClass(ListenerType.NEW));
		saveItem.addActionListener(new ListenerClass(ListenerType.SAVE));
		loadItem.addActionListener(new ListenerClass(ListenerType.LOAD));
		tileItem.addActionListener(new ListenerClass(ListenerType.TILE));
		shuffleItem.addActionListener(new ListenerClass(ListenerType.SHUF));
		EditorFrame.addMouseListener(new MouseClass());
		
		fileMenu.add(newItem);
		fileMenu.add(saveItem);
		fileMenu.add(loadItem);
		specialMenu.add(tileItem);
		specialMenu.add(shuffleItem);
		menuBar.add(fileMenu);
		menuBar.add(specialMenu);
		

		EditorFrame.setVisible(true);
		menuBar.setVisible(true);

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
	public void paint(Graphics page) {
		
		BufferedImage tile = null;
		Image offscreen = createImage(getWidth(), getHeight());
		Graphics offPage = offscreen.getGraphics();
		
		/**
		 * Draws map array from current level object
		 * Steiner fixed the 2D array search
		 */
		for (int y = 0; y < 10; y++){
			for (int x = 0; x < 10; x++){
				
				int tileValue = currentLevel.getTile(x, y);
				tile = tileSheet.getSubimage((tileValue % tileUnitWidth) * TILE_WIDTH, (tileValue / tileUnitWidth) * TILE_WIDTH, TILE_WIDTH, TILE_HEIGHT);

				offPage.drawImage(tile, (50 * x) + 50, (50 * y) + 50, 50, 50, this);
				
			}
		}
		
		page.drawImage(offscreen, 0, 0, this);
	
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
			currentLevel.setTile(x, y, currentLevel.getTile(x, y) + 1);
		
			if (currentLevel.getTile(x, y) > (3 * i) - 1)
				currentLevel.setTile(x, y, 0);
		} else {
			currentLevel.setTile(x, y, currentLevel.getTile(x, y) - 1);
			
			if (currentLevel.getTile(x, y) < 0)
				currentLevel.setTile(x, y, (3 * i) - 1);
		}
		
	}
	
	
	
	/**
	 * ActionListener's for menuBar to save, open, and create new .lvl files.
	 * Opens a dialog box to receive file name s
	 * If s == null or s has a length of zero then the saving and loading is bypassed
	 */
	private static class ListenerClass implements ActionListener {
		
		ListenerType type;
		
		public ListenerClass( ListenerType t ) {
			
			type = t;
			
		}
		
		public void actionPerformed(ActionEvent e) {
			
			if( type == ListenerType.NEW ) {
				
				currentLevel = new Level();
				EditorFrame.repaint();
				
			} else if( type == ListenerType.SAVE ) {
				
				String s = (String) JOptionPane.showInputDialog( EditorFrame, "File name?", "Save",
						JOptionPane.PLAIN_MESSAGE, null, null, currentLevel);
		
				if ((s != null) && (s.length() > 0)) {
					currentLevel.setName(s);
					Utils.saveLevel(currentLevel, s);
				}
				
			} else if( type == ListenerType.LOAD ) {
				
				String s = (String) JOptionPane.showInputDialog( EditorFrame, "File name?", "Load",
						JOptionPane.PLAIN_MESSAGE, null, null, "Untitled");
		
				if ((s != null) && (s.length() > 0))
					currentLevel = Utils.loadLevel(s);
				EditorFrame.repaint();
				
			} else if( type == ListenerType.TILE ) {
				
				Object[] possibilities = {"Circuit", "Pipe", "Prison"};
				
				String s = (String) JOptionPane.showInputDialog( EditorFrame, "Tile Map?", "Change Graphics",
						JOptionPane.PLAIN_MESSAGE, null, possibilities, "Pipe");
				
				try {
					tileSheet = ImageIO.read(new File("assets/" + s + "Map.png"));
					currentLevel.setSeries(s);
				} catch (IOException i) {
					i.printStackTrace();
				}
				
				EditorFrame.repaint();
				
			} else if( type == ListenerType.SHUF ) {
				
				 currentLevel.shuffle();
				
				EditorFrame.repaint();
				
			}
			
			
		}
	}
	
	/**
	 * MouseListener for clicking on squares
	 * calls changeBlock with current x and y
	 */
	private static class MouseClass implements MouseListener {
		public void mousePressed(MouseEvent e) {
			int mouseButton = e.getButton();
			lastPoint = new Point(e.getX(), e.getY());
			
			if (mouseButton == 2) {
				int temp = 0;
				
				String s = (String) JOptionPane.showInputDialog( EditorFrame, "Input tile index:", "Change Tile",
						JOptionPane.PLAIN_MESSAGE, null, null, "");
				if (s.length() > 0 && s.length() < 3)
					temp = Integer.parseInt(s);
				currentLevel.setTile((lastPoint.x - 50) / 50, (lastPoint.y  - 50) / 50, temp);
				
				EditorFrame.repaint();
				return;
				
			}
			
            changeBlock( lastPoint.x, lastPoint.y, ( mouseButton == 1 ) );
            EditorFrame.repaint();
			
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

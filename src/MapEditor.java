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
	static MapEditor EditorFrame;
	
	static JMenuBar menuBar;
	static JMenu fileMenu, specialMenu;
	static public enum lissnerType {
	    NEW, SAVE, LOAD, TILE, SHUF
	}
	
	static BufferedImage tileSheet = null;
	
	static final int TILEWIDTH = 25;
	static final int TILEHEIGHT = 25;
	static int tileUnitWidth;
	
	public static void main(String[] args) {
		
		/**
		 * Initializes the JFrame Frame with size 800 x 600
		 * Creates Menu Bar with one menu and two items
		 */
		EditorFrame = new MapEditor();
		
		currentLevel = new Level();
				
		try {
			tileSheet = ImageIO.read(new File("assets/PrisonMap.png"));
		} catch (IOException i) {
			i.printStackTrace();
		}
		
		tileUnitWidth = tileSheet.getWidth() / TILEWIDTH;
				
		EditorFrame.setSize(600,580);
		EditorFrame.setLocationRelativeTo(null);
		EditorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		EditorFrame.setVisible(true);
		EditorFrame.setResizable(false);
		

		menuBar = new JMenuBar();
		EditorFrame.setJMenuBar(menuBar);
		fileMenu = new JMenu("File");
		specialMenu = new JMenu("Specials");
		menuBar.add(fileMenu);
		menuBar.add(specialMenu);
		

		/**
		 * Creates instances of nested action listener classes for
		 * saving and loading files
		 */
		JMenuItem newItem = new JMenuItem("New");
		JMenuItem saveItem = new JMenuItem("Save");
		JMenuItem loadItem = new JMenuItem("Open");
		JMenuItem tileItem = new JMenuItem("Graphics");
		JMenuItem shuffleItem = new JMenuItem("Random Map");
		newItem.addActionListener(new ListenerClass(lissnerType.NEW));
		saveItem.addActionListener(new ListenerClass(lissnerType.SAVE));
		loadItem.addActionListener(new ListenerClass(lissnerType.LOAD));
		tileItem.addActionListener(new ListenerClass(lissnerType.TILE));
		shuffleItem.addActionListener(new ListenerClass(lissnerType.SHUF));
		EditorFrame.addMouseListener(new MouseClass());
		
		fileMenu.add(newItem);
		fileMenu.add(saveItem);
		fileMenu.add(loadItem);
		specialMenu.add(tileItem);
		specialMenu.add(shuffleItem);
		
		EditorFrame.repaint();

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
		 * Steiner fixed the 2D array switch
		 */
		for (int y = 0; y < 10; y++){
			for (int x = 0; x < 10; x++){
				
				int tileValue = currentLevel.map[y][x];
				tile = tileSheet.getSubimage((tileValue % tileUnitWidth) * TILEWIDTH, (tileValue / tileUnitWidth) * TILEWIDTH, TILEWIDTH, TILEHEIGHT);

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
	private static class ListenerClass implements ActionListener {
		
		lissnerType type;
		
		public ListenerClass( lissnerType t ){
			
			type = t;
			
		}
		
		public void actionPerformed(ActionEvent e) {
			
			if( type == lissnerType.NEW ) {
				
				currentLevel = new Level();
				EditorFrame.repaint();
				
			} else if( type == lissnerType.SAVE ) {
				
				String s = (String) JOptionPane.showInputDialog( EditorFrame, "File name?", "Save",
						JOptionPane.PLAIN_MESSAGE, null, null, currentLevel);
		
				if ((s != null) && (s.length() > 0)) {
					currentLevel.setName(s);
					Utils.saveLevel(currentLevel, s);
				}
				
			} else if( type == lissnerType.LOAD ) {
				
				String s = (String) JOptionPane.showInputDialog( EditorFrame, "File name?", "Load",
						JOptionPane.PLAIN_MESSAGE, null, null, "Untitled");
		
				if ((s != null) && (s.length() > 0))
					currentLevel = Utils.loadLevel(s);
				EditorFrame.repaint();
				
			} else if( type == lissnerType.TILE ) {
				
				Object[] possibilities = {"Prison", "Circuit"};
				
				String s = (String) JOptionPane.showInputDialog( EditorFrame, "Tile Map?", "Change Graphics",
						JOptionPane.PLAIN_MESSAGE, null, possibilities, "Prison");
				
				try {
					tileSheet = ImageIO.read(new File("assets/" + s + "Map.png"));
				} catch (IOException i) {
					i.printStackTrace();
				}
				
				EditorFrame.repaint();
				
			} else if( type == lissnerType.SHUF ) {
				
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
			
			Graphics g = EditorFrame.getGraphics();
			
            lastPoint = new Point(e.getX(), e.getY());
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

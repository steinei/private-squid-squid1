import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;

/**
 * @author Ian Steiner and Thomas Rosebrough
 * Map Editor for final project game
 */
public class MapEditor extends JFrame {

	static Level currentLevel;
	static Point lastPoint;
	
	public static void main(String[] args) {
		
		/**
		 * Initializes the JFrame Frame with size 800 x 600
		 * Creates Menu Bar with one menu and two items
		 */
		MapEditor Frame = new MapEditor();
		
		currentLevel = new Level();
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenuItem saveItem = new JMenuItem("Save");
		JMenuItem loadItem = new JMenuItem("Load");
		fileMenu.add(saveItem);
		fileMenu.add(loadItem);
		Frame.setJMenuBar(menuBar);
		
		Frame.setSize(800,600);
		Frame.setLocationRelativeTo(null);
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Frame.setVisible(true);
		Frame.setResizable(false);
		
		/**
		 * ActionListener's for menuBar to save and load .lvl files.
		 * Opens a dialog box to recieve file name s
		 * If s == null or s has a length of zero then the saving and loading is bypassed
		 */
		saveItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						String s =
								(String)JOptionPane.showInputDialog( null, "File name?", "Save",
										JOptionPane.PLAIN_MESSAGE, null, null, "Untitled");
						
						if ((s != null) && (s.length() > 0))
							Utils.saveLevel(currentLevel, s);
						
					}
		});
		
		loadItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						String s =
								(String)JOptionPane.showInputDialog( null, "File name?", "Load",
										JOptionPane.PLAIN_MESSAGE, null, null, "Untitled");
						
						if ((s != null) && (s.length() > 0))
							currentLevel = Utils.loadLevel(s);
						
					}
		});
		

	}
	/**
	 * Constructor for MapEditor that passes the title to the superclass's constructor
	 * Creates Mouse ActionListener which calls changeBlock on event
	 */
	public MapEditor(){
		
		super("Game Frame No Name Take Blame No Shame");
		
		addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
            	
            	Graphics g = getGraphics();
                lastPoint = new Point(e.getX(), e.getY());
                changeBlock( lastPoint.x, lastPoint.y );
                repaint();
                
            }
        });
		
	}
	
	/**
	 * @param g - Graphics passed by a call in the superclass 
	 */
	public void paint(Graphics g) {
		
		Graphics2D page = (Graphics2D) g;
		
		for (int i = 0; i <= 10; i++){
			for (int j = 0; j <= 10; j ++){
				
				switch (currentLevel.map[i][j]){
				case 0:
					page.setColor(Color.GREEN);
					break;
				case 1:
					page.setColor(Color.RED);
					break;
				case 3:
					page.setColor(Color.BLUE);
					break;
				case 4:
					page.setColor(Color.WHITE);
					break;
				}
				
				page.draw(new Rectangle2D.Double((50 * i), (50 * j), 50, 50));
				
			}
		}
	
	}
	
	/**
	 * Increments the value at an x and y index by one,
	 * limiting the value at 4 and wraping to zero if neccessary
	 * @param x - the x index of the block to change
	 * @param y - the y index
	 */
	public static void changeBlock(int x, int y) {
		
		x /= 50;
		y /= 50;
		
		currentLevel.map[x][y] ++;
		if (currentLevel.map[x][y] >= 5)
			currentLevel.map[x][y] = 0;
		
	}
	
	

}

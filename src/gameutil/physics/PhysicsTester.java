package gameutil.physics;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;

import finalgame.*;
import gameutil.Utils;

@SuppressWarnings("serial")
public class PhysicsTester extends JFrame{
	
	static Image bfImage = null;
	static double angle = 0;
	
	static JMenuBar menuBar;
	static JMenu fileMenu;
	
	
	static final int TILE_WIDTH = 25;
	static final int TILE_HEIGHT = 25;
	static int tileUnitWidth;
	
	static PhysicsTester PhysFrame;
	static Player player = new Player();
	static Level currentLevel = Utils.loadLevel("Random Level");
	static BufferedImage tileSheet = null;
	
	public PhysicsTester(){
		super("Physics Tester");
	}
	  
	 /**
	  * @param args
	  * Currently, intended to create a BufferedImage on screen that can be rotated by clicking/dragging the mouse
	  *  in a JFrame 800 * 600
	  */
	 public static void main(String[] args) {
	  
	  PhysFrame = new PhysicsTester();
	  try {
			tileSheet = ImageIO.read(new File("assets/" + currentLevel.getSeries() + "Map.png"));
	  } catch (IOException i) {
			i.printStackTrace();
	  }
	  tileUnitWidth = tileSheet.getWidth() / TILE_WIDTH;
	  
	  try {
		  	bfImage = ImageIO.read(new File("assets/plato.jpg"));
	  } catch (IOException i) {
		  	i.printStackTrace();
	  }
	  
	  PhysFrame.setSize(800,600);
	  PhysFrame.setLocationRelativeTo(null);
	  PhysFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  PhysFrame.setVisible(true);
	  PhysFrame.setResizable(false);
	  
	  menuBar = new JMenuBar();
	  fileMenu = new JMenu("File");
	  
	  JMenuItem loadItem = new JMenuItem("Open");
	  loadItem.addActionListener(new GameListenerClass());
	  
	  fileMenu.add(loadItem);
	  menuBar.add(fileMenu);
	  PhysFrame.setJMenuBar(menuBar);
	  menuBar.setVisible(true);
	  
	 }
	 
	 
	 /**
	  * @param Graphics g
	  * Draws the level currently loaded and allows it to be rotated by clicking the mouse
	  */
	 public void paint(Graphics page) {
		 
		 double slope;
		 BufferedImage tile = null;
		 Image offscreen = createImage(getWidth(), getHeight());
		 Graphics2D offPage = (Graphics2D) offscreen.getGraphics();
		 offPage.drawLine(0, 300, 800, 600);
		 if (player.getY() - 300 * player.getX() / 800 >= 298){
			 player.hitGround();
			 player.setNormalForce(new Vector(.0017556, .00434)); // angle isn't working right now
		 }
		 player.updatePhysics();
		 offPage.drawString("" + player.getNetForce().getDirection(), 700, 50);
		 offPage.drawString("" + player.getNetForce().getMagnitude(), 700, 75);
		 offPage.drawImage(bfImage, (int) player.getX() - 50, (int) player.getY() - 50, 50, 50, this);
		 page.drawImage(offscreen, 0, 0, this);
		
		 repaint();
		
	 }
	  
	 /**
	 * ActionListener for menuBar to open .lvl files.
	 * Opens a dialog box to receive file name s
	 * If s == null or s has a length of zero then the saving and loading is bypassed
	 */
	 private static class GameListenerClass implements ActionListener {
			
		 public GameListenerClass() {
				
		 }
			
		 public void actionPerformed(ActionEvent e) {
					
			 String s = (String) JOptionPane.showInputDialog( PhysFrame, "File name?", "Load",
					 JOptionPane.PLAIN_MESSAGE, null, null, "Untitled");
			
			 if ((s != null) && (s.length() > 0))
				 currentLevel = Utils.loadLevel(s);
			 try {
				 tileSheet = ImageIO.read(new File("assets/" + currentLevel.getSeries() + "Map.png"));
			 } catch (IOException i) {
				 i.printStackTrace();
			 }
			 PhysFrame.repaint();
		 }
	 }

}
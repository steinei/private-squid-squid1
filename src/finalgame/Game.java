package finalgame;

import gameutil.Utils;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;
	
	/**
	 * @author Ian Steiner and Tommy Rosebrough
	 *
	 */
@SuppressWarnings("serial")
public class Game extends JFrame{
	
	static Image bfImage = null;
	static Point lastPoint, nextPoint, firstPoint;
	static final Point Origin  = new Point(400,300);
	static double angle = 0;
	
	static JMenuBar menuBar;
	static JMenu fileMenu;
	
	static boolean mouseIsPressed = false;
	static boolean firstTime = true;
	static boolean mouseError = false;
	
	static final double FPS = 60;
	
	static final int RECTANGLE_HEIGHT = 400, RECTANGLE_WIDTH = 300, BAR_WIDTH = 700;
	static final int TILE_WIDTH = 25;
	static final int TILE_HEIGHT = 25;
	static int tileUnitWidth;
	
	static Game GameFrame;
	static Level currentLevel = Utils.loadLevel("Random Level");
	static BufferedImage tileSheet = null;
	
	public Game(){
		super("Gravity Prison");
	}
	  
	 /**
	  * @param args
	  * Currently, intended to create a BufferedImage on screen that can be rotated by clicking/dragging the mouse
	  *  in a JFrame 800 * 600
	  */
	 public static void main(String[] args) {
	  
	  GameFrame = new Game();
	  try {
			tileSheet = ImageIO.read(new File("assets/" + currentLevel.getSeries() + "Map.png"));
	  } catch (IOException i) {
			i.printStackTrace();
	  }
	  tileUnitWidth = tileSheet.getWidth() / TILE_WIDTH;
	  
	  try {
		  	bfImage = ImageIO.read(new File("assets/archimedes.jpg"));
	  } catch (IOException i) {
		  	i.printStackTrace();
	  }
	  
	  GameFrame.setSize(800,600);
	  GameFrame.setLocationRelativeTo(null);
	  GameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  GameFrame.setVisible(true);
	  GameFrame.setResizable(false);
	  GameFrame.addMouseListener(new mouseClass());
	  
	  menuBar = new JMenuBar();
	  fileMenu = new JMenu("File");
	  
	  JMenuItem loadItem = new JMenuItem("Open");
	  loadItem.addActionListener(new GameListenerClass());
	  
	  fileMenu.add(loadItem);
	  menuBar.add(fileMenu);
	  GameFrame.setJMenuBar(menuBar);
	  menuBar.setVisible(true);
	  
	 }
	 
	 /**
	  * @param Graphics g
	  * Draws the level currently loaded and allows it to be rotated by clicking the mouse
	  */
	 public void paint(Graphics page) {
		 
		 long startTime = System.currentTimeMillis();
	 	 
		 BufferedImage tile = null;
		 Image offscreen = createImage(getWidth(), getHeight());
		 Graphics2D offPage = (Graphics2D) offscreen.getGraphics();
		 if (mouseIsPressed){
			 
			 firstTime = false;
			 
			 try {
				 nextPoint = new Point(GameFrame.getMousePosition());
			 } catch (NullPointerException i) {
				 if (!mouseError){
					 System.err.println("Mouse out of bounds case caught.");
					 mouseError = true;
				 }
			 }
			 if (GameFrame.getMousePosition() != null){
				 mouseError = false;
			 }
			 
			 //offPage.clearRect(0, 0, 800, 600);
			 //offPage.drawString("" + nextPoint.x + "    " + nextPoint.y + "    " + angle + "    " + lastPoint.x + "    " + lastPoint.y, 50, 500);
			 angle = ((nextPoint.x - Origin.x)*2.0/BAR_WIDTH)*2*Math.PI;
			 if (angle > Math.PI*2){
				 angle = Math.PI*2;
			 }
			 else if (angle < Math.PI*-2){
				 angle = -Math.PI*2;
			 }
			 //offPage.drawString("" + angle, 50, 560);
			 //offPage.translate(Origin.x, Origin.y);
			 offPage.drawString("" + angle, 600, 50);
			 offPage.rotate(angle, 400, 300);
			 
			 for (int y = 0; y < 10; y++){
					for (int x = 0; x < 10; x++){
						
						int tileValue = currentLevel.getTile(x, y);
						tile = tileSheet.getSubimage((tileValue % tileUnitWidth) * TILE_WIDTH, (tileValue / tileUnitWidth) * TILE_WIDTH, TILE_WIDTH, TILE_HEIGHT);

						offPage.drawImage(tile, (50 * x) + 150, (50 * y) + 50, 50, 50, this);
						
					}
			 }
		 }
		 else{
			 offPage.rotate(angle, 400, 300);
			 for (int y = 0; y < 10; y++){
					for (int x = 0; x < 10; x++){
						
						int tileValue = currentLevel.getTile(x, y);
						tile = tileSheet.getSubimage((tileValue % tileUnitWidth) * TILE_WIDTH, (tileValue / tileUnitWidth) * TILE_WIDTH, TILE_WIDTH, TILE_HEIGHT);

						offPage.drawImage(tile, (50 * x) + 150, (50 * y) + 50, 50, 50, this);
						
					}
				}
		 }
		 if(firstTime){
			 
			 for (int y = 0; y < 10; y++){
					for (int x = 0; x < 10; x++){
						
						int tileValue = currentLevel.getTile(x, y);
						tile = tileSheet.getSubimage((tileValue % tileUnitWidth) * TILE_WIDTH, (tileValue / tileUnitWidth) * TILE_WIDTH, TILE_WIDTH, TILE_HEIGHT);

						offPage.drawImage(tile, (50 * x) + 150, (50 * y) + 50, 50, 50, this);
						
					}
				}
		 }
		 page.drawImage(offscreen, 0, 0, this);
		 
		 double wait = 1000 / FPS;
		 long endTime   = System.currentTimeMillis();
		 long totalTime = endTime - startTime;
		 if (totalTime < wait) {
			 try {
				Thread.sleep((long) (wait - totalTime));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
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
					
				String s = (String) JOptionPane.showInputDialog( GameFrame, "File name?", "Load",
							JOptionPane.PLAIN_MESSAGE, null, null, "Untitled");
			
				if ((s != null) && (s.length() > 0))
					currentLevel = Utils.loadLevel(s);
				try {
					tileSheet = ImageIO.read(new File("assets/" + currentLevel.getSeries() + "Map.png"));
			  } catch (IOException i) {
					i.printStackTrace();
			  }
				GameFrame.repaint();
				
			}
		}
	 
	  /** 
	   * mouseListener for dragging rectangle
	   * changes whether mousePressed is true or false
	   */
	  private static class mouseClass implements MouseListener {
		  public void mousePressed(MouseEvent e) {
		   
			  lastPoint = e.getPoint();
			  firstPoint = e.getPoint();
			  mouseIsPressed = true;
			  System.out.println("Mouse is pressed");
			  GameFrame.repaint();
	    
	   }
	
	   public void mouseClicked(MouseEvent arg0) {
	    
	   }
	
	   public void mouseEntered(MouseEvent arg0) {
	    
	   }
	
	   public void mouseExited(MouseEvent arg0) {
	    
	   }
	
	   @Override
	   public void mouseReleased(MouseEvent e) {
		   
		   lastPoint = e.getPoint();
		   mouseIsPressed = false;
		   System.out.println("Mouse is unpressed");
	   
	   }
	  }
	  
} 
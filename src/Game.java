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
	static Point lastPoint, nextPoint, firstPoint, referencePoint;
	static final Point Origin  = new Point(400,300);
	static double angle = 0;
	
	static boolean mouseIsPressed = false;
	static boolean firstTime = true;
	
	static final int RECTANGLE_HEIGHT = 400, RECTANGLE_WIDTH = 300, BAR_WIDTH = 600;
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
	  * Currently, intended to create a BufferedImage on screen that can be rotated by dragging the mouse
	  *  in a JFrame 800 * 600
	  */
	 public static void main(String[] args) {
	  
	  GameFrame = new Game();
	  try {
			tileSheet = ImageIO.read(new File("assets/PrisonMap.png"));
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
	 /**
	  * @param Graphics g
	  */
	 public void paint(Graphics page) {
		 
		 
		 BufferedImage tile = null;
		 Image offscreen = createImage(getWidth(), getHeight());
		 Graphics2D offPage = (Graphics2D) offscreen.getGraphics();
			
		 if (mouseIsPressed){
			 
			 firstTime = false;
			 
			 try {
				 nextPoint = new Point(GameFrame.getMousePosition());
			 } catch (NullPointerException i) {
				 System.err.println("Mouse out of bounds case caught.");
			 }
			 
			 
			 //offPage.clearRect(0, 0, 800, 600);
			 //offPage.drawString("" + nextPoint.x + "    " + nextPoint.y + "    " + angle + "    " + lastPoint.x + "    " + lastPoint.y, 50, 500);
			 angle = ((nextPoint.x - Origin.x)*2.0/BAR_WIDTH)*2*Math.PI;
			 //offPage.drawString("" + angle, 50, 560);
			 //offPage.translate(Origin.x, Origin.y);
			 offPage.rotate(angle, 250, 250);
			 
			 for (int y = 0; y < 10; y++){
					for (int x = 0; x < 10; x++){
						
						int tileValue = currentLevel.map[y][x];
						tile = tileSheet.getSubimage((tileValue % tileUnitWidth) * TILE_WIDTH, (tileValue / tileUnitWidth) * TILE_WIDTH, TILE_WIDTH, TILE_HEIGHT);

						offPage.drawImage(tile, (50 * x) + 50, (50 * y) + 50, 50, 50, this);
						
					}
				}
			 
			 lastPoint = new Point(nextPoint);

		}
		 if(firstTime){
			 
			 for (int y = 0; y < 10; y++){
					for (int x = 0; x < 10; x++){
						
						int tileValue = currentLevel.map[y][x];
						tile = tileSheet.getSubimage((tileValue % tileUnitWidth) * TILE_WIDTH, (tileValue / tileUnitWidth) * TILE_WIDTH, TILE_WIDTH, TILE_HEIGHT);

						offPage.drawImage(tile, (50 * x) + 50, (50 * y) + 50, 50, 50, this);
						
					}
				}
		 }
		 
		page.drawImage(offscreen, 0, 0, this);
		
	 }
} 
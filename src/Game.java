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
	
	static final int RECTANGLE_HEIGHT = 400, RECTANGLE_WIDTH = 300, BAR_WIDTH = 600;
	static boolean firstTime = true;
	
	static Game GameFrame;
	static Level currentLevel;
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
	 public void paint(Graphics g) {
		 mousePressedLoop(g);
	 }
	 
	 public static void mousePressedLoop(Graphics g){
		 
		 Graphics2D page = (Graphics2D) g;
		 if (mouseIsPressed){
			 
			 firstTime = false;
			 
			 try {
				 nextPoint = new Point(GameFrame.getMousePosition());
			 } catch (NullPointerException i) {
				 System.err.println("Mouse out of bounds case caught.");
			 }
			 
			 
			 page.clearRect(0, 0, 800, 600);
			 page.drawString("" + nextPoint.x + "    " + nextPoint.y + "    " + angle + "    " + lastPoint.x + "    " + lastPoint.y, 50, 500);
			 angle = ((nextPoint.x - Origin.x)*2.0/BAR_WIDTH)*2*Math.PI;
			 page.drawString("" + angle, 50, 560);
			 page.translate(Origin.x, Origin.y);
			 page.rotate(angle);
			 
			 page.drawImage(bfImage, -RECTANGLE_WIDTH/2, -RECTANGLE_HEIGHT/2, RECTANGLE_WIDTH, RECTANGLE_HEIGHT, null);
			 lastPoint = new Point(nextPoint);

		}
		 if(firstTime){
			 page.drawImage(bfImage, 250, 100, 300, 400, null);
		 }
		 
		GameFrame.repaint();
		
	 }
} 
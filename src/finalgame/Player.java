package finalgame;

import gameutil.physics.*;

public class Player implements Gravity {
	
	private Vector velocity;
	private Vector normal;
	private double xPos;
	private double yPos;
	
	public Player() {
		
		velocity = new Vector();
		xPos = 40;
		yPos = 20;
		
	}
	
	public double getX() {
		
		return xPos;
		
	}

	public double getY() {
		
		return yPos;
		
	}
	
	public void setPosition(int x, int y) {
		
		xPos = x;
		yPos = y;
		
	}
	
	public void updatePhysics(){
		
		xPos += velocity.getMagnitude() * Math.cos(velocity.getDirection());
		yPos -= velocity.getMagnitude() * Math.sin(velocity.getDirection());
		
	}

}
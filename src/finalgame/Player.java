package finalgame;

import gameutil.physics.*;

public class Player implements Gravity {
	
	private final Vector GRAVITY = new Vector(Math.PI * 3/2, .005);
	private Vector velocity;
	private Vector normalForce; //other forces to be added
	private Vector netForce;
	private double xPos;
	private double yPos;
	
	public Player() {
		
		velocity = new Vector();
		xPos = 100;
		yPos = 100;
		netForce = new Vector();
		normalForce = new Vector();
		
	}
	
	public double getX() {
		
		return xPos;
		
	}

	public double getY() {
		
		return yPos;
		
	}
	
	public Vector getNetForce(){
		
		return netForce;
		
	}
	
	public void setPosition(int x, int y) {
		
		xPos = x;
		yPos = y;
		
	}
	public void setNormalForce(Vector v){
		
		normalForce = new Vector(v.getDirection(), v.getMagnitude());
		
	}
	
	public void hitGround(){
		
		velocity = new Vector();
		
	}
	
	/**
	 * updates the net force on the player with gravity, normal force, and any others 
	 */
	public void updateNetForce(){
		netForce = new Vector();
		netForce.addVector(GRAVITY);
		netForce.addVector(normalForce);
	}
	
	public void updatePhysics(){
		updateNetForce();
		velocity.addVector(netForce);
		xPos += velocity.getMagnitude() * Math.cos(velocity.getDirection());
		yPos -= velocity.getMagnitude() * Math.sin(velocity.getDirection());
	}

}
package finalgame;

import gameutil.physics.*;

public class Player implements Gravity {
	
	private final Vector GRAVITY = new Vector(0, -.005);
	private Vector velocity;
	private Vector normalForce; //other forces to be added
	private Vector netForce;
	private double xPos;
	private double yPos;
	private boolean onGround = false;
	
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
		
		normalForce = v;
		
	}
	
	public void hitGround(){
		
		if (!onGround){
			onGround = true;
			velocity = new Vector();
		}
		
	}
	
	/**
	 * updates the net force on the player with gravity, normal force, and any others 
	 */
	public void updateNetForce(){
		netForce = new Vector();
		netForce.addVector(normalForce);
		netForce.addVector(GRAVITY);
	}
	
	public void updatePhysics(){
		updateNetForce();
		velocity.addVector(netForce);
		xPos += velocity.getX();
		yPos -= velocity.getY();
	}

}
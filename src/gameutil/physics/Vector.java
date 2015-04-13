package gameutil.physics;

import java.lang.Math;

public class Vector {
	
	private double x,
			y,
			direction,
			magnitude;
	
	public Vector() {
		
		x = 0;
		y = 0;
		
		
	}

	public Vector(double xIn, double yIn) {
		
		x = xIn;
		y = yIn;
		
	}
	
	public double getX() {
		
		return x;
		
	}
	
	public double getY() {
		
		return y;
		
	}
	
	public double getDirection() {
		
		return direction;
		
	}
	
	public double getMagnitude() {
		
		return magnitude;
		
	}
	
	public void setX(double xIn) {
		
		x = xIn;
		
	}
	
	public void setY(double yIn) {
		
		y = yIn;
		
	}
	
	public void setDirection(double d) {
		
		direction = d;
		
	}
	
	public void setMagnitude(double m) {
		
		magnitude = m;
		
	}
	
	public Vector addVector( Vector v ) {
		
		x += v.getX();
		y += v.getY();
		
		update();
		
		return this;
		
	}
	
	public Vector addVector( double xIn, double yIn) {
		
		x += xIn;
		y += yIn;
		
		update();
		
		return this;
		
	}
	
	/*public Vector addVector( Vector v ) {
		double xComp = magnitude * Math.cos(direction);
		double yComp = magnitude * Math.sin(direction);
		double xAdd = v.getMagnitude() * Math.cos(v.getDirection());
		double yAdd = v.getMagnitude() * Math.sin(v.getDirection());
		
		double xSum = xComp + xAdd;
		double ySum = yComp + yAdd;
		
		direction = Math.atan2(ySum, xSum);
		direction %= (2 * Math.PI);
		
		if (Math.cos(direction) > .2){
			
			magnitude = xSum / Math.cos(direction);
			
		}
		else{
			
			magnitude = ySum / Math.sin(direction);
			
		}
		
		
		return this;
		
	}
	
	public Vector addVector( double d, double m ) {
		double xComp = magnitude * Math.cos(direction);
		double yComp = magnitude * Math.sin(direction);
		double xAdd = m * Math.cos(d);
		double yAdd = m * Math.sin(d);
		
		double xSum = xComp + xAdd;
		double ySum = yComp + yAdd;
		
		direction = Math.atan2(ySum, xSum);
		direction %= (2 * Math.PI);		
		
		magnitude = xSum / Math.cos(direction);
		
		return this;
		
	}*/
	
	public double angleAbs(double a) {
		
		if (a > Math.PI)
			return (2 * Math.PI) - a;
		if(a < -Math.PI)
			return  (2 * Math.PI) - Math.abs(a);
		if (a < 0)
			return Math.abs(a);	
		return a;
		
	}
	
	public void update() {
		
		direction = Math.atan2(y, x);
		direction += Math.PI * 2;
		direction %= (2 * Math.PI);
			
		magnitude = Math.sqrt(Math.pow(x,2) + Math.pow(y, 2));
		
	}
	
	public String toString() {
		
		return "" + magnitude + "      " + direction/Math.PI + "*PI"; //should be direction then magnitude for consistency
												  					  //but is currently flipped for testing
	}

}

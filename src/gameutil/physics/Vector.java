package gameutil.physics;

import java.lang.Math;

public class Vector {
	
	private double direction;
	private double magnitude;
	
	public Vector() {
		
		direction = 0;
		magnitude = 0;
		
	}

	public Vector(double d, double m) {
		
		direction = d;
		magnitude = m;
		
	}
	
	public double getDirection() {
		
		return direction;
		
	}
	
	public double getMagnitude() {
		
		return magnitude;
		
	}
	
	public void setDirection(double d) {
		
		direction = d;
		
	}
	
	public void setMagnitude(double m) {
		
		magnitude = m;
		
	}
	
	public Vector addVector( Vector v ){
		double xComp = magnitude * Math.cos(direction);
		double yComp = magnitude * Math.sin(direction);
		double xAdd = v.getMagnitude() * Math.cos(v.getDirection());
		double yAdd = v.getMagnitude() * Math.sin(v.getDirection());
		
		double xSum = xComp + xAdd;
		double ySum = yComp + yAdd;
		
		direction = Math.atan2(ySum, xSum);
		direction %= (2 * Math.PI);
		
		magnitude = xSum / Math.cos(direction);
		
		return this;
		
	}
	
	public Vector addVector( double d, double m ){
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
		
	}
	
	public double angleAbs(double a){
		
		if (a > Math.PI)
			return (2 * Math.PI) - a;
		if(a < -Math.PI)
			return  (2 * Math.PI) - Math.abs(a);
		if (a < 0)
			return Math.abs(a);	
		return a;
		
	}

}

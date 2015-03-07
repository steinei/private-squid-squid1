package finalgame;

import java.util.*;


/**
 * @author Ian Steiner and Tommy Rosebrough
 *
 */	
@SuppressWarnings("serial")
public class Level implements java.io.Serializable {
	
	private Random rando = new Random();
	private String name = "Untitled";
	private String series = "Pipe";
	
	public int[][] savedMap = new int[10][10];
	public int[][] activeMap = new int[10][10];
	
	public Level() {
		
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				
				setTile(i, j, 0);
				
			}
		}
		
	}
	
	public int getTile(int x, int y) {
		
		return savedMap[y][x];
		
	}
	
	public int readTile(int x, int y) {
		
		return activeMap[y][x];
		
	}
	
	public String getSeries() {
		
		return series;
		
	}
	
	/**
	 * Sets a value on the 2D array at a point
	 * precon - i is greater than 0
	 * 
	 * @param x - item in the array to be changed
	 * @param y - array in the 2D array to be changed
	 * @param i - value
	 */
	public void setTile(int x, int y, int i){
		
		savedMap[y][x] = i;
		
	}
	
	public void changeTile(int x, int y, int i){
		
		activeMap[y][x] = i;
		
	}
	
	public void setName(String s) {
		
		name = s;
	
	}
	
	public void setSeries(String s) {
		
		series = s;
	
	}
	
	public void shuffle() {
		
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				
				int temp = rando.nextInt(17);
				
				setTile(i, j, temp);
				
			}
		}
		
	}
	
	public String toString() {
		
		return name;
		
	}

}

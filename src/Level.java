import java.util.*;


/**
 * @author Ian Steiner and Tommy Rosebrough
 *
 */	
public class Level implements java.io.Serializable {
	
	private Random rando = new Random();
	public String name = "Untitled";
	
	int[][] map = new int[10][10];
	
	public Level() {
		
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				
				setTile(i, j, 0);
				
			}
		}
		
	}
	
	public int getTile(int x, int y) {
		
		return map[y][x];
		
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
		
		map[y][x] = i;
		
	}
	
	public void setName(String s) {
		
		name = s;
	
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


/**
 * @author Ian Steiner and Tommy Rosebrough
 *
 */	
public class Level implements java.io.Serializable {
	
	/**
	 * 
	 */
	public String name = "";
	
	int[][] map = new int[10][10];
	
	/**
	 * 
	 */
	public Level() {
		
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				
				map[i][j] = 0;
				
			}
		}
		
	}
	
	public String toString() {
		
		return name;
		
	}

}

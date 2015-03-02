
import java.io.*;

/**
 * @author Ian Steiner and Tommy Rosebrough
 *
 */
public class Utils {

	public static void saveLevel(Object o, String s){
		
		try
	      {
	         FileOutputStream fileOut = new FileOutputStream("levels/" + s + ".lvl");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(o);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in levels/" + s + ".lvl");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
		
	}
	
	public static Level loadLevel(String name){
		
		Level temp;
		
		try
	      {
	         FileInputStream fileIn = new FileInputStream("levels/" + name + ".lvl");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         temp = (Level) in.readObject();
	         in.close();
	         fileIn.close();
	         return temp;
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return null;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Level class not found");
	         c.printStackTrace();
	         return null;
	      }
		}
		
}
	


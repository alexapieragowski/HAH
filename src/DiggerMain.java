import java.io.*;
import javax.swing.JFrame;


//Please let this work
public class DiggerMain extends JFrame {
	public Level current_level;
	
	
	
	
	
	
	
	
	
	
	/*
	 * Placeholder for now, once the game window is written it will replace the Level in the game window with the load.
	 */
	public void loadLevel(String FileName){
		Level load=null;
		try{
			FileInputStream filein = new FileInputStream("Saves/"+FileName);
			ObjectInputStream in = new ObjectInputStream(filein);
			load=(Level) in.readObject();
			in.close();
			filein.close();
		}catch(IOException e){
			System.out.println("he class Level has been changed since this was saved, please update saves.");
			throw new IllegalStateException(e);
		}catch(ClassNotFoundException e){
			System.out.println("File not found.");
			throw new IllegalStateException(e);
		}
		current_level=load;
		repaint();
	}
}

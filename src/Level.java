import java.io.*;
import javax.swing.*;



public class Level extends JFrame implements Serializable{
	
	
	
	
	public void saveLevel(String FileName){
		try{
			FileOutputStream fileout = new FileOutputStream("Saves/"+FileName);
			ObjectOutputStream out = new ObjectOutputStream(fileout);
			out.writeObject(this);
			out.close();
			fileout.close();
		}catch(IOException e){
			throw new IllegalStateException(e);
		}
	}
}

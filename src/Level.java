import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JFrame;



public class Level extends JFrame implements Serializable{
//	Entity name[][]= new Entity[16][16];
//	
//	for (int i = 0; i < 16; i++) {
//        for (int j = 0; j < 16; j++) {
//        	add(Entity)
//        }
//	}
//	public void level(){
//		
//	}
	
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

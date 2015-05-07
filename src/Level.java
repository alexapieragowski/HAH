import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JPanel;



public class Level extends JPanel{
	private DiggerMain dm;
	private int gameSize = 16;
	Entity entities[][]= new Entity[gameSize][gameSize];
	KeyEvent e;
	
	public Level(DiggerMain dm){
		this.dm=dm;
		setLayout(new GridLayout(gameSize, gameSize, 0, 0));
		setOpaque(true);
		setBackground(Color.black);
		for (int i = 0; i < gameSize; i++) {
	        for (int j = 0; j < gameSize; j++) {
	        	entities[i][j] = new Entity(Color.gray,0,dm);//Fills the game with "Dirt"
	        	add(entities[i][j]);    	
	        }
		}
		repaint();
	}
	
	public void addHero(int x, int y) { //Puts a hero at the chosen location, for initial level setup.
		remove(x+gameSize*y);
		entities[x][y]= new Entity(Color.blue,0,dm);//TODO make this a hero
		add(entities[x][y],x+gameSize*y);
		entities[x][y].addKeyListener(dm.keyListener);
		repaint();
	}
	public void addEmerald(int x, int y) { //Puts a Emerald at the chosen location, for initial level setup.
		remove(x+gameSize*y);
		entities[x][y]= new Entity(Color.green,100,dm);
		add(entities[x][y],x+gameSize*y);
		repaint();
	}
	
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
	
	public void move(int dx, int dy){
		Hero.heroMovement(e);
	}
}

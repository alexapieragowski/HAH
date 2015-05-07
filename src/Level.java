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
	        	entities[j][i] = new Entity(Color.gray,0,dm, j, i);//Fills the game with "Dirt"
	        	add(entities[j][i]);    	
	        }
		}
		repaint();
	}
	
	public void addHero(int x, int y) { //Puts a hero at the chosen location, for initial level setup.
		remove(x+gameSize*y);
		entities[x][y]= new Hero(dm, x, y);
		add(entities[x][y],x+gameSize*y);
//		entities[x][y].addKeyListener((Hero)entities[x][y]);
		repaint();
	}
	public void addEmerald(int x, int y) { //Puts a Emerald at the chosen location, for initial level setup.
		remove(x+gameSize*y);
		entities[x][y]= new Entity(Color.green,100,dm, x, y);
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
	
	public void move(int x, int y, int dx, int dy){
		if (!(x+dx==-1||x+dx==gameSize||y+dy==-1||y+dy==gameSize)){
			Entity temp = entities[x][y];
			Entity next = entities[x+dx][y+dy];
			next.die();
			remove(x+dx+gameSize*(y+dy));
			next= new Hero(dm, x+dx, y+dy);
			add(next,(x+dx)+gameSize*(y+dy));
			remove(x+gameSize*y);
			temp= new Entity(Color.black,0,dm, x, y);
			add(temp,x+gameSize*y);
			next.requestFocusInWindow();
		}
	}
}

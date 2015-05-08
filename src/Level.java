import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;



public class Level extends JPanel implements Serializable{
	private DiggerMain dm;
	private int gameSize = 16;
	protected Entity entities[][]= new Entity[gameSize][gameSize];
	
	public Level(DiggerMain dm){
		initDm(dm);
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
	public Level(DiggerMain dm,Entity entities[][]){
		initDm(dm);
		this.entities=entities;
		setLayout(new GridLayout(gameSize, gameSize, 0, 0));
		setOpaque(true);
		setBackground(Color.black);
		for (int i = 0; i < gameSize; i++) {
	        for (int j = 0; j < gameSize; j++) {
	        	add(entities[j][i]);    	
	        }
		}
		repaint();
	}
	public void initDm(DiggerMain dm){
		this.dm=dm;
	}
	public void initEntities(){
		for (int i=0;i<16;i++){
			for (int j=0;j<16;j++){
				entities[j][i].initDmLevel(dm);
			}
		}
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
	
	public void saveLevel(){
		new File("Saves").mkdirs();
    	String path = Paths.get("Saves").toAbsolutePath().toString();
    	JFileChooser choose = new JFileChooser(path);
    	disablestuff(choose);
    	int choice =choose.showSaveDialog(this);
    	if (choice == JFileChooser.APPROVE_OPTION){
    		try{
    			FileOutputStream fileout = new FileOutputStream("Saves/"+choose.getSelectedFile().getName());
    			ObjectOutputStream out = new ObjectOutputStream(fileout);
    			out.writeObject(this);
    			out.close();
    			fileout.close();
    		}catch(IOException e){throw new IllegalStateException(e);}
    	}
	}
	/*
	 * Disables directory navigation for save/load windows,
	 * because the user cannot be trusted with that grand power.
	 */
	private void disablestuff(Container container){
		Component c[] = container.getComponents();
		for(int i=0;i<c.length;i++){
			if (c[i] instanceof JComboBox) ((JComboBox<?>)c[i]).setEnabled(false);//Disables drop down menus.
			else if (c[i] instanceof JButton){
				String text = ((JButton)c[i]).getText();
				if (text==null||text.isEmpty()) ((JButton)c[i]).setEnabled(false);//Disables directory related buttons
			}else if (c[i] instanceof Container) disablestuff((Container)c[i]);
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

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 * 
 *Basically this class runs the game. It handles adding things to the DiggerMain, key bindings, and resets
 *
 * @author heshelhj.
 *         Created May 20, 2015.
 */
public class Level extends JPanel{
	private DiggerMain dm;
	protected int gameSize = 16;
	protected int imageSize = 32;
	private String saves[]={"Level1","Level2","Level3","Level4"};
	protected Entity entities[][]= new Entity[gameSize][gameSize];
//	private static final int DELAY = 1000;
	protected Entity hero;
	private transient BufferedImage background;
	private ArrayList<Integer> heroList;
	private ArrayList<Integer> hobbins;
	private ArrayList<Integer> nobbins;
	protected int[] enemySpawn;
	private static final long DELAY = 3000;
	private long sinceLast;
	
	
	public Level(DiggerMain dm){
		initDm(dm);
		setOpaque(true);
		for (int i = 0; i < gameSize; i++) {
	        for (int j = 0; j < gameSize; j++) {
	        	entities[j][i] = new Entity(Color.gray,0,dm, j*imageSize, i*imageSize,"Dirt");//Fills the game with "Dirt"
	        	entities[j][i].initDmLevel(dm);
	        	add(entities[j][i]);    	
	        }
		}
		Random r = new Random();
		int x,y;
		for (int i=0;i<8;i++){
			x=r.nextInt(16);
			y=r.nextInt(16);
			entities[x][y] = new Gold(dm,x*imageSize,y*imageSize);
		}
		for (int i = 0;i<gameSize;i++){
			entities[i][5]= new Entity(Color.black,0,dm,i*imageSize,5*imageSize,"Empty");
		}
		addHero(5,5);
		//Comment this out to make the game load pre-made levels.
//		for (int i = 0; i < 3;i++){
//			addEmerald(i+2,7);
//		}
		enemySpawn = new int[] {15,5};
		entities[11][14] = new Hobbin(dm,11*imageSize,14*imageSize);
		entities[15][5] = new Nobbin(dm,15*imageSize,5*imageSize);
		keybinding();
		initializeStartConditions();
	}
	
	public Level(DiggerMain dm,Entity entities[][],Entity hero,int[] enemySpawn){
		this.hero=hero;
		this.enemySpawn=enemySpawn;
		initDm(dm);
		this.entities=entities;
		setOpaque(true);
		for (int i = 0; i < gameSize; i++) {
	        for (int j = 0; j < gameSize; j++) {
	        	add(entities[j][i]);    	
	        }
		}
		keybinding();
	}
	
	
	//Initializers
	
	
	/**
	 * 
	 * Intializes a DiggerMain and binds the key presses to the game
	 *
	 * @param dm
	 */
	public void initDm(DiggerMain dm){
		this.dm=dm;
		initbg();
		keybinding();
	}
	/**
	 * 
	 *Intializes background
	 *
	 */
	public void initbg(){
		String picFile = "Images/Background.png";
	    try {                
	    	background = ImageIO.read(new File(picFile));
	    } catch (IOException e) {
	    	System.out.println("Could not open picture file: " + picFile);
	    }
	}
	/**
	 * 
	 * Initializes enemies
	 *
	 */
	public void initEntities(){
		for (int i=0;i<16;i++){
			for (int j=0;j<16;j++){
				entities[j][i].initDmLevel(dm);
			}
		}
	}
	/**
	 * 
	 * Initializes the hero, hobbin, and nobbin position lists
	 *
	 */
	public void initializeStartConditions(){
		heroList = getHero();
		hobbins = getHobbins();
		nobbins = getNobbins();
	}
	
	
	//Updating stuff
	
	
	/**
	 * Draws the game board
	 * @param g Graphics g
	 */
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        for (int i = 0; i < gameSize; i++) {
	        for (int j = 0; j < gameSize; j++) {
	        	entities[j][i].paint(g);
	        }
		}
	}
	/**
	 * Updates the level by adding enemies until there are 3
	 * @param time long time, time since entities has become less than 3.
	 */
	public void update(long time){
		if (6>(getHobbins().size()+getNobbins().size())){
			sinceLast+=time;
			if (sinceLast>DELAY){
				Nobbin n = new Nobbin(dm,enemySpawn[0]*imageSize,enemySpawn[1]*imageSize);
				entities[enemySpawn[0]][enemySpawn[1]]=n;
				sinceLast=0;
			}
		}
	}
	/**
	 * 
	 * Resets hobbins, nobbins, and the hero to their original position of the hero dies
	 * but still has lives left
	 *
	 */
	public void resetAfterDie(){
		ArrayList<Integer> heroListcur = getHero();
		ArrayList<Integer> hobbinscur = getHobbins();
		ArrayList<Integer> nobbinscur = getNobbins();
		entities[heroListcur.get(0)/imageSize][heroListcur.get(1)/imageSize] = new Entity(Color.black,0,dm,heroListcur.get(0),heroListcur.get(1),"Empty");
		Hero newHero = new Hero(dm, heroList.get(0), heroList.get(1));
		entities[heroList.get(0)/imageSize][heroList.get(1)/imageSize] = newHero;
		for (int i=0; i<hobbinscur.size();i+=2){
			entities[hobbinscur.get(i)/imageSize][hobbinscur.get(i+1)/imageSize] = new Entity(Color.black,0,dm,hobbinscur.get(i),hobbinscur.get(i+1),"Empty");
		}
		for (int i=0; i<hobbins.size()-1; i+=2){
			Hobbin newHobbin = new Hobbin(dm, hobbins.get(i), hobbins.get(i+1));
			entities[hobbins.get(i)/imageSize][hobbins.get(i+1)/imageSize] = newHobbin;
		}
		for (int i=0; i<nobbinscur.size();i+=2){
			entities[nobbinscur.get(i)/imageSize][nobbinscur.get(i+1)/imageSize] = new Entity(Color.black,0,dm,nobbinscur.get(i),nobbinscur.get(i+1),"Empty");
		}
		for (int i=0; i<nobbins.size()-1; i+=2){
			Nobbin newNobbin = new Nobbin(dm, nobbins.get(i), nobbins.get(i+1));
			entities[nobbins.get(i)/imageSize][nobbins.get(i+1)/imageSize] = newNobbin;
		}
		hero = newHero;
	}
	/**
	 * 
	 * Kills the game if all of the hero's lives are exhuasted
	 *
	 */
	public void hardReset(){
		dm.dispose();
	}
	/**
	 * 
	 * Goes to the next level(if one is available when all the emeralds on the screen runs out.
	 *
	 */
	public void nextLevel(){
		ArrayList<Integer> nowEmeralds = getEmeralds();
		if (nowEmeralds.size()==0){
			if(dm.currentLevelNumber<saves.length-1){
				dm.loadLevel(saves[dm.currentLevelNumber+1]);
				dm.currentLevelNumber++;
			}
		}
	}
	
	
	//Level saving stuff
	
	
	/**
	 * 
	 * Saves a created level
	 *
	 */
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
	 * (In all honesty, this is because save file ignores whatever
	 * directory you try to put it in since we want it kept with
	 * our project. It gets very confusing when saves aren't actually 
	 * in the directory you put them in so take away features from user) 
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
	
	
	//adding things for Testing
	
	
	/**
	 * 
	 * Places a hero on the board
	 *
	 * @param x -- the initial x position
	 * @param y -- the initial y position
	 */
	public void addHero(int x, int y) { //Puts a hero at the chosen location, for initial level setup.
		entities[x][y]= new Hero(dm, x*imageSize, y*imageSize);
		hero=entities[x][y];
		repaint();
	}
	/**
	 * 
	 * Adds emeralds to the board
	 *
	 * @param x -- the x position
	 * @param y -- the y position
	 */
	public void addEmerald(int x, int y) { //Puts a Emerald at the chosen location, for initial level setup.
		entities[x][y]= new Entity(Color.green,100,dm, x*imageSize, y*imageSize,"Emerald");
		repaint();
	}
	
	
	//Methods to get certain types of enemy from the game board.
	
	
	/**
	 * 
	 *adds the emeralds positions to an arrayList
	 *
	 * @return and arrayList of positions
	 */
	public ArrayList<Integer> getGold(){
		ArrayList<Integer> gold = new ArrayList<Integer>();
		for (int i=0;i<gameSize;i++){
			for (int j=0;j<gameSize;j++){
				if (entities[j][i].spriteName.contains("Gold")){
					gold.add(entities[j][i].position[0]);
					gold.add(entities[j][i].position[1]);
				}
			}
		}
		return gold;
	}
	/**
	 * 
	 *adds the emeralds positions to an arrayList
	 *
	 * @return and arrayList of positions
	 */
	public ArrayList<Integer> getEmeralds(){
		ArrayList<Integer> emeralds = new ArrayList<Integer>();
		for (int i=0;i<gameSize;i++){
			for (int j=0;j<gameSize;j++){
				if (entities[j][i].color.equals(Color.green)){
					emeralds.add(entities[j][i].position[0]);
					emeralds.add(entities[j][i].position[1]);
				}
			}
		}
		return emeralds;
	}
	/**
	 * 
	 * adds the hero to their own arrayList
	 *
	 * @return -- an arrayList with the hero's position
	 */
	public ArrayList<Integer> getHero(){
		ArrayList<Integer> heroList = new ArrayList<Integer>();
		for (int i=0;i<16;i++){
			for (int j=0;j<16;j++){
				if (entities[j][i].color.equals(Color.blue)){
					heroList.add(entities[j][i].position[0]);
					heroList.add(entities[j][i].position[1]);
				}
			}
		}
		return heroList;
	}
	/**
	 * 
	 *adds the Hobbins positions to an arrayList
	 *
	 * @return and arrayList of positions
	 */
	public ArrayList<Integer> getHobbins(){
		ArrayList<Integer> hobbins = new ArrayList<Integer>();
		for (int i=0;i<16;i++){
			for (int j=0;j<16;j++){
				if (entities[j][i].color.equals(Color.red)){
					hobbins.add(entities[j][i].position[0]);
					hobbins.add(entities[j][i].position[1]);
				}
			}
		}
		return hobbins;
	}
	/**
	 * 
	 *adds the Nobbins positions to an arrayList
	 *
	 * @return and arrayList of positions
	 */
	public ArrayList<Integer> getNobbins(){
		ArrayList<Integer> nobbins = new ArrayList<Integer>();
		for (int i=0;i<16;i++){
			for (int j=0;j<16;j++){
				if (entities[j][i].color.equals(Color.orange)){
					nobbins.add(entities[j][i].position[0]);
					nobbins.add(entities[j][i].position[1]);
				}
			}
		}
		return nobbins;
	}
	
	
	//Key binding
	
	
	/**
	 * 
	 * Binds keys to certain movements (hero movements, level up/down)
	 *
	 */
	public void keybinding(){
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("U"), "levelUp");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "levelDown");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "up");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "down");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "left");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "right");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "shoot");
		this.getActionMap().put("levelUp", new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	if(dm.currentLevelNumber<saves.length-1){
					dm.loadLevel(saves[dm.currentLevelNumber+1]);
					dm.currentLevelNumber++;
				}
		    }
		});
		this.getActionMap().put("levelDown", new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	if(dm.currentLevelNumber>0){
					dm.loadLevel(saves[dm.currentLevelNumber-1]);
					dm.currentLevelNumber--;
					repaint();
				}
		    }
		});
		this.getActionMap().put("up", new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	((Hero)hero).dpos[1]=-imageSize;
		    	((Hero)hero).dpos[0]=0;
		    }
		});
		this.getActionMap().put("down", new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	((Hero)hero).dpos[1]=imageSize;
		    	((Hero)hero).dpos[0]=0;
		    }
		});
		this.getActionMap().put("left", new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	((Hero)hero).dpos[0]=-imageSize;
		    	((Hero)hero).dpos[1]=0;
		    }
		});
		this.getActionMap().put("right", new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	((Hero)hero).dpos[0]=imageSize;
		    	((Hero)hero).dpos[1]=0;
		    }
		});
		this.getActionMap().put("shoot", new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	((Hero)hero).shoot=true;
		    }
		});
	}
}

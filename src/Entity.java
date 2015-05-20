import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * 
 * Handles the "things" that are placed on the game board( dirt, enemies, hero, etc). 
 *
 * @author heshelhj.
 *         Created May 20, 2015.
 */

public class Entity extends Canvas{
	private Integer pointValue;
	protected DiggerMain dm;
	protected int position[] = new int[2];
	protected Color color;
	protected Level level;
	protected transient BufferedImage sprite;
	protected String spriteName;
	protected int killPriority;
/**
 * 	
 * Contructs an entity 
 *
 * @param color --not really used, was useful before sprites were implemented
 * @param pointValue --points gained from killing the thing
 * @param dm --DiggerMain
 * @param x_position --the entities x position
 * @param y_position --the entities y position
 * @param spriteName --name of the sprite file
 */
	public Entity(Color color, Integer pointValue,DiggerMain dm, int x_position, int y_position,String spriteName) {
		this.pointValue=pointValue;
		this.color=color;
		position[0]=x_position;
		position[1]=y_position;
		this.spriteName=spriteName;
		if (spriteName.startsWith("Gold")) killPriority=4;
		else if (spriteName.startsWith("FallingGold")) killPriority=3;
		else switch (spriteName){
//			case "Gold": killPriority=9; break;
//			case "FallingGold": killPriority=8; break;
			case "Weapon": killPriority=2; break;
			case "Hobbin": killPriority=2; break;
			case "Nobbin": killPriority=2; break;
			case "Hero": killPriority=1; break;
			case "Empty": killPriority=-1; break;
			default: killPriority=0; break;
		}
		initDmLevel(dm);
	}
	/**
	 * 
	 * Loads the sprite from a file
	 *
	 */
	public void loadImage(){
		String picFile = "Images/"+spriteName+".png";
	    try {                
	    	sprite = ImageIO.read(new File(picFile));
	    } catch (IOException e) {
	    	System.out.println("Could not open picture file: " + picFile);
	    }
	}
	/**
	 * 
	 *Initializes the DiggerMain 
	 *
	 * @param dm
	 */
	public void initDmLevel(DiggerMain dm) {
		this.dm=dm;
		level = dm.currentLevel;
		loadImage();
	}
	/**
	 * draws the sprites
	 */
	public void paint(Graphics g){ //Eventually these will be sprites instead of rectangles.
		super.paint(g);
		g.drawImage(sprite, position[0], position[1], null);
		
	}
	/**
	 * 
	 * is the movement method for all entities.  It also checks if an entity should kill another 
	 * based on kill priority. 
	 *
	 * @param dx --amount the entity should move in the x direction
	 * @param dy --amount the entity should move in the y direction
	 */
	public void movement(int dx, int dy) {
		if (!(dx==0&&dy==0)){
			Entity next;
			if (level==null) System.out.println("Null");
			if (position[0]+dx<0||position[0]+dx>(level.gameSize-1)*level.imageSize||position[1]+dy<0||position[1]+dy>(level.gameSize-1)*level.imageSize) next=null;
			else next = level.entities[(position[0]+dx)/level.imageSize][(position[1]+dy)/level.imageSize];
			if (next!=null){
				if (killPriority>=next.killPriority){
					next.die();
					level.entities[(position[0]+dx)/level.imageSize][(position[1]+dy)/level.imageSize]=this;
					level.entities[(position[0])/level.imageSize][(position[1])/level.imageSize]= new Entity(Color.black,0,dm,position[0],position[1],"Empty");
					position[0]+=dx;
					position[1]+=dy;
					if (killPriority == next.killPriority){
						die();
						level.entities[(position[0])/level.imageSize][(position[1])/level.imageSize]= new Entity(Color.black,0,dm,position[0],position[1],"Empty");
					}
				}
				else die();
			}
		}
	}
	
	
	/**
	 * Entities will keep track of time since last update, this is how we will control speeds.
	 * This will be empty for things like emerald, dirt, or empty.
	 * For others it will probably just call move or howtomove, ect.
	 * @param time long time since last update.
	 */
	public void updateThis(long time) {
	}
	/**
	 * 
	 * Adds points to the score
	 *
	 */
	public void die(){
		awardPoints();
	}
	/**
	 * 
	 * Adds points to the score 
	 *
	 */
	public void awardPoints(){
		dm.addScore(pointValue);
	}
}

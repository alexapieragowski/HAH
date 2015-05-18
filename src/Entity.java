import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;




public class Entity extends Canvas{
	private Integer pointValue;
	protected DiggerMain dm;
	protected int position[] = new int[2];
	protected Color color;
	protected Level level;
	private transient BufferedImage sprite;
	protected String spriteName;
	protected int killPriority;
	
	public Entity(Color color, Integer pointValue,DiggerMain dm, int x_position, int y_position,String spriteName) {
		this.pointValue=pointValue;
		this.color=color;
		position[0]=x_position;
		position[1]=y_position;
		this.spriteName=spriteName;
		switch (spriteName){
			case "Gold": killPriority=9; break;
			case "Weapon": killPriority=2; break;
			case "Hobbin": killPriority=2; break;
			case "Nobbin": killPriority=2; break;
			case "Hero": killPriority=2; break;
			case "Empty": killPriority=-1; break;
			default: killPriority=0; break;
		}
		initDmLevel(dm);
	}
	public void loadImage(){
		String picFile = "Images/"+spriteName+".png";
	    try {                
	    	sprite = ImageIO.read(new File(picFile));
	    } catch (IOException e) {
	    	System.out.println("Could not open picture file: " + picFile);
	    }
	}
	public void initDmLevel(DiggerMain dm) {
		this.dm=dm;
		level = dm.currentLevel;
		loadImage();
	}
	
	public void paint(Graphics g){ //Eventually these will be sprites instead of rectangles.
		super.paint(g);
		g.drawImage(sprite, position[0], position[1], null);
		
	}
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
	
	public void die(){
		awardPoints();
	}
	public void awardPoints(){
		dm.addScore(pointValue);
	}
}

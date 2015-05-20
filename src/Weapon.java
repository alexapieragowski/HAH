import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

/**
 * 
 * Creates the shooting weapon for the hero
 *
 * @author heshelhj.
 *         Created May 20, 2015.
 */
public class Weapon extends Entity {
	private static final int DELAY = 150;
	private long sinceLast;
	private long spin;
	private int[] dpos = {0,0};
	private String facing;
	public Weapon(DiggerMain dm, int x_position, int y_position, String facing) {
		super(Color.white, 0, dm, x_position, y_position,"Weapon");
		this.facing=facing;
	}
	
	/**
	 * updates the position of the weapon
	 */
	public void updateThis(long time) {
		sinceLast+=time;
		spin+=time;
		if (sinceLast>DELAY){
			//howToMove();
			move();
			dpos[0]=0;
			dpos[1]=0;
			sinceLast=0;
		}
	}
	/**
	 * paints and spins the weapons
	 */
	public void paint(Graphics g){ //Eventually these will be sprites instead of rectangles.
		super.paint(g);
		if (spin>DELAY/2){
			AffineTransform transform = new AffineTransform();
			transform.rotate(Math.PI/2,sprite.getWidth()/2,sprite.getHeight()/2);
			AffineTransformOp op = new AffineTransformOp(transform,AffineTransformOp.TYPE_BILINEAR);
			sprite=op.filter(sprite,null);
			spin=0;
		}
	}
	/**
	 * 
	 *calls the movement based on the direction based on the direction the hero is facing and 
	 *makes the weapon fire in the appropriate direction
	 *
	 */
	public void move(){
		if (facing == "right") {
			if (position[0]/level.imageSize+1<level.gameSize && isValid(facing)){
				this.movement(level.imageSize, 0);
			}else level.entities[position[0]/level.imageSize][position[1]/level.imageSize]=new Entity(Color.black,0,dm,position[0],position[1],"Empty");
		}
		if (facing == "left") {
			if (position[0]/level.imageSize-1>=0 && isValid(facing)){
				this.movement(-level.imageSize, 0);
			}else level.entities[position[0]/level.imageSize][position[1]/level.imageSize]=new Entity(Color.black,0,dm,position[0],position[1],"Empty");
			
		}
		if (facing == "up") { 
			if (position[1]/level.imageSize-1>=0 && isValid(facing)){
				this.movement(0, -level.imageSize);
			}else level.entities[position[0]/level.imageSize][position[1]/level.imageSize]=new Entity(Color.black,0,dm,position[0],position[1],"Empty");
		}
		if (facing == "down") {
			if (position[1]/level.imageSize+1<level.gameSize && isValid(facing)){
				this.movement(0, level.imageSize);
			}else level.entities[position[0]/level.imageSize][position[1]/level.imageSize]=new Entity(Color.black,0,dm,position[0],position[1],"Empty");
		}
	}
	/**
	 * 
	 * checks if the hero can throw the weapon (isn't on an edge of the screen or so)
	 *
	 * @param facing
	 * @return
	 */
	private boolean isValid(String facing){
		String sprite;
		switch (facing){
			case "right":{sprite = level.entities[position[0]/level.imageSize+1][position[1]/level.imageSize].spriteName; break; }
			case "left":{sprite = level.entities[position[0]/level.imageSize-1][position[1]/level.imageSize].spriteName; break; }
			case "up":{sprite = level.entities[position[0]/level.imageSize][position[1]/level.imageSize-1].spriteName; break; }
			case "down":{sprite = level.entities[position[0]/level.imageSize][position[1]/level.imageSize+1].spriteName; break; }
			default: return false;
		}
		return sprite.equals("Empty")||sprite.equals("Nobbin")||sprite.equals("Hobbin");
	}
	

	
}
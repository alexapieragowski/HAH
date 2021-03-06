import java.awt.Color;
import java.util.Random;

/**
 * 
 * Creates a gold object that is not falling
 *
 * @author heshelhj.
 *         Created May 20, 2015.
 */
public class Gold extends Entity {
	private static final long DELAY = 1000;
	private long sinceLast;
	private static Random r = new Random();

	public Gold(DiggerMain dm, int x_position, int y_position) {
		super(Color.yellow, 0, dm, x_position, y_position, "Gold"+r.nextInt(8));
	}
	
	public Gold(DiggerMain dm, int x_position, int y_position, String spriteName) {
		super(Color.yellow, 0, dm, x_position, y_position, spriteName);
	}
	
	@Override
	/**
	 * Calls the movement method on gold and checks if gold should fall
	 */
	public void updateThis(long time) {
		if(position[1]/level.imageSize+1<level.gameSize){
			String ex = level.entities[position[0]/level.imageSize][position[1]/level.imageSize+1].spriteName;
			if (!(ex.equals("Dirt")||ex.equals("Emerald")||ex.equals("Gold"))){
				sinceLast+=time;
				if (sinceLast>DELAY){
					fallDown();
				}
			}
		}
	}
	/**
	 * 
	 * moves the gold down if there is nothing below it
	 *
	 */
	public void fallDown(){
		String ex = level.entities[position[0]/level.imageSize][position[1]/level.imageSize+1].spriteName;
		if (!(ex.equals("Dirt")||ex.equals("Emerald"))){
			//movement(0, level.imageSize);
			FallingGold fallingGold = new FallingGold(dm, position[0], position[1], "FallingGold"+spriteName.substring(4));
			level.entities[position[0]/level.imageSize][position[1]/level.imageSize] = fallingGold;
		}
	}
	
	

}

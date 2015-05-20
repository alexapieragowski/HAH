import java.awt.Color;

/**
 * 
 * This class creates the gold that is in the process falling
 *
 * @author heshelhj.
 *         Created May 20, 2015.
 */
public class FallingGold extends Entity {
	private static final long DELAY = 150;
	private long sinceLast;
	private int originalPosition;

	public FallingGold(DiggerMain dm, int x_position, int y_position, String spriteName) {
		super(Color.cyan, 0, dm, x_position, y_position, spriteName);
		originalPosition = position[1];
	}
	/**
	 * 
	 * This method checks if the bag has fallen far enough to break and if it hasn't places an unbroken 
	 * gold bag where it stops, else it calls the breakOpen method
	 *
	 */
	public void ifBreak(){
		if (position[1] > originalPosition+level.imageSize) breakOpen();
		else {
			Gold newGold = new Gold(dm, position[0], position[1],"Gold"+spriteName.substring(11));
			level.entities[position[0]/level.imageSize][position[1]/level.imageSize] = newGold;
		}
	}
	/**
	 * 
	 *This method tells the falling gold how to move and how to check if it should move
	 *
	 */
	private void howToMove() {
		if (position[1]/level.imageSize+1<level.gameSize){
			String ex = level.entities[position[0]/level.imageSize][position[1]/level.imageSize+1].spriteName;
			if (!(ex.equals("Dirt")||ex.equals("Emerald")||ex.equals("Gold"))) movement(0,level.imageSize);
			else ifBreak();
		} else ifBreak();
	}
	@Override
	/**
	 * this is the updating method that allows the gold to fall continuously
	 */
	public void updateThis(long time) {
		sinceLast+=time;
		if (sinceLast>DELAY){
			howToMove();
			sinceLast=0;
		}
	}
	/**
	 * 
	 * If the gold has fallen far enough this method is called and the falling gold then is removed
	 * and a broken gold bag is created in its place
	 *
	 */
	public void breakOpen(){
		BrokenGold bg = new BrokenGold(dm, this.position[0],this.position[1],"BrokenGold"+spriteName.substring(11));  
		level.entities[position[0]/level.imageSize][position[1]/level.imageSize] = bg;
	}

}
